package com.parabay.client.ui.components;

import java.util.Map;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.ui.INavigationManager;
import com.parabay.client.data.RootViewMaps;
import com.parabay.client.utils.Constants;

public class HomePanel extends WallToWallPanel {

	private RootViewMaps rootViewMaps;
	
	public HomePanel() {

		super("Home", null);

		this.setPage(Constants.HOME);		
		addStyleName("HomePanel");

		setEditCommand(l10n("Settings"), l10n("Edit settings"), new Command() {
			public void execute() {
				Globals.getInstance().getNavigationManager().navigateToPage("settings");
			}
		});
		
		this.rootViewMaps = Globals.getInstance().getRootViewMaps();
		
		this.startDataLoad(Constants.ROOT_VIEWS);
		this.rootViewMaps.load(new IDataEventHandler() {

			public void onDataChanged(IDataEvent event) {
				endDataLoad(event);
			}
		});
	}


	@Override
	public void refresh() {

		this.clear();
		
		if (this.rootViewMaps.isValid()) {

			Object[] arr = (Object[]) rootViewMaps.getWrapper()
					.getValue("data");

			for (int i = 0; i < arr.length; i++) {
				Map<String, Object> item = (Map<String, Object>) arr[i];

				final String name = (String) item.get("name");
				String title = l10n((String) item.get("description"));
				
				PanelLabel label = new PanelLabel(title, new Command() {
			        public void execute() {
			        	INavigationManager navMgr = Globals.getInstance().getNavigationManager();
			        	navMgr.navigateToPage(name);
			        }
				});
				add(label);
			}
		} 
	}


	public PanelLabel getLabel() {
		throw new RuntimeException(
				"This should not be called on the root panel");
	}

	@Override
	protected String getShortTitle() {
		return l10n("Home");
	}
}
