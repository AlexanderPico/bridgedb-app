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
    
    @Override
    public void run(TaskMonitor tm) throws Exception {
        if (connString == null) {
             tm.showMessage(TaskMonitor.Level.ERROR, "conn string cannot be null.");
             return;
        }

        try {
            IDMapperClient client = IDMapperClientManager.getClient(connString);
            if (client == null) {
                tm.showMessage(TaskMonitor.Level.ERROR, "Could not "+(select?"select":"deselect")
                        +" the specific ID mapping resource since it did not exist.");
            } else {
                IDMapperClientManager.setClientSelection(client, select);
                tm.showMessage(TaskMonitor.Level.INFO, select?"selected":"deselected");
            }
        } catch (Exception e) {
            tm.showMessage(TaskMonitor.Level.ERROR, "Failed to " + (select?"select":"deselect")
                    + " the resource.\n" + e.getMessage());
        }
    }
    
}
