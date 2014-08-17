package com.parabay.client.data;

import java.util.Map;

import com.parabay.client.api.data.IDataCollection;
import com.parabay.client.api.data.IDataQuery;
import com.parabay.client.api.services.IServiceCommand;
import com.parabay.client.services.commands.GetDataCollectionMetadata;
import com.parabay.client.utils.Constants;
import com.parabay.client.utils.SharedUtils;

public class DataCollection extends BaseAsyncDataStore implements IDataCollection {

	private String name;
	private IDataQuery query;
	private int totalSize;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5742859946490593555L;

	public DataCollection(String name) {

		this.query = new DataQuery();
		this.name = name;
		this.setDataKey(formatDataKey(this.app, this.name));
	}

	public DataCollection(Map<String, Object> dataQuery) {
				
		this.name = (String) dataQuery.get("name");
		this.query = new DataQuery();
		this.query.setKind((String)dataQuery.get("type_of"));
		this.setDataKey(formatDataKey(this.app, this.name));
	}
	
	public IDataQuery getQuery() {
		return query;
	}

	public void setQuery(IDataQuery query) {
		this.query = query;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	protected IServiceCommand createCommand() {
		
		GetDataCollectionMetadata dataCollectionMetadataCmd = new GetDataCollectionMetadata(this);
		return dataCollectionMetadataCmd;
	}
	
	public static String formatDataKey(String app, String name) {
		
		String[] keys = { Constants.DATA_QUERY_METADATA, app, name };
		return SharedUtils.createKey(keys);
	}
}
