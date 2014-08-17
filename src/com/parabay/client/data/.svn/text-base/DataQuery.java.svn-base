package com.parabay.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parabay.client.api.data.IDataQuery;

public class DataQuery extends HashMap<String, Object> implements IDataQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3199578430274073937L;
	
	public DataQuery() {
		this.put("kind",  "");
		this.put("columns", new ArrayList<String>());
		this.put("filters", new ArrayList< Map<String, Object> >());
		this.put("orders", 	new ArrayList<String>());
	}
	
	public String getKind() {
		return (String)this.get("kind");
	}

	public void setKind(String kind) {
		this.put("kind",  kind);
	}

	@SuppressWarnings("unchecked")
	public List<String> getColumns() {
		return (List<String>)this.get("columns");
	}

	@SuppressWarnings("unchecked")
	public List< Map<String, Object> > getFilters() {
		return (List< Map<String, Object> >)this.get("filters");
	}

	public void addFilter(String condition, Object param)
	{
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("condition", condition);
		item.put("param", param);
		
		this.getFilters().add(item);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getOrders() {
		return (List<String>)this.get("orders");
	}

}
