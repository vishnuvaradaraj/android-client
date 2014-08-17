/*
 * Copyright 2006 Google Inc.
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

package com.parabay.client.ui.validation.client;

import com.parabay.client.ui.validation.client.ErrorHandler;
import com.google.gwt.user.client.Window;

/**
 * Default Error Handler.
 */
public class DefaultErrorHandler extends ErrorHandler {

  /**
   * The default method of reporting an invalid value to the user. Uses an
   * alertBox to report the error.
   */
  public void reportError(String s) {
    Window.alert(s);
  }
}