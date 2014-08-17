package com.parabay.client.api.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.http.client.URL;
import com.parabay.client.ui.components.HomePanel;
import com.parabay.client.ui.components.WallToWallPanel;

public interface INavigationManager {

	public abstract void initialize(final HomePanel home);

	public abstract void navigateTo(String token, WallToWallPanel parent);

	public abstract String getTokenValue(String name);
	
	public abstract String getTokenForPage(String page);
	
	public WallToWallPanel getCurrentPanel();
	public void navigateToPage(String page);
	
	public HomePanel getHome() ;

	public Map<String, String> parseTokens(String token);

	public String serializeTokens(Map<String, String> params);
	
	public void updatePageToken(String page);
}