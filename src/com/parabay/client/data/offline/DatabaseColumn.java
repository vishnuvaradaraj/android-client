package com.parabay.client.data.offline;

public class DatabaseColumn {
	
	public String name;
	public String type;
	public String length;
	public boolean isKey;
	
	public DatabaseColumn() {
		
	}
	
	public DatabaseColumn(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public DatabaseColumn(String name, String type,
			boolean isKey, String length) {
		super();
		this.name = name;
		this.type = type;
		this.length = length;
		this.isKey = isKey;
	}		
}
