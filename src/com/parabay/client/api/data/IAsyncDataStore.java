package com.parabay.client.api.data;

import com.parabay.client.services.wrappers.BaseWrapper;

public interface IAsyncDataStore extends IServerData {
	
	public String getDataKey();
	public void setDataKey(String dataKey);
	public BaseWrapper getWrapper();
	public void setWrapper(BaseWrapper wrapper);

}
