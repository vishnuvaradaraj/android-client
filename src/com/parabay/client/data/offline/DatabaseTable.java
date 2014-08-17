package com.parabay.client.data.offline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.libideas.logging.shared.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.parabay.client.api.data.IDataEvent;
import com.parabay.client.api.data.IDataEventHandler;
import com.parabay.client.data.DataCollection;
import com.parabay.client.gears.Database;
import com.parabay.client.gears.DatabaseException;
import com.parabay.client.gears.ResultSet;
import com.parabay.client.gears.SuccessCallback;
import com.parabay.client.gears.Transaction;
import com.parabay.client.gears.TransactionCallback;

public class DatabaseTable {
	
	protected String name;
	protected Database db;
	
	protected DatabaseColumn key;
	protected DatabaseColumn []columns;
	
	protected String createSQL;
	protected String insertSQL;
	protected String updateSQL;
	protected String deleteSQL;
	protected String deleteAllSQL;
	protected String selectAllSQL;
	protected String selectSQL;
	
	public DatabaseTable(String name, Database db) {
		super();
		this.name = name;
		this.db = db;
	}

	public void initialize(DatabaseColumn []columns) {
		this.columns = columns;
		this.generateStatements();
	}
	
	public void initialize(final DataCollection dc) {
		
		DeferredCommand.addCommand(new Command() {
		      public void execute() {
					dc.load(new IDataEventHandler() {

						public void onDataChanged(IDataEvent event) {
							
						}
					});
		      }
		    });		
	}
	
	public void createTable() throws DatabaseException {
		
		db.transaction(new TransactionCallback() {
			protected void onCallback(Transaction tx){
				try {
					tx.execute(createSQL, null);

				} catch (DatabaseException e) {
					Log.severe(e.getMessage());
				}
				
			};
		});
	}
	
	public void insert(Map<String, Object> o) throws DatabaseException {
		
		List argsList = new ArrayList();
		for(DatabaseColumn col : columns) {
			
			String value = (String) o.get(col.name);
			argsList.add(value);
		}

		final String []args = new String[argsList.size()];
		argsList.toArray(args);
		
		db.transaction(new TransactionCallback() {
			protected void onCallback(Transaction tx){
				try {
					tx.execute(insertSQL, args);

				} catch (DatabaseException e) {
					Log.severe(e.getMessage());
				}
				
			};
		});
	}
	
	public void update(Map<String, Object> o) throws DatabaseException {
		
		List argsList = new ArrayList();
		for(DatabaseColumn col : columns) {
			
			String value = (String) o.get(col.name);
			argsList.add(value);
		}

		final String []args = new String[argsList.size()];
		argsList.toArray(args);
		
		db.transaction(new TransactionCallback() {
			protected void onCallback(Transaction tx){
				try {
					tx.execute(updateSQL, args);

				} catch (DatabaseException e) {
					Log.severe(e.getMessage());
				}
				
			};
		});
		
	}

	public void delete(Map<String, Object> o) throws DatabaseException {
		
		String id = (String) o.get(this.key.name);
		
		final String [] args = { id };
		db.transaction(new TransactionCallback() {
			protected void onCallback(Transaction tx){
				try {
					tx.execute(deleteSQL, args);

				} catch (DatabaseException e) {
					Log.severe(e.getMessage());
				}
				
			};
		});
	}
	
	public void deleteAll() throws DatabaseException {

		db.transaction(new TransactionCallback() {
			protected void onCallback(Transaction tx){
				try {
					tx.execute(deleteAllSQL, null);

				} catch (DatabaseException e) {
					Log.severe(e.getMessage());
				}
				
			};
		});
		
	}

	public void findAll(final DataCallback callback) throws DatabaseException {
				
		db.transaction(new TransactionCallback() {
			protected void onCallback(Transaction tx){
				final List<Object> rowsList = new ArrayList<Object>();

				try {
					tx.execute(selectAllSQL, null, new SuccessCallback() {
						protected void onCallback(Transaction result, ResultSet rs){
							
							rs.reset();
							while (rs.isValidRow()) {
								try {
									Map<String, Object> o = new HashMap<String, Object>();
									
									for(DatabaseColumn col : columns) {
										
										Object value = rs.getFieldAsString(col.name);
										o.put(col.name, value);
										
									}
								
									rowsList.add(o);
									
								} catch (DatabaseException e) {
									Log.severe(e.getMessage());
								}
								
								rs.next();
							}
							
							
						}
					}, null);

				} catch (DatabaseException e) {
					Log.severe(e.getMessage());
				}
				
				if (callback != null)  {
					callback.onNewData(rowsList);
				}

			};
		});

	}

	public void find(final String key, final DataCallback callback) throws DatabaseException {
		
		db.transaction(new TransactionCallback() {
			protected void onCallback(Transaction tx){
				try {
					String []args = { key };
					
					tx.execute(selectSQL, args, new SuccessCallback() {
						protected void onCallback(Transaction result, ResultSet rs){
							
							rs.reset();
							if (rs.isValidRow()) {
								try {
									Map<String, Object> o = new HashMap<String, Object>();
									
									for(DatabaseColumn col : columns) {
										
										Object value = rs.getFieldAsString(col.name);
										o.put(col.name, value);
										
									}
									
									if (null != callback) {
										callback.onNewData(o);
									}
									
								} catch (DatabaseException e) {
									Log.severe(e.getMessage());
								}
							}
							
							
						}
					}, null);

				} catch (DatabaseException e) {
					Log.severe(e.getMessage());
				}
				
			};
		});
		
	}
	
	protected void generateStatements() {
		
		String insertParams = "";

		createSQL = "CREATE TABLE IF NOT EXISTS " + name + " (";
		insertSQL = "INSERT INTO " + name + " (";
		updateSQL = "UPDATE " + name + " SET ";
		selectAllSQL =  "SELECT * FROM " + name;
		
		for(DatabaseColumn col : columns) {
			
			String column = col.name;
			String field  = col.type;
			
			if (col.isKey) {
				this.key = col;
			}
			
			insertSQL += column + ",";
			insertParams += "?,";
			updateSQL += column + "=?,";	
			createSQL += column + " " + getSQLType(field) + ",";
		}

        createSQL = createSQL.substring(0, createSQL.length()-1) + ")";
        insertParams = insertParams.substring(0, insertParams.length()-1) + ")";
        
        insertSQL = insertSQL.substring(0, insertSQL.length()-1) + ") VALUES (" + insertParams;
        insertSQL = insertSQL.substring(0, insertSQL.length()-1) + ")";
        
		updateSQL = updateSQL.substring(0, updateSQL.length()-1);
		updateSQL += " WHERE " + this.key.name + "= ?";

		deleteAllSQL = "DELETE FROM " + name;
		deleteSQL = "DELETE FROM " + name + " WHERE " + this.key.name + "= ?";
		
		selectSQL = "SELECT * FROM " + name + " WHERE " + this.key.name + "= ?";
		
		return;
	}

	protected String getSQLType(String asType) {
		
		if (asType == "integer" || asType == "uint")
			return "INTEGER";
		else if (asType == "Number")
			return "REAL";
		else
			return "TEXT";				
	}
}
