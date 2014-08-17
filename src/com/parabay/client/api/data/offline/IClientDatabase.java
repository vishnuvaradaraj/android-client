package com.parabay.client.api.data.offline;

import com.google.gwt.gears.client.database.DatabaseException;
import com.google.gwt.gears.client.database.ResultSet;

public interface IClientDatabase {

	public abstract void close() throws DatabaseException;

	public abstract ResultSet execute(String sqlStatement, String... args)
			throws DatabaseException;

	public abstract int getLastInsertRowId();

	public abstract int getRowsAffected();

	public abstract void open();

	public abstract void open(String name);
}