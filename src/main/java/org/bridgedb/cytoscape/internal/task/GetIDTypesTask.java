/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bridgedb.cytoscape.internal.task;

import java.util.HashSet;
import java.util.Set;
import org.bridgedb.cytoscape.internal.IDMapperClientManager;
import org.bridgedb.cytoscape.internal.util.DataSourceWrapper;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.util.ListSingleSelection;

/**
 *
 * @author gaoj
 */
public class GetIDTypesTask extends AbstractTask {
    @Tunable(description="Application name (optional) for application-specific ID mapping resources"
            + " -- do not specify if use the globel resources", context="nogui")
    public String appName = null;
    
//    @Tunable(description="source or target ID type", context="nogui")
//    public ListSingleSelection typeOfIdType = new ListSingleSelection("source", "target", "both");
    
    @Override
    public void run(TaskMonitor tm) throws Exception {
        IDMapperClientManager idMapperClientManager = IDMapperClientManager.getIDMapperClientManager(appName);
        try {
            StringBuilder message = new StringBuilder();
            Set<DataSourceWrapper> srcDss = idMapperClientManager.getSupportedSrcTypes();
            message.append("There are ").append(srcDss.size()).append(" supported source ID types:\n");
            for (DataSourceWrapper ds : srcDss) {
                message.append("\t").append(ds.value()).append("\n");
            }
            message.append("\n");
            Set<DataSourceWrapper> tgtDss = idMapperClientManager.getSupportedTgtTypes();
            message.append("There are ").append(tgtDss.size()).append(" supported target ID types:\n");
            for (DataSourceWrapper ds : tgtDss) {
                message.append("\t").append(ds.value()).append("\n");
            }
            tm.showMessage(TaskMonitor.Level.INFO, message.toString());
        } catch (Exception e) {
            tm.showMessage(TaskMonitor.Level.ERROR, "Failed");
        }
    }
}
