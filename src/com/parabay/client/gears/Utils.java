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
import com.google.gwt.core.client.JsArrayString;

/**
 * A utility class for moving arrays between Java and JavaScript.
 */
public class Utils {
  /**
   * Converts a JavaScript array of strings to a Java array of strings.
   */
  public static String[] toJavaArray(JsArrayString jsArray) {
    String[] urls = new String[jsArray.length()];
    for (int i = 0; i < jsArray.length(); i++) {
        urls[i] = jsArray.get(i);
    }
    return urls;
  }
  
  /**
   * Converts a Java array of strings to a JavaScript array of strings.
   */
  public static JsArrayString toJavaScriptArray(String[] elements) {
    JsArrayString array = JavaScriptObject.createArray().cast();
    if (null != elements) {
	    for (int i = 0; i < elements.length; ++i) {
	      array.set(i, elements[i]);
	    }
    }
    return array;
  }
}
