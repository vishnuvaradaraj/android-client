package com.parabay.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.parabay.client.api.Globals;
import com.parabay.client.api.ui.INavigationManager;
import com.parabay.client.ui.components.HomePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mobile implements EntryPoint {

	private HomePanel homePanel;

	private INavigationManager navMgr;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				initialize();
			}
		});
	}

	private void initialize() {
		// This function should only work once.
		if (navMgr != null) {
			return;
		}

		this.homePanel	= new HomePanel();
		this.navMgr 	= Globals.getInstance().getNavigationManager();
		this.navMgr.initialize(this.homePanel);
		
	}
}
