package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost/javaproject?" +
			"useUnicode = true&characterEncoding=utf8&serverTimezone=UTC";
	
	private final String USER_NAME = "root";
	private final String PASSWORD = "1234";
	public Connection conn = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	
	public DBConnection() {
		
		
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			stmt = conn.createStatement();
			

		} catch(Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
	
	public void insertDB(String name, String score) {
		String SQL = "insert into user (name, score) values ('"+ name + "', '" + score + "');"; 
			
		try {
			stmt.executeUpdate(SQL);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}