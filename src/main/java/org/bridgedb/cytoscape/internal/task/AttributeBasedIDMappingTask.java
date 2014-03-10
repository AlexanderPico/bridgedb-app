package org.bridgedb.cytoscape.internal.task;

/*******************************************************************************
 * Copyright 2010-2013 BridgeDb App developing team
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

import org.bridgedb.cytoscape.internal.AttributeBasedIDMappingImpl;
import org.bridgedb.cytoscape.internal.util.DataSourceWrapper;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import org.cytoscape.model.CyNetwork;

import java.util.Set;
import java.util.Map;
import org.cytoscape.work.ObservableTask;
/**
 *
 */
public class AttributeBasedIDMappingTask extends AbstractTask implements ObservableTask {
    private CyNetwork network;
    private Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes;
    private Map<String, DataSourceWrapper> mapTgtAttrNameIDType;
    private AttributeBasedIDMappingImpl service = new AttributeBasedIDMappingImpl();
    private Map<String,Class> mapTgtAttrNameAttrType;
    
    private boolean success = false;
    
    public AttributeBasedIDMappingTask() {
    }

	/**
         * 
         * @param networks
         * @param mapSrcAttrIDTypes
         * @param mapTgtAttrNameIDType
         */
	public AttributeBasedIDMappingTask(final CyNetwork network,
                                       final Map<String,Set<DataSourceWrapper>> mapSrcAttrIDTypes,
                                       final Map<String, DataSourceWrapper> mapTgtAttrNameIDType,
                                       Map<String,Class> mapTgtAttrNameAttrType) {
            this.network = network;
            this.mapSrcAttrIDTypes = mapSrcAttrIDTypes;
            this.mapTgtAttrNameIDType = mapTgtAttrNameIDType;
            this.mapTgtAttrNameAttrType = mapTgtAttrNameAttrType;
	}

	/**
	 * Executes Task.
	 */
    //@Override
	public void run(final TaskMonitor taskMonitor) {
		 taskMonitor.setTitle("Mapping identifiers ...");
		 try {
			 service.setTaskMonitor(taskMonitor);
			 service.defineTgtAttrs(network, mapTgtAttrNameAttrType);
			 service.map(network, mapSrcAttrIDTypes, mapTgtAttrNameIDType);
                         
			 taskMonitor.showMessage(TaskMonitor.Level.INFO, service.getReport());
                         success = true;
		 } catch (Exception e) {
			 taskMonitor.showMessage(TaskMonitor.Level.ERROR,"ID mapping failed.\n");
			 e.printStackTrace();
		 }
	}
        
        public boolean success() {
            return success;
        }

        public String getResults(Class type)  {
            return service.getReport();
        }
}
