package com.parabay.client.services.commands;

import java.util.Map;

import com.parabay.client.api.Globals;
import com.parabay.client.api.services.IServiceManager;
import com.parabay.client.data.RootViewMaps;
import com.parabay.client.services.wrappers.RootViewMapsWrapper;

public class GetRootViewMapsCommand extends ServiceCommand {
	
	protected RootViewMaps viewMaps;
	
	public GetRootViewMapsCommand(RootViewMaps viewMaps) {
		
		super(viewMaps.getApp());
		this.setDataKey(viewMaps.getDataKey());
		this.viewMaps = viewMaps;
	}

	@Override
	public void execute() {
		IServiceManager service = Globals.getInstance().getServiceManager();
		service.getRootViewMaps(this.getApp(), this);
	}

	@Override
	protected Object mapResultObject(Object result) {
		
		RootViewMapsWrapper wrapper = null;
		
		if (null != result) {
			wrapper = new RootViewMapsWrapper((Map<String, Object>) result);
		}	
		this.viewMaps.setWrapper(wrapper);
		
		return this.viewMaps;
	}

}
