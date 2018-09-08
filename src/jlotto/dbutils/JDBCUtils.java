package jlotto.dbutils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import jlotto.frames.InfoDialog;

public class JDBCUtils {
	
	/* declare values in order to access a database */
	private boolean driverLoaded, dbOpen;
	private String dbName;
	private String dbUser;
	private String dbUserPass;
	private String dbDriver;
	private String dbDriverURL;
	private String dbSystemPath;
	
	/* header and row data from a table to store and to show
	 * on a JPanel per internal method-->showDB()
	 */
	private String[] headers;
	private String[][] rowContent;
	
	/* other Collection tools */
	private ArrayList<String> headerData;
 	private Vector<String> rowData;
	
	/* standard instances for reaching and manipulating a database */
	private Connection connection;
	private Statement sqlStatement;
	private ResultSet resultSet;
	private ResultSetMetaData resultSetData;
	private DatabaseMetaData dbInfoData;
	
	/* the information frame to show the user the actions done by this instance */
	private InfoDialog infoFrame;
	
	private final String[][] DRIVERS = { 
			{"jdbc:odbc:", "sun.jdbc.odbc.JdbcOdbcDriver"}, 
			{"jdbc:mysql:", "com.mysql.jdbc.Driver"}, 
			{"jdbc:microsoft:sqlserver:", "com.microsoft.sqlserver.jdbc.SQLServerDriver"}
	};
	
	/**
	 * Creates a new instance of JDBCUtils
	 */
	public JDBCUtils() {
		/* presets */
		this.driverLoaded = false;
		this.dbOpen = false;
		this.dbName = "";
		this.dbUser = "";
		this.dbUserPass = "";
		this.dbDriver = "";
		this.dbDriverURL = "";
		this.dbSystemPath = "";
		
		this.connection = null;
		this.sqlStatement = null;
		this.resultSet = null;
		this.resultSetData = null;
	}
	
	/**
	 * Closes all connections and statements to an open database
	 * <p></p>
	 * @throws SQLException information is printed on InfoDialog.java 
	 */
	public void closeDB() {
		if (this.isDBOpen()) {
			/* close all operations on the database */
			try {
				this.getDBConnection().close();
				this.setDBConnectable(false);
				this.setDBDriver("");
				this.setDBOpen(false);
			} catch (SQLException sqle) {
				this.infoFrame.setInfoText(sqle.getMessage());
			}
		}
	}
	
	/**
	 * Creates a table in a existing database.
	 * <p></p>
	 * @param dbName the database
	 * @param tableName the new table of the database
	 * @throws java.sql.SQLException
	 */
	public void createTable(String dbName, String tableName) {
		/* check, driver loaded and database accepts SQL-statements */
		if(this.isDBOpen()) {
			try {
				this.sqlStatement = this.connection.createStatement();
				this.sqlStatement.execute("CREATE TABLE " + tableName + " INTO " + dbName);
				/* close all open SQL connectivity */
				this.closeDB();
			}
			catch (SQLException sqle) {
				this.infoFrame.setInfoText(sqle.getMessage());
			}
		}
		else {
			/* open connection to database and retry deleting table(s) */
			/* while in study, database url is absolute */
			this.openConnection(this.getAccessPath() + dbName, 
					this.getDBUser(), this.getDBUserPass());
			this.createTable(dbName, tableName);
		}
	}
	
	/**
	 * Deletes / drops a table of a database.
	 * <p></p>
	 * @param dbName the database
	 * @param tableName the table of the database
	 * @throws java.sql.SQLException
	 */
	public void deleteTable(String dbName, String tableName) {
		if(this.isDBOpen()) {
			try {
				this.sqlStatement = this.connection.createStatement();
				this.sqlStatement.executeUpdate("DROP TABLE " +  tableName);
				this.sqlStatement.close();
				this.connection.close();
				this.setDBConnectable(false);
				System.out.println("Table : " + tableName + " gelöscht");
			}
			catch(SQLException sqle) {
				this.infoFrame.setInfoText(sqle.getMessage());
			}
		}
		else {
			/* retry by opening database */
			this.openConnection(this.getAccessPath() + dbName, 
					this.getDBUser(), this.getDBUserPass());
			this.deleteTable(dbName, tableName);
		}
	}
	
