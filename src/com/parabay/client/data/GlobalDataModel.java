package com.parabay.client.data;

import java.util.HashMap;

import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventManager;
import com.parabay.client.api.data.IDataModel;
import com.parabay.client.data.offline.DataCallback;
import com.parabay.client.data.offline.DatabaseTable;

public class GlobalDataModel extends HashMap<String, Object> implements IDataModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7005671536625999522L;
	
	DatabaseTable cacheTable;
	
	public GlobalDataModel() {
		super();
	}

	@Override
	public Object get(Object arg0) {
		
		return super.get(arg0);
	}

	@Override
	public Object put(String arg0, Object arg1) {
		
		Object ret			= super.put(arg0, arg1);

		IDataEvent event	= new DataEvent(arg0);
		event.setValue(arg1);
		
		IDataEventManager dataEventManager = Globals.getInstance().getDataEventManager();
		dataEventManager.fireEvent(event);
		
		return ret;
	}

	public void get(Object arg0, DataCallback callback) {
		assert false;
	}
	
}
