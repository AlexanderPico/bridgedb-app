/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bridgedb.cytoscape.internal.task;

import javax.swing.JFrame;
import org.bridgedb.cytoscape.internal.ui.BridgeDbDialog;
import org.bridgedb.cytoscape.internal.ui.IDMappingSourceConfigDialog;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskManager;
import org.cytoscape.work.TaskMonitor;

/**
 *
 * @author gaoj
 */
public class OpenIDMappingSourceConfigDialogTask extends AbstractTask { 
    private final TaskManager taskManager;
    private final OpenBrowser openBrowser;
    private final FileUtil fileUtil;
    private final BridgeDbDialog parentDialog;
    private final JFrame parentFrame;
    
    private IDMappingSourceConfigDialog srcConfDialog;

    public OpenIDMappingSourceConfigDialogTask(TaskManager taskManager, OpenBrowser openBrowser, FileUtil fileUtil, BridgeDbDialog parentDialog, JFrame parentFrame) {
        this.taskManager = taskManager;
        this.openBrowser = openBrowser;
        this.fileUtil = fileUtil;
        this.parentDialog = parentDialog;
        this.parentFrame = parentFrame;
    }

    @Override
    public void cancel() {
        if (srcConfDialog!=null) {
            srcConfDialog.setVisible(false);
            srcConfDialog.dispose();
        }
    }

    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {
            taskMonitor.setTitle("BridgeDb App");
            try {
                    taskMonitor.setStatusMessage("Initializing...");
                    if (parentDialog!=null) {
                        srcConfDialog = new IDMappingSourceConfigDialog(
                            parentDialog, taskManager, openBrowser, fileUtil, true);
                        srcConfDialog.setLocationRelativeTo(parentDialog);
                    } else {
                        srcConfDialog = new IDMappingSourceConfigDialog(
                            parentFrame, taskManager, openBrowser, fileUtil, true);
                        srcConfDialog.setLocationRelativeTo(parentFrame);
                    }
                    srcConfDialog.setVisible(true);
                    taskMonitor.setProgress(1.00);
            } catch (Exception e) {
                    taskMonitor.showMessage(TaskMonitor.Level.ERROR, "Failed to open BridgeDb config dialog.");
                    taskMonitor.setProgress(1.00);
                    e.printStackTrace();
            }
    }

}
