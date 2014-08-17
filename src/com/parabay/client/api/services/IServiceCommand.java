package com.parabay.client.api.services;

import com.parabay.client.api.data.IDataEventHandler;

public interface IServiceCommand {
	public abstract void execute();

	public IDataEventHandler getCallback();

	public void setCallback(IDataEventHandler callback);
}
