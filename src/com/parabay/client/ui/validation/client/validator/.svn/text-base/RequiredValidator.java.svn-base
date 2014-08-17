package com.parabay.client.ui.validation.client.validator;

import com.parabay.client.ui.validation.client.ErrorHandler;
import com.parabay.client.ui.validation.client.Subject;

public class RequiredValidator extends BuiltInValidator {

	
	public RequiredValidator() {
		super();
	}

	@Override
	public void checkValid(Subject element, ErrorHandler handler) {

		String s = element.getValue().toString().trim();
	    if (null == s || s.length()==0) {
	    	handler.reportError("Required field");
	    	return;
	    }
	    
	    handler.reportError(null);
	}

}
