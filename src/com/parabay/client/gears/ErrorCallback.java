package com.parabay.client.gears;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;

public class ErrorCallback {

  public ErrorCallback() {
	  
  }
  
  public final void onCallbackWrapper(ErrorResult err) {
    UncaughtExceptionHandler exceptionHandler = GWT.getUncaughtExceptionHandler();
    if (exceptionHandler != null) {
      try {
        onCallback(err);
      } catch (Exception e) {
        exceptionHandler.onUncaughtException(e);
      }
    } else {
      onCallback(err);
    }
  }

  protected void onCallback(ErrorResult err){}
}
