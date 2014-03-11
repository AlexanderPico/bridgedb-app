/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bridgedb.cytoscape.internal.task;

import java.util.Set;
import org.bridgedb.cytoscape.internal.IDMapperClient;
import org.bridgedb.cytoscape.internal.IDMapperClientManager;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

/**
 *
 * @author gaoj
 */
public class ListIDMappingReourcesTask extends AbstractTask {
    @Tunable(description="Application name (optional) for application-specific ID mapping resources"
            + " -- do not specify if use the globel resources", context="nogui")
    public String appName = null;
    
    @Override
    public void run(TaskMonitor tm) throws Exception {
        Set<IDMapperClient> clients = IDMapperClientManager.getIDMapperClientManager(appName).allClients();
        tm.setStatusMessage("There are "+clients.size()+" ID mapping resource(s):");

        for (IDMapperClient client : clients) {
            String connStr = client.getConnectionString();
            tm.setStatusMessage("\t"+connStr+" [class: "+client.getClassString()+"]"+(client.isSelected()?" -- selected":"") );
        }
    }
    
}
