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
import java.sql.DriverManager;

import org.cytoscape.application.CyApplicationManager;
import org.bridgedb.cytoscape.internal.task.OpenMainDialogTaskFactory;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.work.TaskManager;

/**
 *
 * @author jgao
 */
    class IDMappingAction extends AbstractCyAction {
        private final CyApplicationManager cyApplicationManager;
        private final CyNetworkManager cnm;
        private final CySwingApplication swingApp;
        private final TaskManager taskManager;
        private final OpenBrowser openBrowser;
        private final FileUtil fileUtil;
        
        public IDMappingAction(CyApplicationManager cyApplicationManager, CyNetworkManager cnm,
                CySwingApplication swingApp,TaskManager taskManager,
                OpenBrowser openBrowser, FileUtil fileUtil) {
            super("Map Identifiers");
            setPreferredMenu(FinalStaticValues.PARENT_MENU);
            this.setMenuGravity((float)0.0);
            this.cyApplicationManager = cyApplicationManager;
            this.cnm = cnm;
            this.swingApp = swingApp;
            this.openBrowser = openBrowser;
            this.taskManager = taskManager;
            this.fileUtil = fileUtil;
        }

        /**
         * This method is called when the user selects the menu item.
         */
        @Override
        public void actionPerformed(final ActionEvent ae) {
        	try {
	        	DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
	        } catch (Exception e) {
	        	e.printStackTrace();
		    }	
            OpenMainDialogTaskFactory openMainDialogTaskFactory  = new OpenMainDialogTaskFactory(
                    cyApplicationManager, cnm, swingApp, taskManager, openBrowser, fileUtil);
            taskManager.execute(openMainDialogTaskFactory.createTaskIterator());
        } 
    }
