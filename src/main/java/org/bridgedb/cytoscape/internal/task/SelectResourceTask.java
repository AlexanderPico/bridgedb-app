/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bridgedb.cytoscape.internal.task;

import org.bridgedb.cytoscape.internal.IDMapperClient;
import org.bridgedb.cytoscape.internal.IDMapperClientManager;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

/**
 *
 * @author gaoj
 */
public class SelectResourceTask extends AbstractTask {
    @Tunable(description="BridgeDb IDMapper connection string", context="nogui")
    public String connString = null;
    
    @Tunable(description="Select or deselect", context="nogui")
    public boolean select = true;
    
    @Tunable(description="Application name (optional) for application-specific ID mapping resources"
            + " -- do not specify if use the globel resources", context="nogui")
    public String appName = null;
    
    @Override
    public void run(TaskMonitor tm) throws Exception {
        if (connString == null) {
             tm.showMessage(TaskMonitor.Level.ERROR, "conn string cannot be null.");
             return;
        }

        IDMapperClientManager idMapperClientManager = IDMapperClientManager.getIDMapperClientManager(appName);
        try {
            
            IDMapperClient client = idMapperClientManager.getClient(connString);
            if (client == null) {
                tm.showMessage(TaskMonitor.Level.ERROR, "Could not "+(select?"select":"deselect")
                        +" the specific ID mapping resource since it did not exist.");
            } else {
                idMapperClientManager.setClientSelection(client, select);
                tm.showMessage(TaskMonitor.Level.INFO, select?"selected":"deselected");
            }
        } catch (Exception e) {
            tm.showMessage(TaskMonitor.Level.ERROR, "Failed to " + (select?"select":"deselect")
                    + " the resource.\n" + e.getMessage());
        }
    }
    
}
