package com.parabay.client.utils;

import java.util.Map;

public class SharedUtils {

	public static String createKey(String []params) {
		String ret = "";
		
		for(String s : params) {
			ret += s;
			ret += ".";
		}
		
		return ret;
	}
	
	public static Object getValue(Object obj, String name) {
		
		Object ret = null;
		Map<String, Object> map = (Map<String, Object>) obj;
		
		if (null != map && 
				map.containsKey(name) && 
				null != map.get(name) ) {
			ret = map.get(name);
		}
		
		return ret;
	}
	
	public static String getStringValue(Object obj, String name) {
		return (String)getValue(obj, name);
	}
}
