package com.parabay.client.services.commands;

import java.util.Map;

import com.parabay.client.api.Globals;
import com.parabay.client.api.services.IServiceManager;
import com.parabay.client.data.L10n;
import com.parabay.client.services.wrappers.L10nWrapper;

public class GetL10nCommand extends ServiceCommand {

	private L10n l10nData;
	
	public GetL10nCommand(L10n l10nData) {
		super(l10nData.getApp());
		
		this.l10nData = l10nData;
		this.setDataKey(l10nData.getDataKey());
	}

	@Override
	public void execute() {
		IServiceManager service = Globals.getInstance().getServiceManager();
		service.getL10n(app, this.l10nData.getPage(), this);
	}

	@Override
	protected Object mapResultObject(Object result) {
		L10nWrapper wrapper = null;
		
		if (null != result) {
			wrapper = new L10nWrapper((Map<String, Object>) result);
		}		
		this.l10nData.setWrapper(wrapper);
		
		return this.l10nData;
	}
}
