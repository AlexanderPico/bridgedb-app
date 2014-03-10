/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bridgedb.cytoscape.internal.task;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;

/**
 *
 * @author gaoj
 */
public class OpenMainDialogTaskFactory extends AbstractTaskFactory {
    private final CyApplicationManager cyApplicationManager;
    private final CySwingApplication swingApp;
    private final TaskManager taskManager;
    private final OpenBrowser openBrowser;
    private final FileUtil fileUtil;

    public OpenMainDialogTaskFactory(CyApplicationManager cyApplicationManager, CySwingApplication swingApp, TaskManager taskManager, OpenBrowser openBrowser, FileUtil fileUtil) {
        this.cyApplicationManager = cyApplicationManager;
        this.swingApp = swingApp;
        this.taskManager = taskManager;
        this.openBrowser = openBrowser;
        this.fileUtil = fileUtil;
    }
    
    @Override
    public TaskIterator createTaskIterator() {
        OpenMainDialogTask task = new OpenMainDialogTask(cyApplicationManager,
            swingApp, taskManager, openBrowser, fileUtil);
        return new TaskIterator(task);
    }
}
