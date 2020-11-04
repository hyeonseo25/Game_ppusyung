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
			
//			String sql;
//			sql = "SELECT * FROM user";
//			rs = stmt.executeQuery(sql);
			
			//rs.close();
			//stmt.close();
			//conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
//			try {
//				if(stmt != null) {
//					stmt.close();
//				}
//			} catch(SQLException e) {
//				e.printStackTrace();
//			}
//			try {
//				if(conn != null) {
//					conn.close();
//				}
//			} catch(SQLException e) {
//				e.printStackTrace();
//			}
		}
	}
	
}