	/**
	 * Inserts rows of data-values(records) to a table or view.<br></br>
	 * E.g: a row of data(records) can look like this :<br></br>
	 * <i><code>
	 *  INSERT INTO tableName WHERE rows[i] = dataValues[i]; 
	 *  while i < dataValues.size()
	 * </code></i>
	 * <p></p>
	 * @param dbName the database
	 * @param tableName the table/view of the database
	 * @param dataValues a java.util.Vector object representing records of data-values
	 * @throws java.sql.SQLException 
	 */
	public void insertDataset(String dbName, String tableName, Vector<Object> dataValues) {
		
	}
	
	/**
	 * Loads the database-driver referring to which ODBC-Bridge is given.
	 * <P></P>
	 * @param driverName the ODBC driver/-bridge
	 * @throws java.lang.ClassNotFoundException 
	 */
	public void loadJDBCDriver(String driverName) {
		try {
			int i = 0;
			int driverIndx = 1;
			/* 
			 * search if a suitable driver is supported by this programm/system
			 * and save states of support and connect ability
			 */
			while (i < this.DRIVERS.length) {
				if (this.DRIVERS[i][driverIndx].equals(driverName)) {
					Class.forName(driverName).newInstance();
					this.setDBDriver(driverName);
					this.setDBConnectable(true);
					/* save the ODBC-protocol */
					this.dbDriverURL = this.DRIVERS[i][driverIndx - 1];
					/* end searching */
					i = this.DRIVERS.length - 1;
					/* send driver loaded message */
					this.infoFrame.setInfoText(this.dbDriver + " wurde geladen.");
				}
				i++;
			}
		}
		catch(Exception exep) {
			/* none of the supported driver for this programm was found, send message */
			this.infoFrame.setInfoText("Es wurde kein passender Treiber gefunden.\n"
					+ "Bitte sehen sie im Handbuch nach, welche Treiber unterstützt werden.");
		}
	}
	
	/**
	 * Opens a Connection to a database by accessing it through the user-id
	 * and user-password, set in the database.<br></br>
	 * The system folder path where the database residents should be set through
	 * the <b>JDBCUtils.setDBSystemPath(String path)</b> method.
	 * <p></p>
	 * @param dbName the database to access
	 * @param user 
	 * @param pass
	 * @throws java.sql.SQLException
	 */
	public void openConnection(String dbName, String user, String pass) {
		
		if (this.isDBDriverLoaded()) {
			try {
				
				this.connection = DriverManager.getConnection(
						this.getAccessPath() + "\\" + dbName, 
						user, 
						pass);
				
				/* check if is connected */
				if(!this.connection.isClosed()) {
					/* tell user database connected */
					this.infoFrame.setInfoText("Verbindung " 
							+ "zur Datenbank aufgebaut !");
				}
				
				/*
				 * save identifiers for database, user, and user-password for
				 * further operations
				 */
				this.setDBName(dbName);
				this.setDBUser(user);
				this.setDBUserPass(pass);
				this.setDBOpen(true);
			} 
			catch (SQLException sqle) {
				this.infoFrame.setInfoText("Fehler aus openConnection...");
				this.infoFrame.setInfoText(sqle.getMessage());
			}
		}
		else {
			this.infoFrame.setInfoText("Es wurde kein Datenbanktreiber geladen !");
		}
	}
	
