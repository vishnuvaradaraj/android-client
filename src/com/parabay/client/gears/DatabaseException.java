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

/**
 * Exception class indicating a Gears database-related error.
 */
@SuppressWarnings("serial")
public class DatabaseException extends Exception {
  /**
   * Constructor taking a message.
   * 
   * @param message error message for this exception
   */
  public DatabaseException(String message) {
    super(message);
  }

  /**
   * Constructor taking a message and root cause.
   * 
   * @param message error message for this exception
   * @param cause the {@link Throwable} that caused this exception
   */
  public DatabaseException(String message, Throwable cause) {
    super(message, cause);
  }
}
