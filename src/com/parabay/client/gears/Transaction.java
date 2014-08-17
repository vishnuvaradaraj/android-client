package com.parabay.client.gears;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

public final class Transaction extends JavaScriptObject {

  protected Transaction() {
  }

  public void execute(String sqlStatement, String []args)
  throws DatabaseException {
	try {
		nativeExecuteSql(sqlStatement, Utils.toJavaScriptArray(args), null, null);
	} catch (JavaScriptException ex) {
	  throw new DatabaseException(ex.getDescription(), ex);
	}
  }

  public void execute(String sqlStatement, String[] args, SuccessCallback onSuccess, ErrorCallback onFailure)
  throws DatabaseException {
	try {
		nativeExecuteSql(sqlStatement, Utils.toJavaScriptArray(args), onSuccess,onFailure);
	} catch (JavaScriptException ex) {
	  throw new DatabaseException(ex.getDescription(), ex);
	}
  }
  
  public void executeSql(String sqlStatement, String[] args, SuccessCallback onSuccess, ErrorCallback onFailure)
  throws DatabaseException {
	try {
		nativeExecuteSql(sqlStatement, Utils.toJavaScriptArray(args), onSuccess,onFailure);
	} catch (JavaScriptException ex) {
	  throw new DatabaseException(ex.getDescription(), ex);
	}
  }
  
  
  public native void nativeExecuteSql(String sqlStatement, JavaScriptObject args, SuccessCallback onSuccess, ErrorCallback onFailure) /*-{
  	 
	  this.executeSql(sqlStatement, args, 
		  function(tx, rs) {
			  if (onSuccess) {
		      	onSuccess.@com.parabay.client.gears.SuccessCallback::onCallbackWrapper(Lcom/parabay/client/gears/Transaction;Lcom/parabay/client/gears/ResultSet;)(tx,rs);
			  }
		  },
	      function(err) {
	      	if (onFailure) {
	      		onFailure.@com.parabay.client.gears.ErrorCallback::onCallbackWrapper(Lcom/parabay/client/gears/ErrorResult;)(err);
	      	}
	      }
      );

  }-*/;  

}
