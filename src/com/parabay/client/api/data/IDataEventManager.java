package com.parabay.client.api.data;


public interface IDataEventManager {

	public abstract void addHandler(String type, IDataEventHandler handler);

	public abstract void fireEvent(IDataEvent event);

	public abstract void removeHandler(String eventKey,
			IDataEventHandler handler);

}