	/*
	 * Sets the header names of a table into an Vector retrieving
	 * from a ResultSetMetaData-object
	 *
	 * @param rsmd the Set containing the header names
	 * @param columns the number of columns a table has
	 */
	private void setHeaderNames(ResultSetMetaData rsmd, int columns) {
		/* just read the code, its self explanatory */
		this.headers = new String[columns];
		try {
			/* retrieve all Header-descriptions of the excel-table */
			for (int i = 1; i <= columns; i++) {
				this.headers[i - 1] = rsmd.getColumnLabel(i);
				System.out.println("Spaltenname = " + this.headers[i - 1]);
			}
		}
		catch(SQLException sqle) {
			System.out.println("Fehler aus setHeaderNames");
			System.err.println(sqle.getMessage());
		}
	}
	
	/**
	 * Sets the InfoFrame for this instance
	 * <p></p>
	 * @param infoFrame an instance of InfoFrame
	 */
	public void setInfoFrame(InfoDialog infoFrame) {
		this.infoFrame = infoFrame;
	}
	
	/**
	 * Sets all table names of an open database as select able elements of a 
	 * JComboBox.<br></br><br></br>
	 * <b>Important note :</b><br></br>
	 * The Connection to a database should be opened before this method is used !<br></br>
	 * Otherwise this method will throw a SQLException displayed on the InfoFrame.class.
	 * <p></p>
	 * @throws SQLException , the information about which Exception has accrued will be
	 *         shown on InfoDialog.class
	 */
	public void setTableNames(javax.swing.JComboBox inBox) {
		this.headerData = new ArrayList<String>();
		try {
			if(this.isDBOpen()) {
				
				this.dbInfoData = this.getDBConnection().getMetaData();
				String[] types = {"TABLE"};
		        this.resultSet = this.dbInfoData.getTables(null, null, "%", types);
		        inBox.removeAllItems();
		        
		        while (this.resultSet.next()){
		          this.headerData.add(this.resultSet.getString("TABLE_NAME"));
		        }
		        int i = 0;
		        while(i < this.headerData.size()) {
		        	inBox.addItem(this.headerData.get(i));
		        	i++;
		        }
		        inBox.validate();
			}
			else {
				/* open a connection to the database */
				this.openConnection(this.getAccessPath() + this.getDBName(), 
						this.getDBUser(), this.getDBUserPass());
				/* retry */
				this.setTableNames(inBox);
			}
		}
		catch(SQLException sqle) {
			this.infoFrame.setInfoText("Fehler aus setTableNames");
			this.infoFrame.setInfoText(sqle.getMessage());
		}
	}
	
	/*
	 * Sets the content of a table into an internal
	 * two dimensional String array
	 * 
	 * @param rs the results from an ODBC call to a existing table
	 * @param columns the amount(size) of existing columns in a table
	 */
	private void setTableContent(ResultSet rs, int columns) {
		int i = 0;
		int j = 0;
		int k = 0;
		this.rowData = new Vector<String>();
		try {
			/* read column by column content from top-left to bottom-right 
			 * of a table of a database
			 */
			while (rs.next()) {
				/* read column by column */
				while (i < columns) {
					/* save all read content into a vector */
					this.rowData.add(rs.getString(i + 1));
					i++;
				}
				/* reset index counter for next row-column-content */
				i = 0;
			}
			/* determine the row size of the database table */
			int rows = this.rowData.size() / columns;
			
			/* initialize string-array of row-column-data content */
			this.rowContent = new String[rows][columns];
			
			/* set content of the columns of a row from top-left to 
			 * bottom-right from a table of a database
			 */
			while(i < rows) {
				while(j < columns) {
					/* this is know the table content, save it in string-arrays */
					this.rowContent[i][j] = this.rowData.get(k).toString();
					j++;
					k++;
				}
				/* reset for the next position to save in the string array
				 * of a string array
				 */
				j = 0;
				/* next string array of string array to save data */
				i++;
			}
		}
		catch(SQLException sqle) {
			System.err.println(sqle.getMessage());
		}
	}
	
