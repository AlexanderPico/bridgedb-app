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

import org.bridgedb.bio.BioDataSource;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.work.swing.DialogTaskManager;
import org.osgi.framework.BundleContext;

import java.util.Map;
import java.util.Properties;
import org.cytoscape.application.CyApplicationConfiguration;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.util.swing.FileUtil;

/**
 * Plugin for attribute-based ID mapping
 * 
 * 
 */
public final class BridgeDbApp extends AbstractCyActivator {
    public static Map mapSrcAttrIDTypes = null;
    public static double VERSION = 1.31;

    public BridgeDbApp() {
        super();
    }
    
    public void start(BundleContext bc) {
        try {
            BioDataSource.init();
            //addListeners();
            
            CyApplicationConfiguration cyApplicationConfiguration = getService(bc, CyApplicationConfiguration.class);

            IDMapperClientManager.setCyApplicationConfiguration(cyApplicationConfiguration);
            IDMapperClientManager.reloadFromCytoscapeGlobalProperties();
            IDMapperClientManager.cache();

            //BridgeDbNamespace.register(BridgeDbNamespace.NAME);
            
            DialogTaskManager taskManagerServiceRef = getService(bc, DialogTaskManager.class);
            CyApplicationManager cyApplicationManagerRef = getService(bc, CyApplicationManager.class);
            CySwingApplication cySwingApplicationServiceRef = getService(bc, CySwingApplication.class);
            CyNetworkManager cyNetworkManagerServiceRef = getService(bc, CyNetworkManager.class);
            OpenBrowser openBrowser = getService(bc,OpenBrowser.class);
            FileUtil fileUtil = getService(bc, FileUtil.class);
            
            IDMappingAction idMappingAction = new IDMappingAction(cyApplicationManagerRef, cySwingApplicationServiceRef,
                    cyNetworkManagerServiceRef, taskManagerServiceRef, openBrowser, fileUtil);
            
            registerService(bc, idMappingAction, CyAction.class, new Properties());
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
}