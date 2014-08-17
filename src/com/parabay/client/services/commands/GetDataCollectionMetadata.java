package com.parabay.client.services.commands;

import java.util.Map;

import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataCollection;
import com.parabay.client.api.services.IServiceManager;
import com.parabay.client.services.wrappers.DataCollectionMetadataWrapper;

public class GetDataCollectionMetadata extends ServiceCommand {

	private IDataCollection dataCollection;
	
	public GetDataCollectionMetadata(IDataCollection dataCollection) {
		super(dataCollection.getApp());
		this.dataCollection = dataCollection;
		
		this.setDataKey( dataCollection.getDataKey() );
	}
	
	@Override
	public void execute() {
		IServiceManager service = Globals.getInstance().getServiceManager();
		service.getDataCollectionMetadata(this.getApp(), this.dataCollection.getName(), this);
	}

	@Override
	protected Object mapResultObject(Object result) {
		DataCollectionMetadataWrapper wrapper = null;
		
		if (null != result) {
			wrapper = new DataCollectionMetadataWrapper((Map<String, Object>) result);
		}
		
		this.dataCollection.setWrapper(wrapper);
		
		return this.dataCollection;
	}

	public IDataCollection getDataCollection() {
		return dataCollection;
	}

	public void setDataCollection(IDataCollection dataCollection) {
		this.dataCollection = dataCollection;
	}
	
}
