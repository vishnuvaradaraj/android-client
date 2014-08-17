package com.parabay.client.services;

import com.google.gwt.libideas.logging.shared.Log;
import com.parabay.client.api.services.IWorkItem;
import com.parabay.client.api.services.IRunnable;

public class WorkItem implements IWorkItem {

	protected boolean finished;
	protected boolean started;
	protected IRunnable runnable;
	
	protected String name;
	
	public WorkItem(IRunnable runnable) {
		super();
		this.finished = false;
		this.started = false;
		this.runnable = runnable;
		this.name = "Unknown";
	}

	public WorkItem(IRunnable runnable, String name) {
		super();
		this.finished = false;
		this.started = false;
		this.runnable = runnable;
		this.name = name;
	}
	
	public void run() {
		
		Log.info("Starting: " + this.name);
		
		this.setStarted(true);
		if (null != this.runnable) {
			this.runnable.execute(this);
		}
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		Log.info("Finished: " + this.name);
		this.finished = finished;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
}
