package com.parabay.client.services.commands;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataCollectionPager;
import com.parabay.client.api.services.IServiceManager;

public class GetDataCommand extends ServiceCommand {

	private String 					id;
	private IDataCollectionPager 	dataCollectionPager;
	
	public GetDataCommand(String id, IDataCollectionPager 	dataCollectionPager) {
		super(dataCollectionPager.getApp());
		this.id						= id;
		this.dataCollectionPager	= dataCollectionPager;
		this.setDataKey(dataCollectionPager.getDataKey());
	}

	@Override
	public void execute() {
		IServiceManager service = Globals.getInstance().getServiceManager();
		String kind = this.dataCollectionPager.getDataCollection().getQuery().getKind();
		service.get(app, kind, id, this);
	}

	@Override
	protected Object mapResultObject(Object result) {

		Map<String, Object> obj = (Map<String, Object>) result;
		Map<String, Object> ret	= null;
		
		if (obj.containsKey("data")) {
			ret =  (Map<String, Object>)obj.get("data");
			
			if (ret.containsKey("id")) {
				String id = (String)ret.get("id");
				
				if (null != this.dataCollectionPager) {
					this.dataCollectionPager.onUpdateObject(id, ret);
				}
			}				
		}
		
		return this.dataCollectionPager;
	}
}
