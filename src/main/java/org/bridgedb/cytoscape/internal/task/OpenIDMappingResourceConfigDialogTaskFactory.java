/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bridgedb.cytoscape.internal.task;

import javax.swing.JFrame;
import org.bridgedb.cytoscape.internal.ui.BridgeDbDialog;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;

/**
 *
 * @author gaoj
 */
public class OpenIDMappingResourceConfigDialogTaskFactory extends AbstractTaskFactory {
    private final TaskManager taskManager;
    private final OpenBrowser openBrowser;
    private final FileUtil fileUtil;
    private final BridgeDbDialog parentDialog;
    private final JFrame parentFrame;

    public OpenIDMappingResourceConfigDialogTaskFactory(TaskManager taskManager, OpenBrowser openBrowser, FileUtil fileUtil, BridgeDbDialog parentDialog, JFrame parentFrame) {
        this.taskManager = taskManager;
        this.openBrowser = openBrowser;
        this.fileUtil = fileUtil;
        this.parentDialog = parentDialog;
        this.parentFrame = parentFrame;
    }
    
    @Override
    public TaskIterator createTaskIterator() {
        OpenIDMappingResourceConfigDialogTask task = new OpenIDMappingResourceConfigDialogTask(
                taskManager, openBrowser, fileUtil, parentDialog, parentFrame);
        return new TaskIterator(task);
    }
}
