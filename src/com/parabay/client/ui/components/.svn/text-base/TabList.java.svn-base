package com.parabay.client.ui.components;

import java.util.Map;

import com.google.gwt.user.client.ui.Hyperlink;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.data.RootViewMaps;

/*
 * NOT USED, TO BE DELETED.
 * 
 */
public class TabList {

	private WallToWallPanel container;

	private RootViewMaps rootViewMaps;

	public TabList(WallToWallPanel parent) {

		super();

		this.container = parent;
		this.rootViewMaps = Globals.getInstance().getRootViewMaps();
	}

	public void refresh() {

		if (rootViewMaps.isValid()) {

			Object[] arr = (Object[]) rootViewMaps.getWrapper()
					.getValue("data");

			for (int i = 0; i < arr.length; i++) {
				Map<String, Object> item = (Map<String, Object>) arr[i];

				String name = (String) item.get("name");
				String token = Globals.getInstance().getNavigationManager()
						.getTokenForPage(name);
				
				String title = this.container.l10n((String) item.get("description"));
				Hyperlink link = new Hyperlink(
						title, token);

				container.add(new PanelLabel(link, null));
			}
		} 
		
		return;
	}

	public void load(IDataEventHandler handler) {

		this.rootViewMaps.load(handler);
	}
}
