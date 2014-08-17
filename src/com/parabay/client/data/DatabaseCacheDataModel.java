package com.parabay.client.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parabay.client.gears.DatabaseException;
import com.google.gwt.libideas.logging.shared.Log;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventManager;
import com.parabay.client.api.data.IDataModel;
import com.parabay.client.api.data.offline.IClientDatabase;
import com.parabay.client.data.offline.DataCallback;
import com.parabay.client.data.offline.DatabaseColumn;
import com.parabay.client.data.offline.DatabaseTable;
import com.parabay.client.gears.Database;
import com.parabay.client.utils.JSONCodec;

public class DatabaseCacheDataModel implements IDataModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7005671536625999522L;
	
	private DatabaseTable cacheTable;
	private JSONCodec jsonCodec = new JSONCodec();
	
	protected Map<String, Object> map = new HashMap<String, Object>();
	
	public DatabaseCacheDataModel() {
		super();
		
		try {
			Database clientDatabase = Globals.getInstance().getClientDatabase();
			
			cacheTable = new DatabaseTable("cache", clientDatabase);
			DatabaseColumn []columns = { new DatabaseColumn("key", "text", true, null), 
												new DatabaseColumn("value", "text")};
			
			cacheTable.initialize(columns);
		
			cacheTable.createTable();
			
			this.reloadFromDatabase();
			
		} catch (DatabaseException e) {
			Log.severe(e.getMessage());
		}
	}

	public void clearLocalCache() {
		map.clear();
	}
	
	public void reloadFromDatabase() {

		try {
			cacheTable.findAll(new DataCallback() {
				
				public void onError(String message, String details) {
					// TODO Auto-generated method stub
					
				}

				public void onNewData(Object data) {
					List< Map<String, Object> > rows = (List< Map<String, Object> >) data;
					
					for (Map<String, Object> o : rows) {
						map.put((String)o.get("key"), o.get("value"));
					}
				}
				
			});
		} catch (DatabaseException e) {
			Log.severe(e.getMessage());
		}

	}
	
	public void get(Object arg0, DataCallback callback) {
		
		try {
			if (map.containsKey(arg0)) {
				callback.onNewData(map.get(arg0));
			}
			else {
				cacheTable.find((String) arg0, callback);
			}
		} catch (DatabaseException e) {
			Log.severe(e.getMessage());
		}	
	}

	public Map<String, Object> getJSONObject(Object arg0) {
		Map<String, Object> o = null;
		
		String json = (String) this.get(arg0);
		if (null != json) {
			o = (Map<String, Object>) jsonCodec.decode(json);
		}
		
		return o;
	}
	
	public Object putJSONObject(String arg0, Object arg1) {
		Object ret = null;
		
		ret = jsonCodec.encode(arg1);
		ret = this.put(arg0, ret);
		
		return ret;
	}
	
	@Override
	public Object get(Object arg0) {
		return map.get(arg0);
	}
	
	@Override
	public Object put(String arg0, Object arg1) {
		
		Object ret			= map.put(arg0, arg1);
		
		Map<String, Object> o= new HashMap<String, Object>();
		o.put("key", arg0);
		o.put("value", arg1);
		
		try {
			cacheTable.insert(o);
		} catch (DatabaseException e) {
			Log.severe(e.getMessage());
		}

		IDataEvent event	= new DataEvent(arg0);
		event.setValue(arg1);
		
		IDataEventManager dataEventManager = Globals.getInstance().getDataEventManager();
		dataEventManager.fireEvent(event);
		
		return ret;
	}
	
}
