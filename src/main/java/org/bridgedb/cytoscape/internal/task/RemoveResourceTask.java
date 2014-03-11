/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bridgedb.cytoscape.internal.task;

import org.bridgedb.cytoscape.internal.IDMapperClientManager;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

/**
 *
 * @author gaoj
 */
public class RemoveResourceTask extends AbstractTask {
    @Tunable(description="BridgeDb IDMapper connection string", context="nogui")
    public String connString = null;
    @Tunable(description="Application name (optional) for application-specific ID mapping resources"
            + " -- do not specify if use the globel resources", context="nogui")
    public String appName = null;
    @Override
    public void run(TaskMonitor tm) throws Exception {
        if (connString == null) {
             tm.showMessage(TaskMonitor.Level.ERROR, "conn string cannot be null.");
             return;
        }

        try {
            if (IDMapperClientManager.getIDMapperClientManager(appName).removeClient(connString)) {
                tm.showMessage(TaskMonitor.Level.INFO, "Successfully removed");
            } else {
                tm.showMessage(TaskMonitor.Level.ERROR, "Could not remove. The specific ID mapping resource might not exist.");
            }
        } catch (Exception e) {
            tm.showMessage(TaskMonitor.Level.ERROR, "Failed to remove the resource.\n" + e.getMessage());
        }
    }
    
}
