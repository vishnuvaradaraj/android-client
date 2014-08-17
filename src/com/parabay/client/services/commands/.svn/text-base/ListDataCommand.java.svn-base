package com.parabay.client.services.commands;

import java.util.Map;

import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataCollectionPager;
import com.parabay.client.api.services.IServiceManager;
import com.parabay.client.services.wrappers.DataWrapper;

public class ListDataCommand extends ServiceCommand {

	private IDataCollectionPager 	dataCollectionPager;
	private int 					offset;
	private int						pageSize;
	
	public ListDataCommand(IDataCollectionPager dataCollectionPager, int offset) {
		super(dataCollectionPager.getApp());
		this.setDataKey(dataCollectionPager.getDataKey());
		this.dataCollectionPager	= dataCollectionPager;
		this.offset					= offset;
		this.pageSize				= dataCollectionPager.getPageSize();
	}

	@Override
	public void execute() {
		IServiceManager service = Globals.getInstance().getServiceManager();
		service.list(dataCollectionPager.getDataCollection().getApp(), 
				dataCollectionPager.getDataCollection().getQuery(), 
				offset, 
				pageSize, 
				this);
	}

	@Override
	protected Object mapResultObject(Object result) {
		DataWrapper wrapper = null;
		
		if (null != result) {
			wrapper = new DataWrapper((Map<String, Object>) result);
		}		
		this.dataCollectionPager.setWrapper(wrapper);
		this.dataCollectionPager.onDataAvailable(offset, pageSize);
		
		return this.dataCollectionPager;
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public IDataCollectionPager getDataCollectionPager() {
		return dataCollectionPager;
	}

	public void setDataCollectionPager(IDataCollectionPager dataCollectionPager) {
		this.dataCollectionPager = dataCollectionPager;
	}
}
