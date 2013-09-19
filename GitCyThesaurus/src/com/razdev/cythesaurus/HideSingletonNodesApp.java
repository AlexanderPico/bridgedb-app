package com.razdev.cythesaurus;
import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;

/*
 * 2013-8-10:上午10:54:22
 * Keen
 */

/**
 * Main App <br>
 * Class Next we create the skeleton code which is the same for all simple apps
 * and looks as follows. Note that in this sample java class, we do not put our
 * code in a package, so all our classses will be in a single directory. <br>
 * 2013-8-10:上午10:54:22
 * 
 * @author Keen<br>
 */
public class HideSingletonNodesApp extends AbstractCySwingApp {
	public HideSingletonNodesApp(CySwingAppAdapter adapter) {
		super(adapter);
		// Add any customization here!
		adapter.getCySwingApplication().addAction(new MenuAction(adapter));
	}
}
