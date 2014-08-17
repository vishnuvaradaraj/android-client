package com.parabay.client.services.commands;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.services.IServiceChainedCommand;
import com.parabay.client.api.services.IServiceCommand;
import com.parabay.client.api.services.IServiceRequestHandler;
import com.parabay.client.data.DataEvent;
import com.parabay.client.utils.JSONCodec;

public class ServiceCommand implements IServiceCommand, IServiceRequestHandler,
		IServiceChainedCommand {

	protected IServiceCommand nextCommand;
	protected Object previousResult;
	protected IDataEventHandler callback;
	protected String dataKey;
	protected String app;

	protected JSONCodec jsonCodec = new JSONCodec();

	public ServiceCommand(String app) {
		super();
		this.app = app;
	}

	public void execute() {
		//Log.warning("execute invoked on base service command class");
	}

	public void onRequestComplete(final JavaScriptObject json) {
		DeferredCommand.addCommand(new Command() {
		      public void execute() {
		    	  Object ret = onRequestCompleteHelper(dataKey, json);
		    	  onDone(ret);
		      }});
	}

	protected Object mapResultObject(Object result) {
		return result;
	}

	protected Object onRequestCompleteHelper(String key, JavaScriptObject json) {

		Map<String, Object> obj = null;

		if (null != json) {
			obj = (Map<String, Object>) jsonCodec.decode(new JSONObject(json));
		}

		Object mappedValue = this.mapResultObject(obj);
		Globals.getInstance().getModel().put(key, mappedValue);

		return mappedValue;
	}

	protected void processDone(Object result) {

		if (null != this.callback) {
			IDataEvent event = new DataEvent(this.dataKey);
			event.setValue(result);

			this.callback.onDataChanged(event);
		}

		if (null != this.nextCommand) {
			if (this.nextCommand instanceof IServiceChainedCommand) {
				IServiceChainedCommand next = (IServiceChainedCommand) this.nextCommand;
				next.setPreviousResult(result);
			}
			this.nextCommand.execute();
		}
	}

	protected void onDone(final Object result) {

		DeferredCommand.addCommand(new Command() {
			public void execute() {
				processDone(result);
			}
		});
	}

	public IServiceCommand getNextCommand() {
		return nextCommand;
	}

	public void setNextCommand(IServiceCommand nextCommand) {
		this.nextCommand = nextCommand;
	}

	public Object getPreviousResult() {
		return previousResult;
	}

	public void setPreviousResult(Object previousResult) {
		this.previousResult = previousResult;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public IDataEventHandler getCallback() {
		return callback;
	}

	public void setCallback(IDataEventHandler callback) {
		this.callback = callback;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

}
