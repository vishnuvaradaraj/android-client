package com.parabay.client.services.commands;

import java.util.HashMap;
import java.util.Map;

import com.parabay.client.api.Globals;
import com.parabay.client.api.services.IServiceManager;
import com.parabay.client.services.wrappers.UserInfoWrapper;
import com.parabay.client.utils.Constants;

public class RegisterUserCommand extends ServiceCommand {

	private String email;
	private String password;
	
	public RegisterUserCommand(String email, String password) {
		super(Globals.getInstance().getApp());
		this.setDataKey(Constants.USER);
		
		this.email		= email;
		this.password	= password;
	}

	@Override
	public void execute() {

		Map<String, Object> obj = new HashMap<String, Object>();
		
		String firstName = email;
		if (email.indexOf('@') != -1) {
			firstName = email.substring(0, email.indexOf('@'));
		}
		
		obj.put("name", email);
		obj.put("first-name", firstName);
		obj.put("email", email);
		obj.put("password", password);
		
		IServiceManager service = Globals.getInstance().getServiceManager();
		service.register(this.app, obj, this);
	}

	@Override
	protected Object mapResultObject(Object result) {

		UserInfoWrapper wrapper = null;
		
		if (null != result) {
			wrapper = new UserInfoWrapper((Map<String, Object>) result);
		}
		return wrapper;
	}


}
