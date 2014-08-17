package com.parabay.client.utils;

/*
 * Copyright 2006 Florian Fankhauser f.fankhauser@gmail.com
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

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

/**
 * A simple JSON de- and encoder. And a json-rpc client.
 * 
 * @author Flo
 * 
 */
public class JSONCodec {

	/**
	 * Decodes a json string. Primitives are decoded to their object wrappers,
	 * json-objects are decoded to HashMaps and json-arrays are decoded to a
	 * java array of objects.
	 * 
	 * @param json
	 *            string
	 * @return decoded object
	 */
	public Object decode(String json) {
		if (json == null)
			throw new RuntimeException("Json string must not be null.");
		try {
			JSONValue value = JSONParser.parse(json);
			Object jsonObject = buildJavaObjectFromJSONValue(value);
			return jsonObject;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Object decode(JSONValue value) {
		if (value == null)
			throw new RuntimeException("Json must not be null.");
		try {
			Object jsonObject = buildJavaObjectFromJSONValue(value);
			return jsonObject;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Converts a JSONValue to a Java object.
	 * 
	 * @param value
	 * @return
	 */
	private Object buildJavaObjectFromJSONValue(JSONValue value) {
		if (value.isNull() != null) {
			return null;
		}
		if (value.isBoolean() != null) {
			return Boolean.valueOf(value.isBoolean().booleanValue());
		}
		if (value.isString() != null) {
			return value.isString().stringValue();
		}
		if (value.isNumber() != null) {
			return buildNumber(value.isNumber().toString());
		}
		if (value.isArray() != null) {
			return buildJavaArrayFromJSONArray(value.isArray());
		}
		if (value.isObject() != null) {
			return buildJavaMapFromJSONObject(value.isObject());
		}
		return null;
	}

	/**
	 * Converts a JSONObject to a Java Map.
	 * 
	 * @param value
	 *            The JSONObject
	 * @return Map
	 */
	private Map buildJavaMapFromJSONObject(JSONObject jsonObject) {
		HashMap map = new HashMap();
		Set keys = jsonObject.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			map.put(key, buildJavaObjectFromJSONValue(jsonObject.get(key)));
		}
		return map;
	}

	/**
	 * Converts a JSONArray to a Java array of objects.
	 * 
	 * @param jsonArray
	 *            The JSONArray
	 * @return Array of objects
	 */
	private Object[] buildJavaArrayFromJSONArray(JSONArray jsonArray) {
		Object[] array = new Object[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			array[i] = buildJavaObjectFromJSONValue(jsonArray.get(i));
		}
		return array;
	}

	/**
	 * Encodes a java object. Supported are primitive types in their object
	 * wrappers, arrays of primitives, arrays of objects, arrays of arrays,
	 * Lists, Maps, Sets, Vectors and Dates. You can nest objects without limit.
	 * Arrays, Lists, Sets and Vectors are encodet to json-arrays. Maps are
	 * encoded to json-objects. Dates are encoded with their getTime() method,
	 * which results in an unix-timestamp.
	 * 
	 * @param object
	 *            java object to encode
	 * @return json string
	 */
	public String encode(Object object) {
		StringBuffer buffer = new StringBuffer();
		encodeValue(buffer, object);
		return buffer.toString();
	}

	/**
	 * To add new serializable type override this method, add your serializing
	 * code and than call super.
	 * 
	 * @param buffer
	 * @param object
	 */
	protected void encodeValue(StringBuffer buffer, Object object) {
		if (object == null) {
			buffer.append("null");
			return;
		}

		if (object instanceof Boolean) {
			Boolean bool = (Boolean) object;
			buffer.append(bool.booleanValue() ? "true" : "false");
			return;
		}

		if (object instanceof String) {
			encodeString(buffer, (String) object);
			return;
		}

		if (object instanceof Number) {
			buffer.append(object.toString());
			return;
		}

		if (object instanceof Object[]) {
			encodeArray(buffer, (Object[]) object);
			return;
		}

		if (object instanceof Map) {
			encodeObject(buffer, (Map) object);
			return;
		}

		if (object instanceof List) {
			encodeList(buffer, (List) object);
			return;
		}

		if (object instanceof Set) {
			encodeSet(buffer, (Set) object);
			return;
		}

		if (object instanceof Date) {
			encodeDate(buffer, (Date) object);
			return;
		}

		if (encodeArrayOfPrimitive(buffer, object)) {
			return;
		}

		throw new RuntimeException("Not serializable: " + object);
	}

	protected void encodeSet(StringBuffer buffer, Set set) {
		encodeArray(buffer, set.toArray());
	}

	/**
	 * Dates get encoded as number of milliseconds since January 1, 1970,
	 * 00:00:00 GMT (Unix Timestamp). Thats because JSON has no date type and
	 * most languages can handle these unix timestamps easily. Override it if
	 * you prefer another encoding for dates.
	 * 
	 * @param buffer
	 * @param date
	 */
	protected void encodeDate(StringBuffer buffer, Date date) {
		encodeValue(buffer, new Long(date.getTime()));
	}

	/**
	 * Lists get encoded as arrays. Override it if you prefer another encoding
	 * for lists.
	 * 
	 * @param buffer
	 * @param list
	 */
	protected void encodeList(StringBuffer buffer, List list) {
		encodeArray(buffer, list.toArray());
	}

	private boolean encodeArrayOfPrimitive(StringBuffer buffer, Object array) {
		if (array instanceof boolean[]) {
			encodeArray(buffer, convertBooleanToObjectArray((boolean[]) array));
		} else if (array instanceof byte[]) {
			encodeArray(buffer, convertByteToObjectArray((byte[]) array));
		} else if (array instanceof char[]) {
			encodeArray(buffer, convertCharToObjectArray((char[]) array));
		} else if (array instanceof double[]) {
			encodeArray(buffer, convertDoubleToObjectArray((double[]) array));
		} else if (array instanceof float[]) {
			encodeArray(buffer, convertFloatToObjectArray((float[]) array));
		} else if (array instanceof int[]) {
			encodeArray(buffer, convertIntToObjectArray((int[]) array));
		} else if (array instanceof long[]) {
			encodeArray(buffer, convertLongToObjectArray((long[]) array));
		} else if (array instanceof short[]) {
			encodeArray(buffer, convertShortToObjectArray((short[]) array));
		} else {
			return false;
		}
		return true;
	}

	private Object[] convertShortToObjectArray(short[] s) {
		Object[] result = new Object[s.length];
		for (int i = 0; i < s.length; i++) {
			result[i] = new Short(s[i]);
		}
		return result;
	}

	private Object[] convertLongToObjectArray(long[] ls) {
		Object[] result = new Object[ls.length];
		for (int i = 0; i < ls.length; i++) {
			result[i] = new Long(ls[i]);
		}
		return result;
	}

	private Object[] convertIntToObjectArray(int[] is) {
		Object[] result = new Object[is.length];
		for (int i = 0; i < is.length; i++) {
			result[i] = new Integer(is[i]);
		}
		return result;
	}

	private Object[] convertFloatToObjectArray(float[] fs) {
		Object[] result = new Object[fs.length];
		for (int i = 0; i < fs.length; i++) {
			result[i] = new Float(fs[i]);
		}
		return result;
	}

	private Object[] convertDoubleToObjectArray(double[] ds) {
		Object[] result = new Object[ds.length];
		for (int i = 0; i < ds.length; i++) {
			result[i] = new Double(ds[i]);
		}
		return result;
	}

	private Object[] convertCharToObjectArray(char[] cs) {
		Object[] result = new Object[cs.length];
		for (int i = 0; i < cs.length; i++) {
			result[i] = new Character(cs[i]);
		}
		return result;
	}

	private Object[] convertByteToObjectArray(byte[] bs) {
		Object[] result = new Object[bs.length];
		for (int i = 0; i < bs.length; i++) {
			result[i] = new Byte(bs[i]);
		}
		return result;
	}

	private Object[] convertBooleanToObjectArray(boolean[] booleanArray) {
		Object[] result = new Object[booleanArray.length];
		for (int i = 0; i < booleanArray.length; i++) {
			result[i] = Boolean.valueOf(booleanArray[i]);
		}
		return result;
	}

	private void encodeObject(StringBuffer buffer, Map map) {
		buffer.append("{");
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			buffer.append("\"").append(key).append("\":");
			encodeValue(buffer, map.get(key));
			if (iter.hasNext()) {
				buffer.append(",");
			}
		}
		buffer.append("}");
	}

	private void encodeArray(StringBuffer buffer, Object[] objects) {
		buffer.append("[");
		for (int i = 0; i < objects.length; i++) {
			encodeValue(buffer, objects[i]);
			if (i < objects.length - 1) {
				buffer.append(",");
			}
		}
		buffer.append("]");
	}

	private void encodeString(StringBuffer buffer, String string) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			switch (c) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (c >= '\u0000' && c <= '\u001F') {
					String hex = Integer.toHexString(c);
					sb.append("\\u");
					for (int j = 0; j < 4 - hex.length(); j++) {
						sb.append('0');
					}
					sb.append(hex.toUpperCase());
				} else {
					sb.append(c);
				}
			}
		}
		buffer.append("\"").append(sb.toString()).append("\"");
	}

	private Number buildNumber(String value) {
		try {
			Integer i = new Integer(value);
			if (!value.equals(i.toString()))
				throw new RuntimeException("Not an integer");
			return i;
		} catch (Exception e) {
			try {
				Long l = new Long(value);
				if (!value.equals(l.toString()))
					throw new RuntimeException("Not a long");
				return l;
			} catch (Exception e2) {
				try {
					Double d = new Double(value);
					return d;
				} catch (Exception e3) {
				}
			}
		}
		throw new RuntimeException("Cannot parse number: " + value);
	}

}