/*
 Copyright (c) 2006, 2007, The Cytoscape Consortium (www.cytoscape.org)

 The Cytoscape Consortium is:
 - Institute for Systems Biology
 - University of California San Diego
 - Memorial Sloan-Kettering Cancer Center
 - Institut Pasteur
 - Agilent Technologies

 This library is free software; you can redistribute it and/or modify it
 under the terms of the GNU Lesser General Public License as published
 by the Free Software Foundation; either version 2.1 of the License, or
 any later version.

 This library is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 documentation provided hereunder is on an "as is" basis, and the
 Institute for Systems Biology and the Whitehead Institute
 have no obligations to provide maintenance, support,
 updates, enhancements or modifications.  In no event shall the
 Institute for Systems Biology and the Whitehead Institute
 be liable to any party for direct, indirect, special,
 incidental or consequential damages, including lost profits, arising
 out of the use of this software and its documentation, even if the
 Institute for Systems Biology and the Whitehead Institute
 have been advised of the possibility of such damage.  See
 the GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */

package org.bridgedb.cytoscape.internal.ui;

import org.bridgedb.cytoscape.internal.task.AttributeBasedIDMappingTask;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JDialog;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.cytoscape.application.CyApplicationManager;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyTable;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.FinishStatus;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.TaskObserver;
import org.bridgedb.cytoscape.internal.IDMapperClientManager;
import org.bridgedb.cytoscape.internal.util.DataSourceWrapper;

/**
 *
 * @author gjj
 */
public class BridgeDbDialog extends javax.swing.JDialog {
        private final CyNetwork currentNetwork;
	private final TaskManager taskManager;
        private final OpenBrowser openBrowser;
        private final FileUtil fileUtil;
        private final IDMapperClientManager idMapperClientManager;
	
    /** Creates new form BridgeDbDialog */
    public BridgeDbDialog(java.awt.Frame parent,
            CyApplicationManager cyApplicationManager,
            TaskManager taskManager, OpenBrowser openBrowser,
            FileUtil fileUtil, IDMapperClientManager idMapperClientManager,
            boolean modal) {
        super(parent, modal);
        this.currentNetwork = cyApplicationManager.getCurrentNetwork();
        this.openBrowser = openBrowser;
        this.taskManager = taskManager;
        this.fileUtil = fileUtil;
        this.idMapperClientManager = idMapperClientManager;
        initComponents();
        postInit();
    }

