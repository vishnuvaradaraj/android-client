package com.parabay.client.services.wrappers;

import java.util.Map;

import com.parabay.client.utils.JSONCodec;
import com.parabay.client.utils.SharedUtils;

public class PageMetadataWrapper extends BaseWrapper {

	private Map<String, Object>		pageMetadata;
	
	public PageMetadataWrapper(Map<String, Object> data) {
		super(data);
		
		this.pageMetadata = (Map<String, Object>) this.getValue("page_metadata");
	}

	public Object[] getDataQueries() {
		Object [] ret = null;
		if (null != this.pageMetadata) {
			ret = (Object[]) this.pageMetadata.get("data_queries");
		}
		return ret;
	}

	public Object[] getRootViewMaps() {
		Object [] ret = null;
		if (null != this.pageMetadata) {
			ret = (Object[]) this.pageMetadata.get("root_view_maps");
		}
		return ret;
	}

	public Object getViewMap() {
		Object ret = null;
		if (null != this.pageMetadata) {
			ret = (Object) this.pageMetadata.get("view_map");
		}
		return ret;
	}

	public Object getViewDefinition() {
		Object ret = null;
		if (null != this.pageMetadata) {
			ret = (Object) this.pageMetadata.get("view_definition");
		}
		return ret;
	}
	
	public MobileLayoutWrapper getMobileLayout() {
		MobileLayoutWrapper ret = null;
		
		Object viewDef = this.getViewDefinition();
		if (null != viewDef) {
			String jsonLayout = SharedUtils.getStringValue(viewDef, "mobile_layout");
			
			if (null != jsonLayout) {
				JSONCodec codec = new JSONCodec();
				Map<String, Object> layout = (Map<String, Object>) codec.decode(jsonLayout);
				ret = new MobileLayoutWrapper(layout);
			}
		}
		
		return ret;
	}
}
