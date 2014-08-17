package com.parabay.client.services;

import java.util.Map;

import com.parabay.client.api.data.IDataQuery;
import com.parabay.client.api.services.IServiceManager;
import com.parabay.client.api.services.IServiceRequestHandler;

public class OfflineServiceManager implements IServiceManager {

	
	public void delete(String app, String dataType, String id,
			IServiceRequestHandler handler) {
		

	}

	
	public void get(String app, String dataType, String id,
			IServiceRequestHandler handler) {
		

	}

	
	public void getDataCollectionMetadata(String app, String name,
			IServiceRequestHandler handler) {
		

	}

	
	public void getL10n(String app, String page, IServiceRequestHandler handler) {
		

	}

	
	public void getPageMetadata(String app, String page,
			IServiceRequestHandler handler) {
		

	}

	
	public void getRootViewMaps(String app, IServiceRequestHandler handler) {
		

	}

	
	public void list(String app, IDataQuery q, int offset, int size,
			IServiceRequestHandler handler) {
		

	}

	public void register(String app, Map<String, Object> userInfo, IServiceRequestHandler handler) {
		
	}
	
	public void login(String user, String password,
			IServiceRequestHandler handler) {
		

	}

	
	public void logout(String token, IServiceRequestHandler handler) {
		

	}

	
	public void save(String app, String dataType, Map<String, Object> data,
			IServiceRequestHandler handler) {
		

	}

}