    public void postInit() {
        updateOKButtonEnable();

        sourceAttributeSelectionTable.setIDTypeSelectionChangedListener(
                new CheckComboBoxSelectionChangedListener() {
            public void selectionChanged(int idx) {
                setSupportedTgtTypesInTable();
            }
        });
        setSupportedSrcTypesInTable();
        
       // sourceAttributeSelectionTable.addRow();

        setSupportedTgtTypesInTable();
        targetAttributeSelectionTable.addRow();

//        setSelectedNetworkInSrcTable();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JPanel sourcePanel = new javax.swing.JPanel();
        javax.swing.JScrollPane sourceScrollPane = new javax.swing.JScrollPane();
        javax.swing.JPanel addRemoveSourcePanel = new javax.swing.JPanel();
        javax.swing.JPanel destinationPanel = new javax.swing.JPanel();
        javax.swing.JScrollPane destinationScrollPane = new javax.swing.JScrollPane();
        javax.swing.JPanel addRemoveDestinationPanel = new javax.swing.JPanel();
        javax.swing.JPanel typeSourceConfPanel = new javax.swing.JPanel();
        javax.swing.JButton srcConfBtn = new javax.swing.JButton();
        javax.swing.JPanel OKPanel = new javax.swing.JPanel();
        OKBtn = new javax.swing.JButton();
        javax.swing.JButton cancelBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BridgeDb");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        sourcePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select source attribute/IDType(s)"));
        sourcePanel.setMinimumSize(new java.awt.Dimension(500, 120));
        sourcePanel.setPreferredSize(new java.awt.Dimension(500, 140));
        sourcePanel.setLayout(new java.awt.GridBagLayout());

        sourceScrollPane.setMinimumSize(new java.awt.Dimension(300, 100));

        sourceAttributeSelectionTable = new SourceAttributeSelectionTable(currentNetwork, idMapperClientManager);
        sourceScrollPane.setViewportView(sourceAttributeSelectionTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        sourcePanel.add(sourceScrollPane, gridBagConstraints);

        addRemoveSourcePanel.setLayout(new javax.swing.BoxLayout(addRemoveSourcePanel, javax.swing.BoxLayout.LINE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        sourcePanel.add(addRemoveSourcePanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new Insets(5, 5, 5, 0);
        getContentPane().add(sourcePanel, gridBagConstraints);

        destinationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select destination attribute/IDType(s)"));
        destinationPanel.setMinimumSize(new java.awt.Dimension(500, 130));
        destinationPanel.setPreferredSize(new java.awt.Dimension(500, 140));
        destinationPanel.setLayout(new java.awt.GridBagLayout());

        destinationScrollPane.setMinimumSize(new java.awt.Dimension(300, 100));
        destinationScrollPane.setPreferredSize(new java.awt.Dimension(300, 100));

        targetAttributeSelectionTable = new TargetAttributeSelectionTable(idMapperClientManager);
        destinationScrollPane.setViewportView(targetAttributeSelectionTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        destinationPanel.add(destinationScrollPane, gridBagConstraints);

        addRemoveDestinationPanel.setLayout(new javax.swing.BoxLayout(addRemoveDestinationPanel, javax.swing.BoxLayout.LINE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        destinationPanel.add(addRemoveDestinationPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new Insets(5, 5, 5, 0);
        getContentPane().add(destinationPanel, gridBagConstraints);

        srcConfBtn.setText("Manage ID Mapping Resources");
        srcConfBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srcConfBtnActionPerformed(evt);
            }
        });
        typeSourceConfPanel.add(srcConfBtn);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(5, 5, 0, 0);
        getContentPane().add(typeSourceConfPanel, gridBagConstraints);

        OKBtn.setText("   OK   ");
        OKBtn.setEnabled(false);
        OKBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKBtnActionPerformed(evt);
            }
        });
        OKPanel.add(OKBtn);

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        OKPanel.add(cancelBtn);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new Insets(5, 5, 0, 0);
        getContentPane().add(OKPanel, gridBagConstraints);
        
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void srcConfBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_srcConfBtnActionPerformed
        IDMappingSourceConfigDialog srcConfDialog = new IDMappingSourceConfigDialog(
                this, taskManager, openBrowser, fileUtil, idMapperClientManager, true);
        srcConfDialog.setLocationRelativeTo(this);
        srcConfDialog.setVisible(true);

        if (srcConfDialog.isModified()) {
            // Execute Task in New Thread; pop open JTask Dialog Box.
            taskManager.execute((new TaskIterator(new ApplySourceChangeTask())));
        }
    }//GEN-LAST:event_srcConfBtnActionPerformed

    private void OKBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKBtnActionPerformed
        if (!verifyUserInput()) return;

        //Set<CyNetwork> networks = new HashSet(selectedNetworkData.getNetworks());
        final Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes = sourceAttributeSelectionTable.getSourceAttrType();
        final Map<String, DataSourceWrapper> mapTgtAttrNameIDType = targetAttributeSelectionTable.getMapAttrNameIDType();
        final Map<String,Class<?>> mapTgtAttrNameAttrType = targetAttributeSelectionTable.getMapAttrNameAttrType();

//        // define target attributes
//        defineTgtAttributes(mapTgtAttrNameAttrType);

        // execute task
        final AttributeBasedIDMappingTask task
                = new AttributeBasedIDMappingTask(currentNetwork, mapSrcAttrIDTypes,
                        mapTgtAttrNameIDType, mapTgtAttrNameAttrType);
        final JDialog thisDialog = this;

        // Execute Task in New Thread; pop open JTask Dialog Box.
        //Temp solution, need create taskfactory for AttributeBasedIDMappingTask
        //http://chianti.ucsd.edu/svn/core3/network-merge-impl/trunk/src/main/java/org/cytoscape/network/merge/internal/task/NetworkMergeTaskFactory.java
        taskManager.execute(new TaskIterator(task), new TaskObserver() {
            public void allFinished(FinishStatus finishStatus) {
            }
            
            public void taskFinished(ObservableTask otask) {
                if (otask==task) {
                    if (task.success()) {
                        thisDialog.setVisible(false);
                        thisDialog.dispose();
                        cancelled = false;
                    } else {
                        CyTable cyTable = currentNetwork.getDefaultNodeTable();
                        for (String attrName : mapTgtAttrNameAttrType.keySet()) {
                            cyTable.deleteColumn(attrName);
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                JOptionPane.showMessageDialog(thisDialog, "IDs mapping failed");
                            }
                        });
                    }
                }
            }
        });

