package org.bridgedb.cytoscape.internal;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkManager;


/**
 * Creates a new menu item under Apps menu section.
 *
 */
public class MenuAction extends AbstractCyAction {
	private final CyNetworkManager cnm;
	private final CyApplicationManager swingApp;
	public MenuAction(CyApplicationManager swingApp, CyNetworkManager cnm, final String menuTitle) {
		
		super(menuTitle, swingApp, null, null);
		setPreferredMenu("Apps");
		this.cnm = cnm;
		this.swingApp = swingApp;
	}

	public void actionPerformed(ActionEvent e) {

		// Write your own function here.
		//swingApp.getCurrentNetwork();
		JOptionPane.showMessageDialog(null, swingApp.getCurrentNetwork().getDefaultNetworkTable().getTitle());
		
	}
}
