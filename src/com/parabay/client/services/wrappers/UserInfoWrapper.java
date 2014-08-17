package com.parabay.client.services.wrappers;

import java.util.Map;

public class UserInfoWrapper extends BaseWrapper {
    
	public UserInfoWrapper(Map<String, Object> data) {
		super(data);
	}

	public String getName() {
		return (String)this.getValue("name");
	}

	public String getFirstName() {
		return (String)this.getValue("first_name");
	}

	public String getLastName() {
		return (String)this.getValue("last_name");
	}

	public String getEmail() {
		return (String)this.getValue("email");
	}

	public String getPhone() {
		return (String)this.getValue("phone");
	}

	public String getToken() {
		return (String)this.getValue("token");
	}
}
