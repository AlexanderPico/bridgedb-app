package org.nrnb.avalon.cythesaurus.internal;

import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.service.util.AbstractCyActivator;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		
		CyApplicationManager cyApplicationManager = getService(context, CyApplicationManager.class);
		CyNetworkManager cyNetworkManagerServiceRef = getService(context, CyNetworkManager.class);
		
		MenuAction action = new MenuAction(cyApplicationManager, cyNetworkManagerServiceRef, "Hello World App");
		
		Properties properties = new Properties();
		
		registerAllServices(context, action, properties);
	}

}
