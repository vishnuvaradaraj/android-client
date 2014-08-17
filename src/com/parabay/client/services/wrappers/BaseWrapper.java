package com.parabay.client.services.wrappers;

import java.util.Map;

import com.parabay.client.utils.SharedUtils;

public class BaseWrapper {

	private Map<String, Object>		data;

	private int						status;
	
	public BaseWrapper(Map<String, Object> data) {
		super();
		this.status	= -1;
		this.data 	= data;
		
		if (null != this.getValue("status")) {
			this.status	= (Integer)this.getValue("status");
		}
	}
	
	public boolean isValid() {
		return (0 == status);
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public Object getValue(String name) {
		return SharedUtils.getValue(this.data, name);
	}

	public String getStringValue(String name) {
		return (String) this.getValue(name);
	}
	
	public Map<String, Object> getData() {
		return data;
	}
	
}
