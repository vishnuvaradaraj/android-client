package com.parabay.client.data;

import com.parabay.client.api.data.IAsyncDataStore;
import com.parabay.client.api.services.IServiceCommand;
import com.parabay.client.services.commands.GetPageMetadataCommand;
import com.parabay.client.utils.Constants;
import com.parabay.client.utils.SharedUtils;

public class PageMetadata extends BaseAsyncDataStore implements IAsyncDataStore {

	private String page;

	public PageMetadata(String page) {
		super();
		this.page = page;
		
		this.setDataKey(formatDataKey(this.getApp(), this.page));
	}
	
	@Override
	protected IServiceCommand createCommand() {
		
	    GetPageMetadataCommand pageMetadataCmd = new GetPageMetadataCommand(this);
		return pageMetadataCmd;
	}
	
	
	public String getPage() {
		return page;
	}

	public static String formatDataKey(String app, String page) {
		
		String[] keys 	= { Constants.PAGE_METADATA, app, page };
		return SharedUtils.createKey(keys);
	}
}
