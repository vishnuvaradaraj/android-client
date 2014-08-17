package com.parabay.client.services.wrappers;

import java.util.Map;

public class MobileLayoutWrapper extends BaseWrapper {
	
	public MobileLayoutWrapper(Map<String, Object> data) {
		super(data);
	}

	public Object []getGroups() {
		Object [] ret = null;
		ret = (Object[]) this.getData().get("groups");
		
		return ret;
	}
	
	public Object []getFields() {
		Object [] ret = null;
		ret = (Object[]) this.getData().get("fields");
		
		return ret;
	}
	
}
