package com.parabay.client.data.offline;

import java.util.List;

public interface DataCallback {

	public void onNewData(Object data);
		
	public void onError(String message, String details);
}
