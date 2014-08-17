package com.parabay.client.gears;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;

public class SuccessCallback {

  public SuccessCallback() {
	  
  }
  
  public final void onCallbackWrapper(Transaction tx, ResultSet rs) {
    UncaughtExceptionHandler exceptionHandler = GWT.getUncaughtExceptionHandler();
    if (exceptionHandler != null) {
      try {
        onCallback(tx, rs);
      } catch (Exception e) {
        exceptionHandler.onUncaughtException(e);
      }
    } else {
      onCallback(tx, rs);
    }
  }

  protected void onCallback(Transaction result, ResultSet rs){}
}
