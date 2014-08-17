package com.parabay.client.api.data.offline;

import java.util.Date;

import com.google.gwt.gears.client.database.DatabaseException;

public interface IClientResultSet {

	public abstract void close() throws DatabaseException;

	public abstract byte getFieldAsByte(int fieldIndex)
			throws DatabaseException;

	public abstract char getFieldAsChar(int fieldIndex)
			throws DatabaseException;

	public abstract Date getFieldAsDate(int fieldIndex)
			throws DatabaseException;

	public abstract double getFieldAsDouble(int fieldIndex)
			throws DatabaseException;

	public abstract float getFieldAsFloat(int fieldIndex)
			throws DatabaseException;

	public abstract int getFieldAsInt(int fieldIndex) throws DatabaseException;

	public abstract long getFieldAsLong(int fieldIndex)
			throws DatabaseException;

	public abstract short getFieldAsShort(int fieldIndex)
			throws DatabaseException;

	public abstract String getFieldAsString(int fieldIndex)
			throws DatabaseException;

	public abstract int getFieldCount();

	public abstract String getFieldName(int fieldIndex)
			throws DatabaseException;

	public abstract boolean isValidRow();

	public abstract void next();

}