package com.parabay.client.ui.managers;

import java.util.Map;

import com.parabay.client.api.ui.IDisplayComponent;
import com.parabay.client.api.ui.IDisplayHost;
import com.parabay.client.api.ui.IPageTypeManager;
import com.parabay.client.ui.components.entities.EntityEditor;
import com.parabay.client.ui.components.entities.EntityList;

public class PageTypeManager implements IPageTypeManager {

	private Map<String, IDisplayHost> mapPages;

	public PageTypeManager() {
		super();
	}

	public IDisplayComponent createPage(String type, IDisplayHost parent) {
		
		IDisplayComponent ret = null;
		
		if (type.equals("EntityListTab")) {
			ret = new EntityList(parent);
		}
		else if (type.equals("EntityEditorTab")) {
			ret = new EntityEditor(parent);
		}
		
		return ret;
	}
}
