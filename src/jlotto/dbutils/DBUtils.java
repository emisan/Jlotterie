package jlotto.dbutils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class DBUtils {

	private final String CONNECTION_DRIVER = "com.mysql.jdbc.Driver";
	private Connection connection;
	
	public DBUtils() {
		connection = null;
	}
	
	public void connectDB(String dbName, 
						  String dbUserName, 
						  String dbUserPassword)
	{
		try {
			Class.forName(CONNECTION_DRIVER);
			
			try {
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost/lottoDB", dbUserName, dbUserPassword);
			}
			catch(SQLException sqlErr) {
				
				System.out.println("SQL-Error in connectDB()\n");
				sqlErr.printStackTrace();
			}
		}
		catch(ClassNotFoundException noDriver) 
		{
			System.out.println("The MySQL drive " + CONNECTION_DRIVER
					+ " could not be found or loaded !");
			noDriver.printStackTrace();
		}
	}
	
	public void closeDatabase() {
		try {
			connection.close();
		}
		catch(SQLException sqlErr) {
			
			System.out.println("SQL-Error: Could not close connection " 
					+ "in closeDatabase\n");
			sqlErr.printStackTrace();
		}
	}
	
	public ResultSet getSelectQuery(String sqlQuery) {
		
		ResultSet rs = null;
		
		try {
			if (connection != null) 
			{
				Statement stmnt = connection.createStatement();
				rs = stmnt.executeQuery(sqlQuery);
			}
			else {
				if (connection.isClosed()) 
				{
					System.out.println("ResultSet for SELECT-Query " 
							+ "could not be received !\n"
							+ "The Connection to the database is closed.");
				}
			}
		}
		catch(SQLException sqlErr) {
			System.out.println("SQL-Error in getSelectQuery()\n");
			sqlErr.printStackTrace();
		}
		return rs;
	}
	
	public void printResultSet(ResultSet rs) {
		try
		{
			StringBuilder str = new StringBuilder();
			
			ResultSetMetaData rsData = rs.getMetaData();
			
			int i = 1;
			
			int columnsSize = rsData.getColumnCount();
			
			// build up Column-Header-Names
			//
			while(i < columnsSize) 
			{
				str.append(rsData.getColumnName(i) + "\t");
				i++;
			}
			
			while (rs.next()) 
			{
				i = 1;
				
				while(i <= columnsSize) 
				{
					str.append(rs.getString(i) + "\t");
					i++;
				}
			}
			
			rs.close();
			
			System.out.println(str);
		}
		catch(SQLException sqlErr)
		{
			System.out.println("SQL-Error in printResultSet()\n");
			sqlErr.printStackTrace();
		}
	}
}
