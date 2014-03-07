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
package org.bridgedb.cytoscape.internal;

import java.awt.event.ActionEvent;
import org.cytoscape.application.CyApplicationManager;
import static org.bridgedb.cytoscape.internal.BridgeDbApp.mapSrcAttrIDTypes;
import org.bridgedb.cytoscape.internal.ui.BridgeDbDialog;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;
import org.cytoscape.work.TaskMonitor;

/**
 *
 * @author jgao
 */
    class IDMappingAction extends AbstractCyAction {
        private final CyApplicationManager cyApplicationManager;
        private final CySwingApplication swingApp;
        private final CyNetworkManager cnm;
        private final TaskManager taskManager;
        private final OpenBrowser openBrowser;
        private final FileUtil fileUtil;

        private static final String APP_MENU_TITLE ="Map Identifiers";
        private static final String PARENT_MENU ="Tools";
        
        public IDMappingAction(CyApplicationManager cyApplicationManager, 
                CySwingApplication swingApp, CyNetworkManager cnm,
                TaskManager taskManager, OpenBrowser openBrowser, FileUtil fileUtil) {
            super(APP_MENU_TITLE);
            setPreferredMenu(PARENT_MENU);
            this.cyApplicationManager = cyApplicationManager;
            this.swingApp = swingApp;
            this.cnm = cnm;
            this.openBrowser = openBrowser;
            this.taskManager = taskManager;
            this.fileUtil = fileUtil;
        }

        /**
         * This method is called when the user selects the menu item.
         */
        @Override
        public void actionPerformed(final ActionEvent ae) {
            OpenMainDialogTask task = new OpenMainDialogTask();
            taskManager.execute((new TaskIterator(task)));
        }
                
        private class OpenMainDialogTask extends AbstractTask {            
            public OpenMainDialogTask() {
            }

            @Override
            public void cancel() {
                    // TODO Auto-generated method stub
            }

            @Override
            public void run(TaskMonitor taskMonitor) throws Exception {
                    taskMonitor.setTitle("BridgeDb App");
                    try {
                            taskMonitor.setStatusMessage("Initializing...");
                            BridgeDbDialog dialog = new BridgeDbDialog(swingApp.getJFrame(),
                                    cyApplicationManager, cnm, taskManager, openBrowser, fileUtil, false);
                                        dialog.setLocationRelativeTo(swingApp.getJFrame());
                                        dialog.setMapSrcAttrIDTypes(mapSrcAttrIDTypes);
                            dialog.setVisible(true);
                            mapSrcAttrIDTypes = dialog.getMapSrcAttrIDTypes();
                            taskMonitor.setProgress(1.00);
                    } catch (Exception e) {
                            taskMonitor.setProgress(1.00);
                            taskMonitor.setStatusMessage("Failed.\n");
                            e.printStackTrace();
                    }
            }

        }
    }
