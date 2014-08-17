package com.parabay.client.services.wrappers;

import java.util.Map;

import com.parabay.client.utils.SharedUtils;

public class DataCollectionMetadataWrapper extends BaseWrapper{

	private Map<String, Object>		dataCollMetadata;
	
	public DataCollectionMetadataWrapper(Map<String, Object> data) {
		super(data);
		
		this.dataCollMetadata = (Map<String, Object>) this.getValue("dataquery_metadata");
	}

	public Map<String, Object> findEntityMetadata(String name) {
		Map<String, Object> em = null;
		
		Map<String, Object> [] objects = (Map<String, Object>[]) this.getEntityMetadatas();
		for(Map<String, Object> obj : objects) {
			String emName = (String) SharedUtils.getValue(obj, "name");
			if (null != emName && name.equals(emName)) {
				em = obj;
				break;
			}
		}
		
		return em;
	}

	public Map<String, Object> findEntityPropertyMetadata(Map<String, Object> e, String name) {
		
		Map<String, Object> ep = null;
		
		Map<String, Object> [] objects = (Map<String, Object>[]) SharedUtils.getValue(e, "entity_property_metadatas");
		for(Map<String, Object> obj : objects) {
			String epName = (String) SharedUtils.getValue(obj, "name");
			if (null != epName && name.equals(epName)) {
				ep = obj;
				break;
			}
		}
		
		return ep;
	}

	public Object[] getEntityMetadatas() {
		Object [] ret = null;
		if (null != this.dataCollMetadata) {
			ret = (Object[]) this.dataCollMetadata.get("entity_metadatas");
		}
		return ret;
	}

	public Object[] getEnumerations() {
		Object [] ret = null;
		if (null != this.dataCollMetadata) {
			ret = (Object[]) this.dataCollMetadata.get("enumerations");
		}
		return ret;
	}

	public Object[] getEntityRelations() {
		Object [] ret = null;
		if (null != this.dataCollMetadata) {
			ret = (Object[]) this.dataCollMetadata.get("entity_relations");
		}
		return ret;
	}
}