//        if (succ) {
//            this.setVisible(false);
//            this.dispose();
//            cancelled = false;
//        } else {
            //Delete the new attributes
        	//Temp comments 
//            CyAttributes nodeAttributes = Cytoscape.getNodeAttributes();
//            for (String attrName : mapTgtAttrNameAttrType.keySet()) {
//                nodeAttributes.deleteAttribute(attrName);
//            }
//        }
    }//GEN-LAST:event_OKBtnActionPerformed

    private boolean verifyUserInput() {
//        if (selectedNetworkData.getNetworks().isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please select at least one network.");
//            return false;
//        }

        if (idMapperClientManager.countSelectedClients()==0) {
            int ret = JOptionPane.showConfirmDialog(this, "No ID mapping resources have been registered/selected.\n"
                    + "Would you like to manage ID mapping resources?", "No ID Mapping Resources",
                    JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION) {
                srcConfBtnActionPerformed(null);
            } 
            
            return false;
            
        }

//        if (types[1].isEmpty()) {
//            JOptionPane.showMessageDialog(this, "No target ID type available. Please configure the sources of ID mapping first.");
//            return false;
//        }

        Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes = sourceAttributeSelectionTable.getSourceAttrType();
        for (Set<DataSourceWrapper> dss : mapSrcAttrIDTypes.values()) {
            if (dss==null || dss.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select at least one ID type for each source attribute.");
                return false;
            }
            
            for (DataSourceWrapper ds : dss) {
                if (ds.getDsAttr() == DataSourceWrapper.DsAttr.ATTRIBUTE) {
                    int ret = JOptionPane.showConfirmDialog(this, "Attributes selected for source type."
                            + " It may take long time to map from attributes to IDs.\n"
                            + "Are you sure you want to proceed?", "Attribute mapping takes long time",
                            JOptionPane.YES_NO_OPTION);
                    if (ret == JOptionPane.NO_OPTION) {
                        return false;
                    }
                }
            }
        }
        
        CyTable table = currentNetwork.getDefaultNodeTable();
        List<String> existAttrNames = new ArrayList<String>();
        for(CyColumn cyCol : table.getColumns()) {
        	existAttrNames.add(cyCol.getName());
        }
        List<String> attrNames = targetAttributeSelectionTable.getTgtAttrNames();
        if (!Collections.disjoint(attrNames, existAttrNames)) { // overlap between new and existing attribute
            JOptionPane.showMessageDialog(this, "Target attributes must have new names.");
            return false;
        }
        if (attrNames.contains("")) {
            JOptionPane.showMessageDialog(this, "The new attribute name cannot be empty.");
            return false;
        }

        Set<String> attrNamesNR = new HashSet(attrNames);
        if (attrNamesNR.size()!=attrNames.size()) { //same name
            JOptionPane.showMessageDialog(this, "Target attributes must have different names.");
            return false;
        }
        

//        List<String> idTypes = targetAttributeSelectionTable.getTgtIDTypes();
//        Set<String> idTypesNR = new HashSet(idTypes);
//        // TODO: problem when id type and attribute have the same name
//        if (idTypesNR.size()!=idTypes.size()) {
//            JOptionPane.showMessageDialog(this, "At most one target attribute is allowed for each ID type.");
//            return false;
//        }

        return true;
    }
    
    /**
     * update the button of OK to enable
     * 
     */
    private void updateOKButtonEnable() {
        if (currentNetwork==null) {
            OKBtn.setEnabled(false);
            OKBtn.setToolTipText("No current network");
            OKBtn.repaint();
            return;
        }

        OKBtn.setEnabled(true);
        OKBtn.setToolTipText(null);

        OKBtn.repaint();
    }

    private void setSupportedSrcTypesInTable() {
        sourceAttributeSelectionTable.setSupportedIDType();
    }

    private void setSupportedTgtTypesInTable() {
        Set<DataSourceWrapper> sourceDss = sourceAttributeSelectionTable.getSelectedIDTypes();
        targetAttributeSelectionTable.setSupportedIDType(sourceDss);
    }

