package com.parabay.client.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IDataCollection;
import com.parabay.client.api.data.IDataCollectionPager;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.api.data.IDataEventManager;
import com.parabay.client.api.data.IDataEvent.Action;
import com.parabay.client.api.services.IServiceCommand;
import com.parabay.client.services.commands.DeleteDataCommand;
import com.parabay.client.services.commands.GetDataCommand;
import com.parabay.client.services.commands.ListDataCommand;
import com.parabay.client.services.commands.SaveDataCommand;
import com.parabay.client.services.wrappers.BaseWrapper;
import com.parabay.client.services.wrappers.DataWrapper;
import com.parabay.client.utils.Constants;
import com.parabay.client.utils.SharedUtils;

public class DataCollectionPager extends BaseAsyncDataStore implements IDataCollectionPager {

	protected String name;
	protected IDataCollection dataCollection;
	protected List<Object> data = new ArrayList<Object>();
	protected int offset = -1;
	protected int pageSize = 10;
	protected int selectedIndex = -1;

	public DataCollectionPager(String name, IDataCollection dataCollection) {
		super();
		
		this.setDataKey(this.formatDataKey(this.getApp(), name));
		
		this.name = name;
		this.setDataCollection(dataCollection);
		this.clear();
	}

	@Override
	protected IServiceCommand createCommand() {
		
		ListDataCommand list = new ListDataCommand(this, offset);
		return list;
	}

	public void setOffset(int offset, final IDataEventHandler handler) {
		
		if (this.offset != offset) {
			
			this.offset = offset;
			this.load(handler);
		}
		else {
			final IDataEvent event = new DataEvent(this.getDataKey());
			event.setValue(this);
			event.setOffset(offset);
			event.setLimit(this.pageSize);

			DeferredCommand.addCommand(new Command() {
				public void execute() {
					handler.onDataChanged(event);
				}
			});
		}
	}

	public List<Object> getData() {
		return data;
	}

	public boolean hasNextPage() {
		return true;
	}

	public boolean hasPrevPage() {
		if (0 == offset)
			return false;
		else
			return true;
	}

	public void first(IDataEventHandler handler) {
		this.setOffset(0, handler);
	}

	public void last(IDataEventHandler handler) {
		int totalSize = this.dataCollection.getTotalSize();
		this.setOffset(totalSize - (totalSize % this.pageSize), handler);
	}

	public void onUpdateSelection(Object value) {

		if (-1 != this.selectedIndex) {
			this.data.set(this.selectedIndex, value);
		} else if (null != value) {
			this.data.add(value);
			this.setSelectedIndex(this.data.indexOf(value));
		}

		final IDataEvent event = new DataEvent(this.getDataKey());
		event.setValue(value);
		event.setOffset(this.selectedIndex);
		event.setLimit(1);

		fireChangedEvent(event);
	}

	public Map<String, Object> get(String id) {

		Map<String, Object> ret = null;
		for (int i = 0; i < this.data.size(); i++) {
			Map<String, Object> item = (Map<String, Object>) this.data.get(i);

			if (item.containsKey("id")) {
				String itemId = (String) item.get("id");

				if (itemId.equals(id)) {
					ret = item;
					break;
				}
			}
		}
		return ret;
	}
	
	public void onUpdateObject(String id, Object value) {

		int index = -1;

		for (int i = 0; i < this.data.size(); i++) {
			Map<String, Object> item = (Map<String, Object>) this.data.get(i);

			if (item.containsKey("id")) {
				String itemId = (String) item.get("id");

				if (itemId.equals(id)) {
					this.data.set(i, value);
					index = i;
					break;
				}
			}
		}

		if (-1 == index) {
			this.data.add(value);
			index = this.data.indexOf(value);
		}

		final IDataEvent event = new DataEvent(this.getDataKey());
		event.setValue(value);
		event.setOffset(index);
		event.setLimit(1);

		fireChangedEvent(event);
	}

	public void onDeleteObject(String id) {

		int index = -1;
		Map<String, Object> ret = null;

		for (int i = 0; i < this.data.size(); i++) {
			Map<String, Object> item = (Map<String, Object>) this.data.get(i);

			if (item.containsKey("id")) {
				String itemId = (String) item.get("id");

				if (itemId.equals(id)) {
					index = i;
					ret = (Map<String, Object>) this.data.get(i);
					this.data.remove(i);
					break;
				}
			}
		}

		final IDataEvent event = new DataEvent(this.getDataKey());
		event.setValue(id);
		event.setOffset(index);
		event.setLimit(1);
		event.setAction(Action.DELETE);

		fireChangedEvent(event);
	}

	public void onDataAvailable(int offset, int limit) {
		
		final IDataEvent event = new DataEvent(this.getDataKey());
		event.setValue(this);
		event.setOffset(offset);
		event.setLimit(limit);
		
		fireChangedEvent(event);
	}

	public void clear() {

		this.data.clear();
		selectedIndex = -1;
		offset = -1;
	}

	public void fireChangedEvent(final IDataEvent event) {

		DeferredCommand.addCommand(new Command() {
			public void execute() {
				IDataEventManager dataEventManager = Globals
						.getInstance().getDataEventManager();
				dataEventManager.fireEvent(event);
			}
		});
	}

	public IDataCollection getDataCollection() {
		return dataCollection;
	}

	public void setDataCollection(IDataCollection dataCollection) {
		this.dataCollection = dataCollection;
	}

	public int getOffset() {
		return offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public String getName() {
		return name;
	}
	
	public static String formatDataKey(String app, String name) {
		
		String[] keys 	= { Constants.DATA, app, name };
		return SharedUtils.createKey(keys);
	}

	@Override
	public void setWrapper(BaseWrapper wrapper) {
		
		super.setWrapper(wrapper);
		
		DataWrapper dataWrapper = (DataWrapper)wrapper;

		this.data.clear();
		
		if (null != dataWrapper && dataWrapper.isValid()) {
			Map<String, Object> obj = dataWrapper.getData();	
			if (obj.containsKey("data")) {
				
				Object data =  obj.get("data");
				if (data instanceof Object[]) {
					
					Object [] arr = (Object [])data;
					this.data.addAll(Arrays.asList(arr));
				}
			}
			
			if (obj.containsKey("count")) {
				int totalSize =  (Integer)obj.get("count");
				this.getDataCollection().setTotalSize(totalSize);
			}
		}
	}

	public void deleteObject(String id, IDataEventHandler handler) {

		DeleteDataCommand getDataCmd = new DeleteDataCommand(
				id, this);
		getDataCmd.setCallback(handler);
		getDataCmd.execute();
	}

	public void getObject(String id, IDataEventHandler handler) {
		GetDataCommand deleteDataCmd = new GetDataCommand(
				id, this);
		deleteDataCmd.setCallback(handler);
		deleteDataCmd.execute();
	}

	public void saveObject(Object value, IDataEventHandler handler) {
		SaveDataCommand saveDataCmd = new SaveDataCommand(
				(Map<String, Object>) value, this);
		saveDataCmd.setCallback(handler);
		saveDataCmd.execute();
	}
	
	public void registerForNotifications(IDataEventHandler handler) {
		IDataEventManager dataEventManager = Globals
				.getInstance().getDataEventManager();
		dataEventManager.addHandler(this.getDataKey(), handler);
	}
	
	public void unRegisterForNotifications(IDataEventHandler handler) {
		IDataEventManager dataEventManager = Globals
				.getInstance().getDataEventManager();
		dataEventManager.removeHandler(this.getDataKey(), handler);
	}
}
