package com.parabay.client.api.data;

public interface IDataEvent {

	public enum Action {
		CREATE, UPDATE, DELETE
	}
	
	public abstract String getType();

	public abstract void setType(String type);

	public abstract Object getValue();

	public abstract void setValue(Object value);

	public abstract int getOffset();

	public abstract void setOffset(int offset);

	public abstract int getLimit();

	public abstract void setLimit(int limit);

	public Action getAction();

	public void setAction(Action action);
	
	public int getStatus();
	
}