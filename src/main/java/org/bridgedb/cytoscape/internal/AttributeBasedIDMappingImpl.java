package org.bridgedb.cytoscape.internal;

/*******************************************************************************
 * Copyright 2010-2013 BridgeDb App developing team
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import org.bridgedb.cytoscape.internal.util.DataSourceWrapper;
import org.bridgedb.cytoscape.internal.util.IDMapperWrapper;
import org.bridgedb.cytoscape.internal.util.XrefWrapper;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.model.CyTable;

import java.util.*;

public class AttributeBasedIDMappingImpl implements AttributeBasedIDMapping{
    protected TaskMonitor taskMonitor;
    protected boolean interrupted;
    protected String report;
    protected Map<String,Class<?>> attrNameType = null;
    protected IDMapperClientManager idMapperClientManager;
    protected IDMapperWrapper idMapperWrapper;

    public AttributeBasedIDMappingImpl(TaskMonitor taskMonitor, IDMapperClientManager idMapperClientManager) {
        this.taskMonitor = taskMonitor;
        idMapperWrapper = new IDMapperWrapper(idMapperClientManager);
    }

    public void setTaskMonitor(TaskMonitor taskMonitor) {
        this.taskMonitor = taskMonitor;
        interrupted = false;
    }

    public void interrupt() {
            interrupted = true;
            report = "Aborted!";
     }

    public String getReport() {
        return report;
    }

    /**
     * Define target attributes.
     * Call this method first before mapping if necessary.
     * @param attrNameType
     */
    public void defineTgtAttrs(CyNetwork network, Map<String,Class<?>> attrNameType) {
        this.attrNameType = attrNameType;
        
        CyTable cyTable = network.getDefaultNodeTable();

        for (Map.Entry<String,Class<?>> entry : attrNameType.entrySet()) {
            String attrname = entry.getKey();
            Class<?> attrtype = entry.getValue();
            
            if (null == cyTable.getColumn(attrname)) {
                // if not exist
                if (attrtype == List.class) {
                    cyTable.createListColumn(attrname, String.class, false);
                } else {
                    cyTable.createColumn(attrname, attrtype, false);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void map(CyNetwork network, Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes,
            Map<String, DataSourceWrapper> mapTgtAttrNameIDType) {

        // prepare source xrefs
        List<CyNode> nodes = network.getNodeList();
        CyTable table = network.getDefaultNodeTable();
        
        updateTaskMonitor("Preparing source cross references...", -1.0);
        Map<CyNode,Set<XrefWrapper>> mapNodeSrcXrefs = prepareNodeSrcXrefs(table, nodes, mapSrcAttrIDTypes);
        Set<XrefWrapper> srcXrefs = srcXrefUnion(mapNodeSrcXrefs);

        // target id types
        Set<DataSourceWrapper> tgtTypes = new HashSet(mapTgtAttrNameIDType.values());

        // id mapping
        updateTaskMonitor("Mapping IDs...", -1.0);
        Map<XrefWrapper, Set<XrefWrapper>> mapping = idMapperWrapper.mapID(srcXrefs, tgtTypes);

        // set target attribute
        updateTaskMonitor("Set target column...", -1.0);
        Map<CyNode,Set<XrefWrapper>> mapNodeTgtXrefs = getNodeTgtXrefs(mapNodeSrcXrefs, mapping);
        setTgtAttribute(table, mapNodeTgtXrefs, mapTgtAttrNameIDType);

        report = "Identifiers mapped for "+mapNodeTgtXrefs.size()+" nodes (out of "+nodes.size()+")!";
        updateTaskMonitor(report,1.00);
    }

    private Map<CyNode,Set<XrefWrapper>> prepareNodeSrcXrefs(CyTable nodeTable,
            Collection<CyNode> nodes, Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes) {
        Map<CyNode,Set<XrefWrapper>> ret = new HashMap();

//        int nNode = nodes.size();
//        int i=0;
        for (CyNode node : nodes) {
            if (interrupted) return null;
//            updateTaskMonitor("Preparing cross reference for nodes...\n"+i+"/"+nNode,(i+1)*100/nNode);
//            i++;

            Set<XrefWrapper> xrefs = new HashSet();
            ret.put(node, xrefs);
            
            CyRow cyRow = nodeTable.getRow(node.getSUID());

            for (Map.Entry<String,Set<DataSourceWrapper>> entryAttrIDTypes : mapSrcAttrIDTypes.entrySet()) {
                String attrName = entryAttrIDTypes.getKey();
                Set<DataSourceWrapper> dss = entryAttrIDTypes.getValue();

                CyColumn cyColumn = nodeTable.getColumn(attrName);
                if (cyColumn.getType() == List.class) {
                    List attr = cyRow.get(cyColumn.getName(), List.class);
                    if (attr!=null) {
                        for (Object obj : attr) {
                            String str = obj.toString();
                            for (DataSourceWrapper ds : dss) {
                                xrefs.add(new XrefWrapper(str, ds));
                            }
                        }
                    }
                } else {
                    Object obj = cyRow.get(cyColumn.getName(), cyColumn.getType());
                    if (obj!=null) {
                        String str = obj.toString();
                        if (str.length()>0) {
                            for (DataSourceWrapper ds : dss) {
                                xrefs.add(new XrefWrapper(str, ds));
                            }
                        }
                    }
                }
            }
        }

        return ret;
    }

    private Set<XrefWrapper> srcXrefUnion(Map<CyNode,Set<XrefWrapper>> mapNodeSrcXrefs) {
        Set<XrefWrapper> ret = new HashSet();
        for (Set<XrefWrapper> xrefs : mapNodeSrcXrefs.values()) {
            ret.addAll(xrefs);
        }
        return ret;
    }

    private Map<CyNode,Set<XrefWrapper>> getNodeTgtXrefs (Map<CyNode,Set<XrefWrapper>> mapNodeSrcXrefs,
                                                 Map<XrefWrapper, Set<XrefWrapper>> idMapping) {
        Map<CyNode,Set<XrefWrapper>> mapNodeTgtXrefs = new HashMap();

        for (Map.Entry<CyNode,Set<XrefWrapper>> entryNodeXrefs : mapNodeSrcXrefs.entrySet()) {
            CyNode node = entryNodeXrefs.getKey();
            Set<XrefWrapper> tgtXrefs = new HashSet();
            Set<XrefWrapper> srcXrefs = entryNodeXrefs.getValue();
            //TODO: deal with ambiguity--same node, same attribute, different data source
            for (XrefWrapper srcXref : srcXrefs) {
                Set<XrefWrapper> xrefs = idMapping.get(srcXref);
                if (xrefs!=null) {
                    tgtXrefs.addAll(xrefs);
                }
            }

            if (!tgtXrefs.isEmpty())
                mapNodeTgtXrefs.put(node, tgtXrefs);
        }

        return mapNodeTgtXrefs;
    }

    private void setTgtAttribute(CyTable nodeTable, Map<CyNode,Set<XrefWrapper>> mapNodeTgtXrefs,
                                 Map<String, DataSourceWrapper> mapTgtAttrNameIDType) {
        Map<DataSourceWrapper, Set<String>> mapIDTypeAttrName = new HashMap();
        for (String attrName : mapTgtAttrNameIDType.keySet()) {
            DataSourceWrapper idType = mapTgtAttrNameIDType.get(attrName);
            Set<String> names = mapIDTypeAttrName.get(idType);
            if (names==null) {
                names = new HashSet();
                mapIDTypeAttrName.put(idType, names);
            }
            names.add(attrName);
        }

//        int i = 0;
//        int nNode = mapNodeTgtXrefs.size();
        for (Map.Entry<CyNode,Set<XrefWrapper>> entryNodeXrefs : mapNodeTgtXrefs.entrySet()) {
            if (interrupted) return;
//            updateTaskMonitor("Preparing cross reference for nodes...\n"+i+"/"+nNode,(i+1)*100/nNode);
//            i++;

            // type wise
            Map<DataSourceWrapper, Set<String>> mapDsIds = new HashMap();
            Set<XrefWrapper> tgtXrefs = entryNodeXrefs.getValue();
            for (XrefWrapper xref : tgtXrefs) {
                DataSourceWrapper ds = xref.getDataSource();
                Set<String> ids = mapDsIds.get(ds);
                if (ids==null) {
                    ids = new TreeSet(); // alphabetically
                    mapDsIds.put(ds, ids);
                }
                ids.add(xref.getValue());
            }
            
            // set attribute
            CyNode node = entryNodeXrefs.getKey();
            CyRow cyRow = nodeTable.getRow(node.getSUID());
            
            for (Map.Entry<DataSourceWrapper, Set<String>> entryDsIds : mapDsIds.entrySet()) {
                DataSourceWrapper ds = entryDsIds.getKey();
                Set<String> attrNames = mapIDTypeAttrName.get(ds);
                if (attrNames==null) {
                    // TODO: what happened?
                    continue;
                }
                for (String attrName : attrNames) {
                    Class attrType = nodeTable.getColumn(attrName).getType();
                    Set<String> ids = entryDsIds.getValue();
                    if (attrType==List.class) {
                        List<String> values = new ArrayList(ids);
                        cyRow.set(attrName, values);
                    } else if (attrType==String.class) {
                        // only returns the first ID
                        //TODO: is that a way to get the "best" one?
                        if (!ids.isEmpty()) {
                            cyRow.set(attrName, ids.iterator().next());
                        }
                    }
                }
            }

        }

    }

    private void updateTaskMonitor(String status, double percentage) {
        if (this.taskMonitor!=null) {
            taskMonitor.setStatusMessage(status);
            taskMonitor.setProgress(percentage);
        }
    }
}
