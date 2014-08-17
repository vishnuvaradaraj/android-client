package com.parabay.client.data.offline;

import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataCollection;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.data.offline.IOfflineDataManager;
import com.parabay.client.api.services.IRunnable;
import com.parabay.client.api.services.IWorkItem;
import com.parabay.client.data.DataCollection;
import com.parabay.client.services.WorkItem;
import com.parabay.client.services.wrappers.DataCollectionMetadataWrapper;
import com.parabay.client.utils.Constants;

public class LoadDataCollectionMetadata implements IRunnable {

	private IDataCollection dc;
	private String pageName;
	private IOfflineDataManager offlineDataMgr;
	
	public LoadDataCollectionMetadata(IOfflineDataManager offlineDataMgr, IDataCollection dc, String pageName) {
		super();
		this.dc = dc;
		this.pageName = pageName;
		this.offlineDataMgr = offlineDataMgr;
	}

	public void execute(final IWorkItem workItem) {

		dc.load(new IDataEventHandler() {

			public void onDataChanged(IDataEvent event) {
				
				try {
					Globals.getInstance().getDatabaseCacheModel().putJSONObject(event.getType(), ((DataCollection) event
							.getValue()).getWrapper().getData());
					
					offlineDataMgr.getProgressCallback().setPercentageCompleted(50);
					
					DataCollectionMetadataWrapper wrapper = (DataCollectionMetadataWrapper) ((DataCollection) event
							.getValue()).getWrapper();
					
					LoadDataCollection ldc = new LoadDataCollection(offlineDataMgr, dc, pageName);
					WorkItem wi = new WorkItem(ldc, "LoadData: " + pageName);
					Globals.getInstance().getWorkerQueue(Constants.DATA_SYNC).add(wi);
				}
				finally {
					workItem.setFinished(true);
				}

			}
		});
	}

}
