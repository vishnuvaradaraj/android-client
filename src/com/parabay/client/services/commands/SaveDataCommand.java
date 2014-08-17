package com.parabay.client.services.commands;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataCollectionPager;
import com.parabay.client.api.services.IServiceManager;

public class SaveDataCommand extends ServiceCommand {
	
	private Map<String, Object> 	data;
	private IDataCollectionPager 	dataCollectionPager;
	
	public SaveDataCommand(Map<String, Object> data, IDataCollectionPager dataCollectionPager) {
		super(dataCollectionPager.getApp());
		this.data					= data;
		this.dataCollectionPager	= dataCollectionPager;
		this.setDataKey(dataCollectionPager.getDataKey());
	}

	@Override
	public void execute() {
		IServiceManager service = Globals.getInstance().getServiceManager();
		String kind = this.dataCollectionPager.getDataCollection().getQuery().getKind();
		service.save(this.app, 
				kind, 
				data, 
				this);
	}
	
	@Override
	protected Object mapResultObject(Object result) {

		Map<String, Object> obj = (Map<String, Object>) result;
		if (obj.containsKey("id")) {
			String id =  (String)obj.get("id");
			this.data.put("id", id);		
			this.dataCollectionPager.onUpdateObject(id, this.data);
		}
		
		return this.dataCollectionPager;
	}
}