	/**
	 * Shows the whole content of a given database table.<br></br><br></br>
	 * In Order to set the content of a given database to the presentation tier of<br></br>
	 * an application, this method needs further information of which JPanel inherits a<br></br>
	 * JTable instance/component.<br></br>
	 * If the given information has been set, this method will call internal set-get-make<br></br>
	 * methods in order to place all information received from the database table into<br></br>
	 * a JTable object, which should be given to this by parameter.<br></br>
	 * 
	 * <br></br><br></br>
	 * 
	 * Here is an example call of this method :<br></br>
	 * <code> "employees" </code> is a table of a certain database<br></br>
	 * <code> JScrollPanel tablePanel = new JScrollPanel(); // the JPanel of the JTabel </code><br></br>
	 * <b><code> showTableOn("employees", tablePanel);</code></b><br></br><br></br>
	 * 
	 * This will start to open and read all of "employees", puts them into the JDBCUtils internal<br></br>
	 * JTable and finally places this on the "tablePanel" to show in the presentation tier of the GUI.
	 * <p></p>
	 * @param tableName the name of the database table
	 * @param tablePanel the parent table which should contain the JTable
	 */
	public void showTableOn(String tableName, 
			javax.swing.JScrollPane scrollPanel) 
	{
		if(this.isDBOpen()) {
			try {
				/* select all from DB-Table tableName */
				this.sqlStatement = this.getDBConnection().createStatement();
				this.resultSet = 
					this.sqlStatement.executeQuery("SELECT * FROM " + tableName);
				this.resultSetData = this.resultSet.getMetaData();
				int columns = this.resultSetData.getColumnCount();
				this.setHeaderNames(this.resultSetData, columns);
				this.setTableContent(this.resultSet, columns);
				
				/* now show the whole content of the selected table on the
				 * JPanel by deleting all content first, then setting and repainting it
				 */
				scrollPanel.removeAll();
				scrollPanel.add(this.getDBTable());
				scrollPanel.repaint();
				scrollPanel.validate();
			}
			catch (SQLException sqle) {
				this.infoFrame.setInfoText(sqle.getMessage());
			}
		}
		else {
			this.infoFrame.setInfoText("Datenbankverbindung ist nicht offen !" 
					+ "\n Es wird versucht eine Verbindung herzustellen..");
			this.openConnection(this.getAccessPath() 
					+ this.getDBName(), this.getDBUser(), this.getDBUserPass());
			this.showTableOn(tableName, scrollPanel);
		}
	}
	
	/*
	 * Sets the connect able state for the database
	 * 
	 * @param state true will set connectivity to database, false will not
	 */
	private void setDBConnectable(boolean state) {
		this.driverLoaded = state;
	}
	
	// ZU TESTZWECEKN public , SONST private
	/*
	 * Sets the state for the opened connection of a database
	 * 
	 * @param state true will set database open for SQL statements, false not
	 */
	public void setDBOpen(boolean state) {
		this.dbOpen = state;
	}
	
