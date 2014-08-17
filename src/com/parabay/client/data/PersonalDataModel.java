package com.parabay.client.data;

import com.parabay.client.api.data.IDataModel;
import com.parabay.client.data.offline.DataCallback;

public class PersonalDataModel extends java.util.HashMap<String, Object>  implements IDataModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4864202680807732180L;

	public void get(Object arg0, DataCallback callback) {
		callback.onNewData(super.get(arg0));
	}

	@Override
	public Object put(String arg0, Object arg1) {
		return super.put(arg0, arg1);
	}

}
