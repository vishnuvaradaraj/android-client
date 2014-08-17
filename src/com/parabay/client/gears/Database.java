/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.parabay.client.gears;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

public final class Database extends JavaScriptObject {
  protected Database() {
    // Required for overlay types
  }

  public native void uncheckedTransaction(TransactionCallback callback) /*-{
    this.transaction(function(tx, rs) {
			  if (callback) {
		      	callback.@com.parabay.client.gears.TransactionCallback::onCallbackWrapper(Lcom/parabay/client/gears/Transaction;)(tx);
			  }
		  });
  }-*/;

  public void transaction(TransactionCallback callback) throws DatabaseException {
    try {
    	uncheckedTransaction(callback);
    } catch (JavaScriptException ex) {
      throw new DatabaseException(ex.getDescription(), ex);
    }
  }

}