package com.parabay.client.data;

import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IServerData;

public class DataEvent implements IDataEvent {
	
	private String 	type;
	
	private Object	value;
	
	private int		offset;
	private int		limit;
	
	private Action	action = Action.UPDATE;
	
	public DataEvent() {
		super();
	}
	
	public DataEvent(String type)
	{
		this.type 	= type;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	public int getStatus() {
		
		int status = 1;
		
		if (null != value && value instanceof IServerData) {
			
			IServerData data = (IServerData)value;
			status = data.getStatus();
		}		
		
		return status;
	}
}
