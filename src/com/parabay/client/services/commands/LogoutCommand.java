package com.parabay.client.services.commands;

import java.util.Map;

import com.parabay.client.api.Globals;
import com.parabay.client.api.services.IServiceManager;
import com.parabay.client.services.wrappers.UserInfoWrapper;
import com.parabay.client.utils.Constants;

public class LogoutCommand extends ServiceCommand {

	private String token;

	public LogoutCommand(String token) {
		super(Globals.getInstance().getApp());
		
		this.setDataKey(Constants.USER);
		this.token = token;
	}

	@Override
	public void execute() {

		IServiceManager service = Globals.getInstance().getServiceManager();
		service.logout(token, this);
	}

	@Override
	protected Object mapResultObject(Object result) {

		UserInfoWrapper wrapper = null;
		Globals.getInstance().getDatabaseCacheModel().put("userToken", null);
		return wrapper;
	}
}
