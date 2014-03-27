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

import java.sql.DriverManager;
import org.bridgedb.bio.BioDataSource;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.work.swing.DialogTaskManager;
import org.osgi.framework.BundleContext;

import java.util.Map;
import java.util.Properties;
import org.bridgedb.cytoscape.internal.task.ListIDMappingResourcesTaskFactory;
import org.bridgedb.cytoscape.internal.task.OpenIDMappingResourceConfigDialogTaskFactory;
import org.bridgedb.cytoscape.internal.task.OpenMainDialogTaskFactory;
import org.bridgedb.cytoscape.internal.task.AddResourceTaskFactory;
import org.bridgedb.cytoscape.internal.task.AttributeBasedIDMappingTaskFactory;
import org.bridgedb.cytoscape.internal.task.GetIDTypesTaskFactory;
import org.bridgedb.cytoscape.internal.task.RemoveResourceTaskFactory;
import org.bridgedb.cytoscape.internal.task.SelectResourceTaskFactory;
import org.cytoscape.application.CyApplicationConfiguration;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.util.swing.FileUtil;
import static org.cytoscape.work.ServiceProperties.COMMAND;
import static org.cytoscape.work.ServiceProperties.COMMAND_NAMESPACE;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskManager;

/**
 * Plugin for attribute-based ID mapping
 * 
 * 
 */
public final class BridgeDbApp extends AbstractCyActivator {
    public static Map mapSrcAttrIDTypes = null;

    public BridgeDbApp() {
        super();
    }
    
    public void start(BundleContext bc) {
        try {
            BioDataSource.init();
            
            try {
                    DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            } catch (Exception e) {
                    e.printStackTrace();
            }	
            
            CyApplicationConfiguration cyApplicationConfiguration = getService(bc, CyApplicationConfiguration.class);
            IDMapperClientManager.setCyApplicationConfiguration(cyApplicationConfiguration);
            
            IDMapperClientManager idMapperClientManager = IDMapperClientManager.getDefaultIDMapperClientManager();
            idMapperClientManager.reloadFromCytoscapeGlobalProperties();
            idMapperClientManager.cache();
            
            DialogTaskManager taskManagerServiceRef = getService(bc, DialogTaskManager.class);
            CyNetworkManager networkManagerRef = getService(bc, CyNetworkManager.class);
            CyApplicationManager cyApplicationManagerRef = getService(bc, CyApplicationManager.class);
            CySwingApplication cySwingApplicationServiceRef = getService(bc, CySwingApplication.class);
            OpenBrowser openBrowser = getService(bc,OpenBrowser.class);
            FileUtil fileUtil = getService(bc, FileUtil.class);

            registerServices(bc, cyApplicationManagerRef, networkManagerRef, cySwingApplicationServiceRef,
                    taskManagerServiceRef, openBrowser, fileUtil);
            
            IDMappingAction idMappingAction = new IDMappingAction(cyApplicationManagerRef, networkManagerRef, cySwingApplicationServiceRef,
                    taskManagerServiceRef, openBrowser, fileUtil);
            
            ManageIDMappingResourcesAction manageIDMappingResourcesAction
                    = new ManageIDMappingResourcesAction(cySwingApplicationServiceRef,
                    taskManagerServiceRef, openBrowser, fileUtil);
            
            registerService(bc, idMappingAction, CyAction.class, new Properties());
            registerService(bc, manageIDMappingResourcesAction, CyAction.class, new Properties());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void addListeners() {
//        PropertyChangeSupport pcs = Cytoscape.getPropertyChangeSupport();
//
//        pcs.addPropertyChangeListener(Cytoscape.CYTOSCAPE_INITIALIZED,
//                new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                IDMapperClientManager.reloadFromCytoscapeGlobalProperties();
//                //registerDefaultClients();
//                IDMapperClientManager.reCache();
//                mapSrcAttrIDTypes = null;
//            }
//        });
//    }

//    private void registerDefaultClients() {
//        IDMapperClientManager.reloadFromCytoscapeGlobalProperties();
//    }
    
    private void registerServices(BundleContext bc, CyApplicationManager cyApplicationManager, 
            CyNetworkManager networkManagerRef, CySwingApplication swingApp,
            TaskManager taskManager, OpenBrowser openBrowser, FileUtil fileUtil) {
        // maind dialog
        OpenMainDialogTaskFactory openMainDialogTaskFactory  = new OpenMainDialogTaskFactory(
                    cyApplicationManager, networkManagerRef, swingApp, taskManager, openBrowser, fileUtil);
        Properties props = new Properties();
        props.setProperty(COMMAND, "main dialog");
        props.setProperty(COMMAND_NAMESPACE, "bridgedb");
        registerService(bc,openMainDialogTaskFactory, TaskFactory.class, props);
        
        // source config dialog
        OpenIDMappingResourceConfigDialogTaskFactory openIDMappingSourceConfigDialogTaskFactory
                = new OpenIDMappingResourceConfigDialogTaskFactory(taskManager, openBrowser, fileUtil, null, swingApp.getJFrame());
        props = new Properties();
        props.setProperty(COMMAND, "resource config dialog");
        props.setProperty(COMMAND_NAMESPACE, "bridgedb");
        registerService(bc, openIDMappingSourceConfigDialogTaskFactory, TaskFactory.class, props);
        
        // List mapping resources
        ListIDMappingResourcesTaskFactory listIDMappingSourceTaskFactory
                = new ListIDMappingResourcesTaskFactory();
        props = new Properties();
        props.setProperty(COMMAND, "resource list");
        props.setProperty(COMMAND_NAMESPACE, "bridgedb");
        registerService(bc, listIDMappingSourceTaskFactory, TaskFactory.class, props);
        
        // Add mapping resource
        AddResourceTaskFactory addResourceTaskFactory
                = new AddResourceTaskFactory();
        props = new Properties();
        props.setProperty(COMMAND, "resource add");
        props.setProperty(COMMAND_NAMESPACE, "bridgedb");
        registerService(bc, addResourceTaskFactory, TaskFactory.class, props);
        
        // Remove mapping resource
        RemoveResourceTaskFactory removeResourceTaskFactory
                = new RemoveResourceTaskFactory();
        props = new Properties();
        props.setProperty(COMMAND, "resource remove");
        props.setProperty(COMMAND_NAMESPACE, "bridgedb");
        registerService(bc, removeResourceTaskFactory, TaskFactory.class, props);
        
        // Remove mapping resource
        SelectResourceTaskFactory selectResourceTaskFactory
                = new SelectResourceTaskFactory();
        props = new Properties();
        props.setProperty(COMMAND, "resource select");
        props.setProperty(COMMAND_NAMESPACE, "bridgedb");
        registerService(bc, selectResourceTaskFactory, TaskFactory.class, props);
        
        // Remove mapping resource
        GetIDTypesTaskFactory getIDTypesTaskFactory
                = new GetIDTypesTaskFactory();
        props = new Properties();
        props.setProperty(COMMAND, "get id types");
        props.setProperty(COMMAND_NAMESPACE, "bridgedb");
        registerService(bc, getIDTypesTaskFactory, TaskFactory.class, props);
        
        // Remove mapping resource
        AttributeBasedIDMappingTaskFactory attributeBasedIDMappingTaskFactory
                = new AttributeBasedIDMappingTaskFactory();
        props = new Properties();
        props.setProperty(COMMAND, "id mapping");
        props.setProperty(COMMAND_NAMESPACE, "bridgedb");
        registerService(bc, attributeBasedIDMappingTaskFactory, TaskFactory.class, props);
    }
}