package com.parabay.client.api.data;

import java.util.List;
import java.util.Map;


public interface IDataCollectionPager extends IAsyncDataStore {

	public abstract IDataCollection getDataCollection();

	public abstract void setDataCollection(IDataCollection dataCollection);

	public abstract int getOffset();

	public abstract void setOffset(int offset,IDataEventHandler handler);

	public abstract List<Object> getData();

	public abstract int getPageSize();

	public abstract boolean hasNextPage();

	public abstract boolean hasPrevPage();

	public abstract void first(IDataEventHandler handler);

	public abstract void last(IDataEventHandler handler);

	public abstract void onDataAvailable(int offset, int limit);
	
	public abstract void setSelectedIndex(int index);

	public abstract int getSelectedIndex();
	
	public abstract void onUpdateSelection(Object value);
	public abstract void onUpdateObject(String id, Object value);
	public abstract void onDeleteObject(String id);

	public abstract void saveObject(Object value, IDataEventHandler handler);
	public abstract void getObject(String id, IDataEventHandler handler);
	public abstract void deleteObject(String id, IDataEventHandler handler);
	
	public void registerForNotifications(IDataEventHandler handler);
	public void unRegisterForNotifications(IDataEventHandler handler);
	
	public abstract String getName();
	public Map<String, Object> get(String id);
	
}