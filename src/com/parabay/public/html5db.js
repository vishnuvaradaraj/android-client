
function initHtml5db() {
	if(window.console) {
					console.log("initHtml5db");
	}
	// Add Compatibility with the Gears DB
	if(!window.openDatabase) {
		window.openDatabase = function (name) {
			
			if(!window.google || !window.google.gears) {
				throw "Google Gears required."
			}

			var handle = google.gears.factory.create('beta.database');
			handle.open(name);
			var db = new HTML5DatabaseEmulator(handle)
			return db
		}
	}
}

// Wrap the gears db db with something that looks like an html5 db
function HTML5DatabaseEmulator(handle) {
	this.gearsDb = handle;
	
	this.transaction = function (func) {
			var tx = new HTML5TransactionEmulator(this)
			func(tx)
	}
};

// Simulate the html5 transaction interface (currently without transactions :))
function HTML5TransactionEmulator(database) {
	this.database = database;
	
	this.executeSql = function (sql,args,onSuccess,onFailure) {
			var me = this;
			var resultSet;
			try {
				if(window.console) {
					console.log("SQL: "+sql+" Args: "+args)
				}
				/// XXX run this inside a worker to really become async
				var rs = this.database.gearsDb.execute(sql, args);
				resultSet = new HTML5ResultSetEmulator(this.database,rs)
				resultSet.initializeResultSet();

			} catch(e) {
				if(window.console) {
					console.log(e + "\nSQL: "+sql + "\nArgs: "+args)
				}
				if(onFailure) {
					onFailure(me, e)
				} else {
					throw e
				}
			};
			if(resultSet) {
				if(onSuccess) {
					onSuccess(me, resultSet)
				}
			}
	}
};

// Emulate html5 result sets
function HTML5ResultSetEmulator(database, rs) {
	this.database = database;
	this.resultSet = rs;
	this.insertId;
	this.rowsAffected;
	this.rows;
	
	this.initializeResultSet = function () {
		var rs = this.resultSet;
		this.insertId = this.database.gearsDb.lastInsertRowId;
		var rows = [];
		rows.item = function (i) {
			return this[i]
		};
		var names = [];
		var fieldCount = rs.fieldCount();
		for(var i = 0; i < fieldCount; i++) {
			names.push(rs.fieldName(i))
		}
		while (rs.isValidRow()) {
			var row = {};
			row.length = fieldCount;
			for(var i = 0; i < names.length; i++) {
				var name = names[i];
				row[name] = rs.fieldByName(name)
			}
			for(var i = 0; i < fieldCount; i++) {
				row[i] = rs.field(i)
			}
			rows.push(row)
			rs.next();
		}
		rs.close();
		this.rows = rows
	}
}

initHtml5db();
