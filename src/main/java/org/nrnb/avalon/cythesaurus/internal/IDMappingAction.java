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
package org.nrnb.avalon.cythesaurus.internal;

import java.awt.event.ActionEvent;
import static org.nrnb.avalon.cythesaurus.internal.CyThesaurusPlugin.mapSrcAttrIDTypes;
import org.nrnb.avalon.cythesaurus.internal.ui.CyThesaurusDialog;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.work.TaskManager;

/**
 *
 * @author jgao
 */
    class IDMappingAction extends AbstractCyAction {
        private final CySwingApplication swingApp;
        private final CyNetworkManager cnm;
        private final TaskManager taskManager;

        private static final String APP_MENU_TITLE ="ID Mapping";
        private static final String PARENT_MENU ="Tools";
        
        public IDMappingAction(CySwingApplication swingApp, CyNetworkManager cnm,
                TaskManager taskManager) {
            super(APP_MENU_TITLE);
            setPreferredMenu(PARENT_MENU);
            this.swingApp = swingApp;
            this.cnm = cnm;
            this.taskManager = taskManager;
        }

        /**
         * This method is called when the user selects the menu item.
         */
        @Override
        public void actionPerformed(final ActionEvent ae) {
            final CyThesaurusDialog dialog = new CyThesaurusDialog(swingApp.getJFrame(), cnm, taskManager, true);
                        dialog.setLocationRelativeTo(swingApp.getJFrame());
                        dialog.setMapSrcAttrIDTypes(mapSrcAttrIDTypes);
            dialog.setVisible(true);
            //if (!dialog.isCancelled()) {
                mapSrcAttrIDTypes = dialog.getMapSrcAttrIDTypes();
            //}
        }
    }
