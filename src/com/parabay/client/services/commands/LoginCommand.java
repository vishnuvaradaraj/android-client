package com.parabay.client.services.commands;

import java.util.Map;

import com.parabay.client.api.Globals;
import com.parabay.client.api.services.IServiceManager;
import com.parabay.client.services.wrappers.UserInfoWrapper;
import com.parabay.client.utils.Constants;

public class LoginCommand extends ServiceCommand {

	private String user;
	private String password;
	
	public LoginCommand(String user, String password) {
		super(Globals.getInstance().getApp());
		this.setDataKey(Constants.USER);
		
		this.user		= user;
		this.password	= password;
	}

	@Override
	public void execute() {

		IServiceManager service = Globals.getInstance().getServiceManager();
		service.login(this.user, this.password, this);
	}

	@Override
	protected Object mapResultObject(Object result) {

		UserInfoWrapper wrapper = null;
		
		if (null != result) {
			wrapper = new UserInfoWrapper((Map<String, Object>) result);
			
			Globals.getInstance().getDatabaseCacheModel().put("userToken", wrapper.getToken());
		}
		return wrapper;
	}


}
