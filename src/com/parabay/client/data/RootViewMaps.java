package com.parabay.client.data;

import java.util.Map;

import com.google.gwt.user.client.Window;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IAsyncDataStore;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.data.IDataEventManager;
import com.parabay.client.api.services.IServiceCommand;
import com.parabay.client.services.commands.GetRootViewMapsCommand;
import com.parabay.client.utils.Constants;

public class RootViewMaps extends BaseAsyncDataStore implements IAsyncDataStore {

	public RootViewMaps() {
		super();

		this.setDataKey(Constants.ROOT_VIEWS);
		
		IDataEventManager eventMgr = Globals.getInstance()
				.getDataEventManager();
		eventMgr.addHandler(Constants.USER, new IDataEventHandler() {

			public void onDataChanged(IDataEvent event) {

				//Window.alert("1. Send view map load request to server");
				load(null);
			}
		});

	}

	@Override
	protected IServiceCommand createCommand() {

		GetRootViewMapsCommand getRootViewsCmd = new GetRootViewMapsCommand(
				this);
		return getRootViewsCmd;
	}

	public Map<String, Object> getViewMapForPageURL(String page) {

		Map<String, Object> ret = null;

		if (null != page && null != this.wrapper && this.wrapper.isValid()) {

			Object[] arr = (Object[]) this.wrapper.getValue("data");

			for (int i = 0; i < arr.length; i++) {
				Map<String, Object> item = (Map<String, Object>) arr[i];

				String name = (String) item.get("url");
				if (name.equals(page)) {
					ret = item;
					break;
				}
			}
		}

		return ret;
	}
}
