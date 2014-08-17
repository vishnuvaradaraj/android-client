package com.parabay.client.data;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IAsyncDataStore;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.data.IDataEventManager;
import com.parabay.client.api.services.IServiceCommand;
import com.parabay.client.services.wrappers.BaseWrapper;

/* setWrapper needs to be called by Command,
 * createCommand needs to return a command object that satisfies the above criteria.
 */
public class BaseAsyncDataStore implements IAsyncDataStore {

	protected BaseWrapper wrapper;
	protected String dataKey;
	protected String app;

	public BaseAsyncDataStore() {
		super();
		this.wrapper = null;
		this.app = Globals.getInstance().getApp();
	}

	public int getStatus() {
		int status = -1;

		if (null != this.wrapper) {
			status = this.wrapper.getStatus();
		}

		return status;
	}

	public BaseWrapper getWrapper() {
		return wrapper;
	}

	public void setWrapper(BaseWrapper wrapper) {
		this.wrapper = wrapper;
	}

	protected IServiceCommand createCommand() {
		return null;
	}

	public void load(final IDataEventHandler handler) {

		if (handler != null) {
			// register for future events also
			IDataEventManager eventMgr = Globals.getInstance()
					.getDataEventManager();
			eventMgr.addHandler(this.dataKey, handler);
		}

		if (this.getStatus() != 0) {
			
			IAsyncDataStore asyncDataStore = (IAsyncDataStore) Globals.getInstance()
					.getModel().get(this.dataKey);
			IServiceCommand cmd = this.createCommand();
			cmd.execute();
			
		}
		else if (null != handler){

			// data available locally.
			final IDataEvent event = new DataEvent(this.dataKey);
			event.setValue(this);

			DeferredCommand.addCommand(new Command() {
				public void execute() {
					handler.onDataChanged(event);
				}
			});
		}
		
	}

	public boolean isValid() {
		return (this.getStatus() == 0);
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}
}
