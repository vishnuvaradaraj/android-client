package com.parabay.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.Timer;
import com.parabay.client.api.services.IWorkItem;

public class WorkerQueue extends ArrayList<WorkItem> {

	private IWorkItem current;

	final Timer t = new Timer() {
		public void run() {

			if (current != null && !current.isFinished()) {
				return;
			}

			if (size() == 0) {
				return;
			}

			IWorkItem p = get(0);
			if (p.isFinished()) {
				remove(0);
			} else if (!p.isStarted()) {
				current = p;
				p.run();
			}
		}
	};

	public WorkerQueue() {
		t.scheduleRepeating(100);
	}

	@Override
	public boolean add(WorkItem o) {
		if (contains(o)) {
			return false;
		}
		return super.add(o);
	}
}
