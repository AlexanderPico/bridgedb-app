package org.nrnb.avalon.cythesaurus.internal.ui;

/*******************************************************************************
 * Copyright 2010-2013 CyThesaurus developing team
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import org.nrnb.avalon.cythesaurus.internal.AttributeBasedIDMappingImpl;
import org.nrnb.avalon.cythesaurus.internal.util.DataSourceWrapper;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.cytoscape.model.CyNetwork;

import java.util.Set;
import java.util.Map;
/**
 *
 */
public class AttributeBasedIDMappingTask extends AbstractTask {
	private final CyNetwork network;
    private final Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes;
    private final Map<String, DataSourceWrapper> mapTgtAttrNameIDType;
    private final AttributeBasedIDMappingImpl service;
    private final Map<String,Byte> mapTgtAttrNameAttrType;
    
//	private TaskMonitor taskMonitor;
    private boolean success;

	/**
         * 
         * @param networks
         * @param mapSrcAttrIDTypes
         * @param mapTgtAttrNameIDType
         */
	public AttributeBasedIDMappingTask(final CyNetwork network,
                                       final Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes,
                                       final Map<String, DataSourceWrapper> mapTgtAttrNameIDType,
                                       Map<String,Byte> mapTgtAttrNameAttrType) {
		this.network = network;
        this.mapSrcAttrIDTypes = mapSrcAttrIDTypes;
        this.mapTgtAttrNameIDType = mapTgtAttrNameIDType;
        this.mapTgtAttrNameAttrType = mapTgtAttrNameAttrType;
        service = new AttributeBasedIDMappingImpl();
        success = false;
	}

	/**
	 * Executes Task.
	 */
    //@Override
	public void run(final TaskMonitor taskMonitor) {
		 taskMonitor.setTitle("ID mapping");
		 try {
			 service.setTaskMonitor(taskMonitor);
			 service.suggestTgtAttrType(network, mapTgtAttrNameAttrType);
			 service.map(mapSrcAttrIDTypes, mapTgtAttrNameIDType);
		 } catch (Exception e) {
			 taskMonitor.setProgress(1.00);
			 taskMonitor.setStatusMessage("ID mapping failed.\n");
			 e.printStackTrace();
		 }
		 success = true;
	}

	public boolean success() {
		return success;
	}


//	/**
//	 * Halts the Task: Not Currently Implemented.
//	 */
//        //@Override
//	public void halt() {
//            service.interrupt();
//	}
//
//	/**
//	 * Sets the Task Monitor.
//	 *
//	 * @param taskMonitor
//	 *            TaskMonitor Object.
//	 */
//        //@Override
//	public void setTaskMonitor(TaskMonitor taskMonitor) throws IllegalThreadStateException {
//		this.taskMonitor = taskMonitor;
//	}
//
//	/**
//	 * Gets the Task Title.
//	 *
//	 * @return Task Title.
//	 */
//        //@Override
//	public String getTitle() {
//		return new String("ID mapping");
//	}
}
