package com.parabay.client.ui.components;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataCollection;
import com.parabay.client.api.data.IDataCollectionPager;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.ui.IDisplayComponent;
import com.parabay.client.api.ui.IDisplayHost;
import com.parabay.client.data.PageMetadata;
import com.parabay.client.services.wrappers.PageMetadataWrapper;
import com.parabay.client.utils.SharedUtils;

public class PagePanel extends WallToWallPanel {

	private IDisplayComponent displayComponent;
	private PageMetadata pageMetadata;

	private Map<String, IDataCollectionPager> mapDataPagers;

	public PagePanel(String name, WallToWallPanel parent) {
		super("Parabay", parent);
		this.setPage(name);

		this.pageMetadata = Globals.getInstance().getPageMetadataForViewMap(
				Globals.getInstance().getApp(),name);
		this.mapDataPagers = new HashMap<String, IDataCollectionPager>();

		this.startDataLoad(this.pageMetadata.getDataKey());

		final IDisplayHost host = this;
		this.pageMetadata.load(new IDataEventHandler() {
		
			public void onDataChanged(IDataEvent event) {

				Log.info("Loaded pagemetadata: " + getPage());

				PageMetadataWrapper wrapper = (PageMetadataWrapper) pageMetadata
						.getWrapper();

				Object[] dataQueries = wrapper.getDataQueries();
				if (null != dataQueries) {

					Log.info("Found queries: "
							+ String.valueOf(dataQueries.length));

					for (int i = 0; i < dataQueries.length; i++) {

						Map<String, Object> dataQuery = (Map<String, Object>) dataQueries[i];

						final IDataCollection dc = Globals.getInstance()
								.getDataCollectionForDataQuery(
										Globals.getInstance().getApp(), dataQuery);

						String pageName = getPage();
						Map<String, Object> viewDef = (Map<String, Object>) wrapper.getViewDefinition();
						if (null != viewDef) {
							
							String viewDefType = SharedUtils.getStringValue(viewDef, "type_of");
							if (null != viewDefType && viewDefType.equals("EntityEditorTab")) {
								pageName = pageName.concat("s");
							}
						}
						
						IDataCollectionPager pager = Globals.getInstance()
								.getDataCollectionPager(
										Globals.getInstance().getApp(),
										pageName, dc);

						mapDataPagers.put(pager.getName(), pager);
						
						startDataLoad(pager.getDataKey());

						/*
						 * TODO: doesn't seem to be using the data collection metadata.
						DeferredCommand.addCommand(new Command() {
						      public void execute() {
									dc.load(new IDataEventHandler() {

										public void onDataChanged(IDataEvent event) {
											
										}
									});
						      }
						    });
						*/
						
						pager.first(new IDataEventHandler() {

							public void onDataChanged(IDataEvent event) {
								endDataLoad(event);
							}
						});
					}
				}

				if (null != wrapper.getViewDefinition()) {
					
					String viewType = (String) SharedUtils.getValue(wrapper.getViewDefinition(), "type_of");
					
					displayComponent = Globals.getInstance().getPageTypeManager().createPage(viewType, host);
					displayComponent.setDataMap(mapDataPagers);
				}

				endDataLoad(event);
			}
		});
	}

	@Override
	protected String getShortTitle() {
		return l10n("Parabay");
	}

	@Override
	public void refresh() {

		Log.info("Refreshing page: " + this.getPage());

		this.clear();
		
		if (null != this.displayComponent) {
			this.displayComponent.refresh();
		}
	}
	
	public PageMetadata getPageMetadata() {
		return pageMetadata;
	}

}
