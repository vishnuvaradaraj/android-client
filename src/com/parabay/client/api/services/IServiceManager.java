package com.parabay.client.api.services;

import java.util.Map;

import com.parabay.client.api.data.IDataQuery;

public interface IServiceManager {

	public abstract void register(String app, Map<String, Object> userInfo, IServiceRequestHandler handler);
	
	public abstract void login(String user, String password, IServiceRequestHandler handler);

	public abstract void logout(String token, IServiceRequestHandler handler);
	
	public abstract void list(String app, IDataQuery q, int offset, int size, IServiceRequestHandler handler);

	public abstract void getPageMetadata(String app, String page, IServiceRequestHandler handler);

	public abstract void save(String app, String dataType, Map<String, Object> data, IServiceRequestHandler handler);

	public abstract void delete(String app, String dataType, String id, IServiceRequestHandler handler);

	public abstract void get(String app, String dataType, String id, IServiceRequestHandler handler);

	public abstract void getDataCollectionMetadata(String app, String name,
			IServiceRequestHandler handler);
	
	public abstract void getRootViewMaps(String app, IServiceRequestHandler handler);
	
	public void getL10n(String app, String page, IServiceRequestHandler handler);
}