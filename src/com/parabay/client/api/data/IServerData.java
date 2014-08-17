package com.parabay.client.api.data;

public interface IServerData {

	public void load(final IDataEventHandler handler);
	public int getStatus();
	public boolean isValid();

	public String getApp();
	public void setApp(String app);
}