	/**
	 * Sets the driver used for a database of the this instance
	 * <p></p>
	 * @param dbDriver the database driver
	 */
	public void setDBDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}
	
	/*
	 * Sets the name of the database used in an application
	 *
	 * @param dbName the name of the database
	 */
	private void setDBName(String dbName) {
		this.dbName = dbName;
	}
	
	/*
	 * Sets the user name of a database for connection reasons
	 *
	 * @param user the name of the user who uses a database
	 */
	private void setDBUser(String user) {
		this.dbUser = user;
	}
	
	/*
	 * Sets the password of a user of a database for connection reasons
	 * 
	 * @param userPass the password of the user who uses a database
	 */
	private void setDBUserPass(String userPass) {
		this.dbUserPass = userPass;
	}
	
	/*
	 * Sets the database destination directory
	 * 
	 * @param path the database destination directory
	 */
	public void setDBSystemPath(String path) {
		this.dbSystemPath  = path;
	}
	
	/**
	 * Determines if a ODBC conform driver is loaded or not
	 * <p></p>
	 * @return <b>true</b> if a ODBC driver is already loaded,
	 *         <b>false</b> if not
	 */
	public boolean isDBDriverLoaded() {
		return this.driverLoaded;
	}
	
	/**
	 * Determines if a open database connection is available
	 * <p></p>
	 * @return <b>true</b> if a connection to a database 
	 *         has been established, <b>false</b> if not
	 */
	public boolean isDBOpen() {
		return this.dbOpen;
	}
	
	
	/*
	 * Returns the currently user of a database
	 * 
	 * @return a String presentation of the user of a database
	 */
	private String getDBUser() {
		return this.dbUser;
	}
	
	/*
	 * Returns the current user password of a database
	 * 
	 * @return a String presentation of the password from a user
	 */
	private String getDBUserPass() {
		return this.dbUserPass;
	}
	
	/**
	 * Returns the current name of a database used here
	 * <p></p>
	 * @return a String presentation about the name of a database
	 */
	public String getDBName() {
		return this.dbName;
	}
	
	/*
	 * Returns the current database driver
	 * 
	 * @return a String presentation about the name of the database driver
	 */
	private String getDriver() {
		return this.dbDriver;
	}
	
	/*
	 * Returns the URL of the database driver
	 * 
	 * @return a String presentation of the URL
	 *         for example, for a access with JDBC-ODBC database :
	 *         URL = "jdbc:odbc"
	 */
	private String getDriverURL() {
		return this.dbDriverURL;
	}
	
	/**
	 * Returns the URL of the database destination folder
	 * <p></p>
	 * @return a String presentation of the URL 
	 */
	public final String getDBSystemPath() {
		return this.dbSystemPath;
	}
	
	/*
	 * Returns the absolute directory folder where the database can be found
	 * 
	 * @return a String presentation of the URL where the database can be found
	 */
	private String getAccessPath() {
		return this.getDriverURL()
				+ "Driver={Microsoft Access Driver (*.mdb)};DBQ="
				+ this.getDBSystemPath();
	}
	
	/*
	 * Returns the header data of a table
	 * 
	 * @param a String array containing the header-labels of a table
	 */
	private String[] getHeaderContent() {
		return this.headers;
	}
	
	/*
	 * Returns the whole content of a table except the header data
	 * of a table
	 * 
	 * @return the content of a table without the data from its headers
	 */
	private String[][] getTableContent() {
		return this.rowContent;
	}
	
	/*
	 * Returns the currently used database connection
	 * 
	 * @return java.sql.Connection
	 */
	private Connection getDBConnection() {
		return this.connection;
	}
	
	/**
	 * Returns the DatabaseMetaData object used in JDBCUtils
	 * <p></p>
	 * @return the actually used DatabaseMetaData object
	 */
	public DatabaseMetaData getDatabaseMetaData() {
		return this.dbInfoData;
	}
	
	/**
	 * Returns the info dialog used by this instance for retrieving information<br></br>
	 * about certain exceptions or errors caused by accessing or manipulating on the database
	 * <p></p>
	 * @return InfoDialog.java
	 */
	public InfoDialog getInfoDialog() {
		return this.infoFrame;
	}
	
	/**
	 * Returns the statement object of JDBCUtils
	 * <p></p>
	 * @return the actually used Statement object
	 */
	public Statement getStatement() {
		return this.sqlStatement;
	}
	
	/**
	 * Returns the ResultSet object of JDBUtils
	 * <p></p>
	 * @return the actually used ResultSet object
	 */
	public ResultSet getResultSet() {
		return this.resultSet;
	}
	
	/**
	 * Returns the ResultSetMetaData object of JDBUtils
	 * <p></p>
	 * @return the actually used ResultSetMetaData object
	 */
	public ResultSetMetaData getResultSetMetaData() {
		return this.resultSetData;
	}
	
	/*
	 * Returns a JTable instance with the content of a database table
	 * 
	 * @return a JTable with database table contents with its header and row data
	 */
	private javax.swing.JTable getDBTable() {
		return new DBResultTable(this.getTableContent(), this.getHeaderContent());
	}
}
