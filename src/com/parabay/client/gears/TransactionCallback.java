package com.parabay.client.gears;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;

public class TransactionCallback {

  public final void onCallbackWrapper(Transaction result) {
    UncaughtExceptionHandler exceptionHandler = GWT.getUncaughtExceptionHandler();
    if (exceptionHandler != null) {
      try {
        onCallback(result);
      } catch (Exception e) {
        exceptionHandler.onUncaughtException(e);
      }
    } else {
      onCallback(result);
    }
  }

  protected void onCallback(Transaction tx){}
}
