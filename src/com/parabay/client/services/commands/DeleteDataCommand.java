package com.parabay.client.services.commands;

import java.util.Map;

import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataCollectionPager;
import com.parabay.client.api.services.IServiceManager;

public class DeleteDataCommand extends ServiceCommand {

	private String 					id;
	private IDataCollectionPager 	dataCollectionPager;
	
	public DeleteDataCommand(String id, IDataCollectionPager 	dataCollectionPager) {
		super(dataCollectionPager.getApp());
		this.id						= id;
		this.dataCollectionPager	= dataCollectionPager;
		this.setDataKey(dataCollectionPager.getDataKey());
	}

	@Override
	public void execute() {
		IServiceManager service = Globals.getInstance().getServiceManager();
		
		String kind = this.dataCollectionPager.getDataCollection().getQuery().getKind();
		service.delete(app, kind, id, this);
	}

	@Override
	protected Object mapResultObject(Object result) {

		Map<String, Object> obj = (Map<String, Object>) result;
		if (obj.containsKey("status")) {
			int status = (Integer)obj.get("status");
				
			if (0 == status && null != this.dataCollectionPager) {
				this.dataCollectionPager.onDeleteObject(id);
			}			
		}
		
		return this.dataCollectionPager;
	}	
}