//    private void setSelectedNetworkInSrcTable() {
//        sourceAttributeSelectionTable.setSelectedNetworks(currentNetwork);
//    }

//    private Set<DataSource>[] getSupportedType() {
//        Set<DataSource>[] ret = new Set[2];
//        ret[0] = new HashSet();
//        ret[1] = ret[0];
//
//        IDMapperStack stack = IDMapperClientManager.selectedIDMapperStack();
//        try {
//            ret[0] = stack.getCapabilities().getSupportedSrcDataSources();
//            ret[1] = stack.getCapabilities().getSupportedTgtDataSources();
//        } catch (IDMapperException ex) {
//            ex.printStackTrace();
//        }
//
//        return ret;
//    }
//
//    private void defineTgtAttributes(Map<String,Byte> attrNameType) {
//        CyAttributes nodeAttributes = Cytoscape.getNodeAttributes();
//        MultiHashMapDefinition mmapDef = nodeAttributes.getMultiHashMapDefinition();
//
//        for (Map.Entry<String,Byte> entry : attrNameType.entrySet()) {
//            String attrname = entry.getKey();
//            byte attrtype = entry.getValue();
//
//            byte[] keyTypes;
//            if (attrtype==CyAttributes.TYPE_STRING) {
//                    keyTypes = null;
//            } else if (attrtype==CyAttributes.TYPE_SIMPLE_LIST ) {
//                    keyTypes = new byte[] { MultiHashMapDefinition.TYPE_INTEGER };
//            } else {
//                    keyTypes = null;
//            }
//
//            mmapDef.defineAttribute(attrname,
//                                    MultiHashMapDefinition.TYPE_STRING,
//                                    keyTypes);
//        }
//    }

    public Map<String,Set<DataSourceWrapper>> getMapSrcAttrIDTypes() {
        Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes = sourceAttributeSelectionTable.getSourceAttrType();
        return mapSrcAttrIDTypes;
    }

    public void setMapSrcAttrIDTypes(Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes) {
        sourceAttributeSelectionTable.setSourceAttrType(mapSrcAttrIDTypes);
        this.setSupportedTgtTypesInTable();
    }

    public boolean isCancelled() {
        return cancelled;
    }

    private SourceAttributeSelectionTable sourceAttributeSelectionTable;
    private TargetAttributeSelectionTable targetAttributeSelectionTable;
    private boolean cancelled = true;

    // Variables declaration - do not modify                     
    private javax.swing.JButton OKBtn;

    private class ApplySourceChangeTask extends AbstractTask {

        public ApplySourceChangeTask() {
        }

        @Override
        public void cancel() {
                // TODO Auto-generated method stub

        }

        @Override
        public void run(TaskMonitor taskMonitor) throws Exception {
                try {
                        taskMonitor.setStatusMessage("Applying...");
                        setSupportedSrcTypesInTable();
                        setSupportedTgtTypesInTable();
//                        setSelectedNetworkInSrcTable();
                        taskMonitor.setProgress(1.00);
                } catch (Exception e) {
                        taskMonitor.setProgress(1.00);
                        taskMonitor.setStatusMessage("Failed.\n");
                        e.printStackTrace();
                }
        }

    }
}


