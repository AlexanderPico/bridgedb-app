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

/**
 *
 * @author gaoj
 */
public class ListIDMappingReourcesTask extends AbstractTask {
    @Override
    public void run(TaskMonitor tm) throws Exception {
        Set<IDMapperClient> clients = IDMapperClientManager.allClients();
        tm.setStatusMessage("There are "+clients.size()+" ID mapping resource(s):");

        for (IDMapperClient client : clients) {
            String connStr = client.getConnectionString();
            tm.setStatusMessage("\t"+connStr+" [class: "+client.getClassString()+"]"+(client.isSelected()?" -- selected":"") );
        }
    }
    
}
