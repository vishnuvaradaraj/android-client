package com.parabay.client.data.offline;

import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataCollection;
import com.parabay.client.api.data.IDataCollectionPager;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.data.offline.IOfflineDataManager;
import com.parabay.client.api.services.IRunnable;
import com.parabay.client.api.services.IWorkItem;
import com.parabay.client.data.DataCollection;
import com.parabay.client.utils.Constants;

public class LoadDataCollection implements IRunnable {

	private String pageName;
	private IDataCollection dc;
	private IOfflineDataManager offlineDataMgr;

	private IDataCollectionPager pager;

	public LoadDataCollection(IOfflineDataManager offlineDataMgr, IDataCollection dc, String pageName) {
		super();
		this.dc = dc;
		this.pageName = pageName;
		this.offlineDataMgr = offlineDataMgr;

		this.pager = Globals.getInstance().getDataCollectionPager(
				Globals.getInstance().getApp(), pageName, dc);

	}

	public void execute(final IWorkItem workItem) {

		pager.first(new IDataEventHandler() {

			public void onDataChanged(IDataEvent event) {
				
				try {
					offlineDataMgr.getProgressCallback().setPercentageCompleted(75);
					
					if (Globals.getInstance().getWorkerQueue(Constants.DATA_SYNC).size() <= 1) {
						offlineDataMgr.getProgressCallback().setPercentageCompleted(100);
					}
				}
				finally {
					workItem.setFinished(true);
				}
				
			}
		});
	}

}
