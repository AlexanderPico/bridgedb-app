/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bridgedb.cytoscape.internal.task;

import org.bridgedb.cytoscape.internal.IDMapperClient;
import org.bridgedb.cytoscape.internal.IDMapperClientImpl;
import org.bridgedb.cytoscape.internal.IDMapperClientManager;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

/**
 *
 * @author gaoj
 */
public class AddResourceTask extends AbstractTask {
    // Options
    @Tunable(description="BridgeDb IDMapper class path", context="nogui")
    public String classPath = null;
    
    @Tunable(description="BridgeDb IDMapper connection string", context="nogui")
    public String connString = null;
    
    @Tunable(description="Resouce display name", context="nogui")
    public String displayName = null;

    @Tunable(description="Application name (optional) for application-specific ID mapping resources"
            + " -- do not specify if use the globel resources", context="nogui")
    public String appName = null;
    
    @Override
    public void run(TaskMonitor tm) throws Exception {
        if (connString == null || classPath==null) {
             tm.showMessage(TaskMonitor.Level.ERROR, "Class path or conn string cannot be null.");
             return;
        }

        if (displayName==null)
            displayName = connString;
        
        IDMapperClientManager idMapperClientManager = IDMapperClientManager.getIDMapperClientManager(appName);

        try {
            IDMapperClient client = new IDMapperClientImpl
                            .Builder(connString, classPath)
                            .displayName(displayName)
                            .build();
            boolean succ = idMapperClientManager.registerClient(client, true, true);
            if (succ)
                tm.showMessage(TaskMonitor.Level.INFO, "Successfully added");
            else
                tm.showMessage(TaskMonitor.Level.ERROR, "Failed to add the resource");
        } catch (Exception e) {
            tm.showMessage(TaskMonitor.Level.ERROR, "Failed to add the resource.\n" + e.getMessage());
        }
    }
    
}
