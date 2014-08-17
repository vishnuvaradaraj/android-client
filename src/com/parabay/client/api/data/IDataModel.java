package com.parabay.client.api.data;

import com.parabay.client.data.offline.DataCallback;

public interface IDataModel {

	public abstract Object get(Object arg0);
	
	public abstract void get(Object arg0, DataCallback callback);

	public abstract Object put(String arg0, Object arg1);

}