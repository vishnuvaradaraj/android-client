package com.parabay.client.api.data;

public interface IDataCollection extends IAsyncDataStore {
	
	public enum PageStatus {
		LOADING, READY, ERROR
	}
	
	public abstract String getName();
	
	public abstract IDataQuery getQuery();
	public abstract void setQuery(IDataQuery query);
	
	public abstract int getTotalSize();
	public abstract void setTotalSize(int totalSize);
}