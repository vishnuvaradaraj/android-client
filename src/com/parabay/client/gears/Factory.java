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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Factory class used to create all other Gears objects.
 */
public final class Factory {


  public Factory() {
    // Required for overlay types
  }

  /**
   * Creates a new {@link Database} instance.
   * 
   * @return a new {@link Database} instance
   */
  public Database createDatabase(String dbName) {
    return create(dbName);
  }

  /**
   * Creates an instance of the specified Gears object.
   * 
   * @param <T> Gears object type to return
   * @param className name of the object to create
   * @return an instance of the specified Gears object
   */
  private native <T extends JavaScriptObject> T create(String dbName) /*-{
  	
	var mydb = null;
	
	try {
		if ($wnd.openDatabase) {
			mydb = $wnd.openDatabase(dbName, '1.0', dbName, 65536);
		}
	} catch(e) {
		//fatal error.
	}
    
    return mydb;
    
  }-*/;
}
