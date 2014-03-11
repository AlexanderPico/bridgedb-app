package org.bridgedb.cytoscape.internal.task;

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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.bridgedb.cytoscape.internal.AttributeBasedIDMappingImpl;
import org.bridgedb.cytoscape.internal.util.DataSourceWrapper;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.cytoscape.model.CyNetwork;

import java.util.Set;
import java.util.Map;
import org.bridgedb.cytoscape.internal.IDMapperClientManager;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.Tunable;
/**
 *
 */
public class AttributeBasedIDMappingTask extends AbstractTask implements ObservableTask {
    @Tunable(description="Network to mapping identifiers in",context="nogui")
    public CyNetwork network;
    
    @Tunable(description="",context="nogui")
    public String sourceAttribute;
    
    @Tunable(description="",context="nogui")
    public String sourceIdType;
    
    @Tunable(description="",context="nogui")
    public String targetAttribute;
    
    @Tunable(description="",context="nogui")
    public String targetIdType;
    
    private Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes;
    private Map<String, DataSourceWrapper> mapTgtAttrNameIDType;
    private Map<String,Class<?>> mapTgtAttrNameAttrType;
    
    AttributeBasedIDMappingImpl mappingService = new AttributeBasedIDMappingImpl();
    
    private boolean success = false;
    
    private boolean byCommand = true;
    
    public AttributeBasedIDMappingTask() {
    }

	/**
         * 
         * @param networks
         * @param mapSrcAttrIDTypes
         * @param mapTgtAttrNameIDType
         */
	public AttributeBasedIDMappingTask(final CyNetwork network,
                                       final Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes,
                                       final Map<String, DataSourceWrapper> mapTgtAttrNameIDType,
                                       Map<String,Class<?>> mapTgtAttrNameAttrType) {
            byCommand = false;
            this.network = network;
            this.mapSrcAttrIDTypes = mapSrcAttrIDTypes;
            this.mapTgtAttrNameIDType = mapTgtAttrNameIDType;
            this.mapTgtAttrNameAttrType = mapTgtAttrNameAttrType;
	}

	/**
	 * Executes Task.
	 */
    //@Override
	public void run(final TaskMonitor taskMonitor) {
            if (byCommand && !convertCommandParameters(taskMonitor)) {
                return;
            }
            
		 taskMonitor.setTitle("Mapping identifiers ...");
		 try {
			 mappingService.setTaskMonitor(taskMonitor);
			 mappingService.defineTgtAttrs(network, mapTgtAttrNameAttrType);
			 mappingService.map(network, mapSrcAttrIDTypes, mapTgtAttrNameIDType);
                         
			 taskMonitor.showMessage(TaskMonitor.Level.INFO, mappingService.getReport());
                         success = true;
		 } catch (Exception e) {
			 taskMonitor.showMessage(TaskMonitor.Level.ERROR,"ID mapping failed.\n");
			 e.printStackTrace();
		 }
	}
        
        public boolean success() {
            return success;
        }

        public String getResults(Class type)  {
            return mappingService.getReport();
        }
        
    private boolean convertCommandParameters(final TaskMonitor taskMonitor) {
        if (network == null) {
            taskMonitor.showMessage(TaskMonitor.Level.ERROR, "Please specify a network.");
            return false;
        }
        
        if (sourceAttribute == null) {
            taskMonitor.showMessage(TaskMonitor.Level.ERROR, "Please specify source attribute.");
            return false;
        }
        
        if (null == network.getDefaultNodeTable().getColumn(sourceAttribute)) {
            taskMonitor.showMessage(TaskMonitor.Level.ERROR, "Could not find source node attribute "
                    +sourceAttribute);
            return false;
        }
        
        if (sourceIdType == null) {
            taskMonitor.setStatusMessage("Please specify source ID type.");
            return false;
        }
        
        Set<DataSourceWrapper> srcDataSources = IDMapperClientManager.getSupportedSrcTypes();
        Set<DataSourceWrapper> tgtDataSources = IDMapperClientManager.getSupportedTgtTypes();
        if (srcDataSources==null || srcDataSources.isEmpty()) {
            taskMonitor.setStatusMessage("No supported source or target id type."
                    + " Please select mapping resources first.");
            return false;
        }
        
        DataSourceWrapper srcDsw = DataSourceWrapper.getInstance(sourceIdType);
        if (!srcDataSources.contains(srcDsw)) {
            taskMonitor.showMessage(TaskMonitor.Level.ERROR, "Could not find source ID type "
                    +sourceIdType);
            return false;
        }
        
        DataSourceWrapper tgtDsw = DataSourceWrapper.getInstance(targetIdType);
        if (!tgtDataSources.contains(tgtDsw)) {
            taskMonitor.showMessage(TaskMonitor.Level.ERROR, "Could not find target ID type "
                    +targetIdType);
            return false;
        }
        
        if (targetAttribute == null) {
            taskMonitor.showMessage(TaskMonitor.Level.ERROR, "Please specify target attribute.");
            return false;
        }
        
        if (null == network.getDefaultNodeTable().getColumn(targetAttribute)) {
            taskMonitor.showMessage(TaskMonitor.Level.INFO, "Could not find target node attribute "
                    +targetAttribute+". A new attribute in node table will be created.");
        }
        
        mapSrcAttrIDTypes = Collections.singletonMap(sourceAttribute, Collections.singleton(srcDsw));
        mapTgtAttrNameIDType = Collections.singletonMap(targetAttribute, tgtDsw);
        mapTgtAttrNameAttrType = new HashMap<String,Class<?>>(1);
        mapTgtAttrNameAttrType.put(targetAttribute, List.class); // why could not use singleton?
        
        return true;
    }
}
