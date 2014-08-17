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

import java.util.Date;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

public final class ResultSet extends JavaScriptObject {
    
  protected ResultSet() {
    // Required for overlay types
  }

  protected native int uncheckedSize() /*-{
  	return this.rows.length;
  }-*/;
 
  public int size() throws DatabaseException {
	  try {
	      return uncheckedSize();
		} catch (JavaScriptException ex) {
		  throw new DatabaseException(ex.getMessage(), ex);
		}
  }

  public native boolean isValidRow() /*-{
	  return (null != this.row);
	}-*/;

	public native void reset() /*-{
	  this.current = 0;
	  this.row = null;
	  if (this.rows && this.current < this.rows.length) {
			this.row = this.rows.item(this.current);
	  }	
	}-*/;
	
	public native void next() /*-{
		this.row = null;
		if (this.rows && this.current < this.rows.length) {
			this.row = this.rows.item(this.current++);
		}	
	}-*/;

  public byte getFieldAsByte(String fieldName) throws DatabaseException {
    return (byte) getFieldAsDouble(fieldName);
  }

  public char getFieldAsChar(String fieldName) throws DatabaseException {
    try {
      return uncheckedGetFieldAsChar(fieldName);
    } catch (JavaScriptException ex) {
      throw new DatabaseException(ex.getMessage(), ex);
    }
  }

  public Date getFieldAsDate(String fieldName) throws DatabaseException {
    try {
      long value = (long) uncheckedGetFieldAsDate(fieldName);
      return new Date(value);
    } catch (JavaScriptException ex) {
      throw new DatabaseException(ex.getMessage(), ex);
    }
  }

  public double getFieldAsDouble(String fieldName) throws DatabaseException {
    try {
      return uncheckedGetFieldAsDouble(fieldName);
    } catch (JavaScriptException ex) {
      throw new DatabaseException(ex.getMessage(), ex);
    }
  }

  public float getFieldAsFloat(String fieldName) throws DatabaseException {
    return (float) getFieldAsDouble(fieldName);
  }

  public int getFieldAsInt(String fieldName) throws DatabaseException {
    return (int) getFieldAsDouble(fieldName);
  }

  public long getFieldAsLong(String fieldName) throws DatabaseException {
    return (long) getFieldAsDouble(fieldName);
  }

  public short getFieldAsShort(String fieldName) throws DatabaseException {
    return (short) getFieldAsDouble(fieldName);
  }

  public String getFieldAsString(String fieldName) throws DatabaseException {
    try {
      return uncheckedGetFieldAsString(fieldName);
    } catch (JavaScriptException ex) {
      throw new DatabaseException(ex.getMessage(), ex);
    }
  }

  private native char uncheckedGetFieldAsChar(String fieldName) /*-{
    var val = this.row[fieldName];
    if (val == null) {
      return 0;
    }

    if (typeof val == 'string') {
      return val.charCodeAt(0);
    } else {
      return val;
    }
  }-*/;

  private native double uncheckedGetFieldAsDate(String fieldName) /*-{
    var val = this.row[fieldName];
    if (val == null) {
      return -1;
    } else {
      var d = new Date(val);
      return d.getTime();
    }
  }-*/;

  private native double uncheckedGetFieldAsDouble(String fieldName) /*-{
    var val = this.row[fieldName];
    return val == null ? 0 : Number(val);
  }-*/;

  private native String uncheckedGetFieldAsString(String fieldName) /*-{
    var val = this.row[fieldName];
    return val == null ? null : String(val);
  }-*/;

}