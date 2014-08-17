package com.parabay.client.data;

import java.util.Map;

import com.parabay.client.api.Globals;
import com.parabay.client.api.data.IAsyncDataStore;
import com.parabay.client.api.services.IServiceCommand;
import com.parabay.client.services.commands.GetL10nCommand;
import com.parabay.client.utils.Constants;
import com.parabay.client.utils.SharedUtils;

public class L10n extends BaseAsyncDataStore implements IAsyncDataStore {

	private String page;
	private String locale;
	
	private Map<String, String> l10n;
	
	public L10n(String page) {
		super();
		
		this.page = page;		
		this.locale = Globals.getInstance().getCurrentLocale();

		this.setDataKey(formatDataKey(this.getApp(), this.page, this.locale));
	}
	
	@Override
	protected IServiceCommand createCommand() {
		
		GetL10nCommand l10nCmd = new GetL10nCommand(this);
		return l10nCmd;
	}
	
	public String getPage() {
		return page;
	}
	
	public String getLocale() {
		return locale;
	}

	public String get(String name) {
		
		String value = l10n.get(name);
		if (null == value) {
			value = name;
		}
		return l10n.get(name);
	}
	
	public static String formatDataKey(String app, String page, String locale) {
		
		String[] keys 	= { Constants.L10N, app, page, locale };
		return SharedUtils.createKey(keys);
	}
}
