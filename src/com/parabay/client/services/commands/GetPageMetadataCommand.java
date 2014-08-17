package com.parabay.client.services.commands;

import java.util.Map;

import com.parabay.client.api.Globals;
import com.parabay.client.api.services.IServiceManager;
import com.parabay.client.data.PageMetadata;
import com.parabay.client.services.wrappers.MobileLayoutWrapper;
import com.parabay.client.services.wrappers.PageMetadataWrapper;

public class GetPageMetadataCommand extends ServiceCommand {

	private PageMetadata pageMetadata;
	
	public GetPageMetadataCommand(PageMetadata metadata) {
		super(metadata.getApp());
		
		this.pageMetadata	= metadata;
		this.setDataKey( metadata.getDataKey() );
	}
	
	@Override
	public void execute() {
		IServiceManager service = Globals.getInstance().getServiceManager();
		service.getPageMetadata(app, this.pageMetadata.getPage(), this);
	}

	@Override
	protected Object mapResultObject(Object result) {
		PageMetadataWrapper wrapper = null;
		
		if (null != result) {
			wrapper = new PageMetadataWrapper((Map<String, Object>) result);
		}		
		this.pageMetadata.setWrapper(wrapper);
				
		return this.pageMetadata;
	}
}
