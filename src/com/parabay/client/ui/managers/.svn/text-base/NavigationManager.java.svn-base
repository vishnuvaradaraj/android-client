package com.parabay.client.ui.managers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.parabay.client.api.Globals;
import com.parabay.client.api.ui.INavigationManager;
import com.parabay.client.ui.components.AboutPanel;
import com.parabay.client.ui.components.HomePanel;
import com.parabay.client.ui.components.LoginPanel;
import com.parabay.client.ui.components.PagePanel;
import com.parabay.client.ui.components.RegisterPanel;
import com.parabay.client.ui.components.SettingsPanel;
import com.parabay.client.ui.components.WallToWallPanel;
import com.parabay.client.utils.Constants;

public class NavigationManager implements INavigationManager {

	private HomePanel home;
	
	private LoginPanel loginPanel;
	
	/**
	 * Prevent repeated loads of the same token.
	 */
	String lastToken = "";

	private Map<String, WallToWallPanel> panels = new HashMap<String, WallToWallPanel>();
	
	public NavigationManager() {
		super();
	}

	public void initialize(final HomePanel home) {

		this.home = home;
		this.loginPanel = new LoginPanel(home);
		
		// Add a HistoryListener to control the application.
		History.addHistoryListener(new HistoryListener() {

			public void onHistoryChanged(String token) {

				if (lastToken.equals(token)) {
					return;
				}

				if (token != null && token.length() > 0) {
					lastToken = token;
					processHistoryToken(token);
					
				} else {
					home.enter();
				}
			}
		});

		// show the first page
		String token = History.getToken();
		if (token == null || token.length() == 0) {

			this.home.enter();

		} else {

			processHistoryToken(token);
		}
	}

	public void navigateTo(String tokenParam, WallToWallPanel parent) {
		
		if (tokenParam.length() == 0) {
			return;
		}

		Map<String, String> params = this.parseTokens(tokenParam);	
		String token = params.get(Constants.PAGE);
		if (token.equals("login") || token.equals("register")) {
			this.panels.clear();
		}
		
		if (this.panels.containsKey(tokenParam)) {
		
			WallToWallPanel panel = this.panels.get(tokenParam);
			panel.enter();
			
		}
		else {
						
			if (parent.getPage().equals(token)) {
				parent = this.getHome();
			}
			
			WallToWallPanel panel = null;
			
			if (Constants.LOGIN.equals(token)) {
				panel = this.loginPanel;
			}
			else if (Constants.REGISTER.equals(token)) {
				panel = new RegisterPanel(parent);
			}
			else if (Constants.ABOUT.equals(token)) {
				panel = new AboutPanel(parent);
			}
			else if (Constants.HOME.equals(token)) {
				panel = this.home;
			}
			else if (Constants.SETTINGS.equals(token)) {
				panel = new SettingsPanel(parent);
			}		
			else {
				panel = new PagePanel(token, parent);
			}
	
			panel.setToken(tokenParam);
			this.panels.put(tokenParam, panel);
			panel.enter();
		}
	}
	
	/**
	 * Change the application's state based on a new history token.
	 */
	private void processHistoryToken(String token) {
		
		this.navigateTo(token, this.getCurrentPanel());
	}
	
	public Map<String, String> parseTokens(String token) {
		
		Map<String, String> ret = new HashMap<String, String>();
		
		token = URL.decodeComponent(token);

		String[] params = token.split("\\|\\|");
		for (String param : params) {
			if (param.indexOf("=") != -1) {
			      String[] halves 	= param.split("=");
			      String name 		= halves[0];
			      String value 		= halves[1];
			      
			      ret.put(name, value);
			}
		}
		
		return ret;
	}
	
	public String serializeTokens(Map<String, String> params) {
		
		String ret = "";
		
		Iterator it = params.entrySet().iterator();
	    while (it.hasNext()) {
	    	
	    	if (ret.length() > 0) {
	    		ret = ret.concat("||");
	    	}
	    	
	        Map.Entry pairs = (Map.Entry)it.next();
	        ret = ret.concat(pairs.getKey() + "=" + pairs.getValue());
	    }

		return ret;
	}
	
	public String getTokenValue(String name) {
		
		String ret 					= null;

		String token 				= History.getToken();
		Map<String, String> params 	= this.parseTokens(token);
		
		ret							= params.get(name);
		
		if (null == ret) {
			
			if (name.equals(Constants.APP)) {
				ret = Constants.DEFAULT_APP;
			}
			else if (name.equals(Constants.PAGE)) {
				ret = Constants.DEFAULT_PAGE;
			}
		}
		
		return ret;
	}
	
	public String getTokenForPage(String page) {

		String token 				= History.getToken();
		Map<String, String> params 	= this.parseTokens(token);
		
		String app = this.getTokenValue(Constants.APP);
		
		params.clear();
		params.put(Constants.APP, app);
		params.put(Constants.PAGE, page);
		
		token = this.serializeTokens(params);

		return token;
	}
	
	public WallToWallPanel getCurrentPanel() {
		
		WallToWallPanel activePanel = WallToWallPanel.activePanel;
		if (null == activePanel) {
			activePanel = this.home;
		}
		return activePanel;
	}
	
	public void navigateToPage(String page) {
		
		String token = Globals.getInstance().getNavigationManager()
			.getTokenForPage(page);
		History.newItem(token);
	}

	/* Same as navigateToPage, but does not result in processHistoryToken */
	public void updatePageToken(String token) {
		
		this.lastToken = token;
		History.newItem(token);
	}
	
	public HomePanel getHome() {
		return home;
	}
	
}
