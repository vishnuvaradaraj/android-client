package com.parabay.client.api.data.offline;

import com.parabay.client.api.services.IProgressNotification;

public interface IOfflineDataManager {

	public abstract void syncDownload(IProgressNotification progressCallback);

	public abstract void syncUpload(IProgressNotification progressCallback);

	public abstract IProgressNotification getProgressCallback();
	
}