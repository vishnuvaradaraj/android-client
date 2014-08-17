package com.parabay.client.services;

import java.util.Map;

import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.http.client.URL;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataQuery;
import com.parabay.client.api.services.IServiceManager;
import com.parabay.client.api.services.IServiceRequestHandler;
import com.parabay.client.utils.JSONCodec;
import com.parabay.client.utils.JSONRequest;

public class ServiceManager implements IServiceManager {

	private static native String getHostPageLocation()
	/*-{
	  var s = $doc.location.href;	
	  return s;
	}-*/;

	protected String getURLPrefix() {
		
		String ret = "http://localhost:8080";
		
		String url = getHostPageLocation();
		if (url.startsWith("http")) {
			
			int start = url.indexOf("://");
			if (start != -1) {
				String protocol = url.substring(0, start);
				url = url.substring(start + 3);
				
				String []items = url.split("/");
				if (items.length > 0) {
					ret = protocol + "://" + items[0];
				}
			}
		}
		
		//debug only hack
		if (ret.startsWith("http://localhost")) {
			ret = "http://localhost:8080";
		}
		
		return "http://parabaydata.appspot.com";
	}
	
	public void register(String app, Map<String, Object> userInfo, IServiceRequestHandler handler) {

		JSONCodec jsonUtils = new JSONCodec();
		String jsonData = jsonUtils.encode(userInfo);
		
		String url = this.getURLPrefix() + "/api/register_user?app=" + app + "&user=" + jsonData + "&callback=";
		Log.info(url);
		
		JSONRequest.get(URL.encode(url), handler);
	}	

	public void login(String user, String password, IServiceRequestHandler handler) {

		String url = this.getURLPrefix() + "/api/login?username=" + user + "&password=" + password + "&callback=";
		Log.info(url);
		
		JSONRequest.get(URL.encode(url), handler);
	}

	public void logout(String token, IServiceRequestHandler handler) {

		String url = this.getURLPrefix() + "/api/logout?token=" + token + "&callback=";
		Log.info(url);

		if (!token.equals("")) {
			JSONRequest.get(URL.encode(url), handler);
		}
		else {
			handler.onRequestComplete(null);
		}
	}
	
	public void list(String app, IDataQuery q, int offset, int size, IServiceRequestHandler handler) {

		JSONCodec jsonUtils = new JSONCodec();
		String jsonQuery = jsonUtils.encode(q);

		String token = Globals.getInstance().getToken();
		String url = this.getURLPrefix() + "/api/list/" + app + "?token=" + Globals.getInstance().getToken() + "&query=" + jsonQuery +  "&callback=";
		Log.info(url);
		
		if (!token.equals("")) {
			JSONRequest.get(URL.encode(url), handler);
		}
		else {
			handler.onRequestComplete(null);
		}	
	}

	public void save(String app, String dataType, Map<String, Object> data, IServiceRequestHandler handler) {

		JSONCodec jsonUtils = new JSONCodec();
		String jsonData = jsonUtils.encode(data);
		
		String url = this.getURLPrefix() + "/api/save/" + app + "/" + dataType + "?token=" + Globals.getInstance().getToken() + "&data=" + jsonData + "&callback=";
		Log.info(url);
		
		JSONRequest.get(URL.encode(url), handler);
	}
	
	public void delete(String app, String dataType, String id, IServiceRequestHandler handler) {

		String url = this.getURLPrefix() + "/api/delete/" + app + "/" + dataType + "/" + id + "?token=" + Globals.getInstance().getToken() + "&callback=";
		Log.info(url);

		JSONRequest.get(URL.encode(url), handler);
	}
	
	public void get(String app, String dataType, String id, IServiceRequestHandler handler) {

		String url = this.getURLPrefix() + "/api/get/" + app + "/" + dataType + "/" + id + "?token=" + Globals.getInstance().getToken() + "&callback=";
		Log.info(url);

		JSONRequest.get(URL.encode(url), handler);
	}
	
	public void getPageMetadata(String app, String page, IServiceRequestHandler handler) {
		
		String url = this.getURLPrefix() + "/api/page_metadata/" + app + "/" + page +  "?token=" + Globals.getInstance().getToken() + "&callback=";
		Log.info(url);
		
		JSONRequest.get(URL.encode(url), handler);

	}
	
	public void getDataCollectionMetadata(String app, String name, IServiceRequestHandler handler) {
		
		String url = this.getURLPrefix() + "/api/dataquery_metadata/" + app + "/" + name +  "?token=" + Globals.getInstance().getToken() + "&callback=";
		Log.info(url);
		
		JSONRequest.get(URL.encode(url), handler);

	}
	
	public void getRootViewMaps(String app, IServiceRequestHandler handler) {

		String token = Globals.getInstance().getToken();
		String url = this.getURLPrefix() + "/api/root_view_maps/" + app + "?token=" + token + "&callback=";
		Log.info(url);

		if (!token.equals("")) {
			JSONRequest.get(URL.encode(url), handler);
		}
		else {
			handler.onRequestComplete(null);
		}
	}
	
	public void getL10n(String app, String page, IServiceRequestHandler handler) {

		String url = this.getURLPrefix() + "/api/l10n/" + app + "/" + page +"?token=" + Globals.getInstance().getToken() + "&callback=";
		Log.info(url);

		JSONRequest.get(URL.encode(url), handler);
	}
};
