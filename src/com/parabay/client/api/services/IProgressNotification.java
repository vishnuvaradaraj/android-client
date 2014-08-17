package com.parabay.client.api.services;

public interface IProgressNotification {

	public abstract void setMessage(String message);
	public abstract void setPercentageCompleted(int percentage);
	
}
