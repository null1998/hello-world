package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCPostLocation {
	private static Connection connection() {
		String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/login?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	    String user = "root";
	    String password = "12345";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, user, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	public static boolean postLocation(String myusername,String myLongitude,String myLatitude){
		try{
			Connection conn = connection();
			//提交注册信息
			String sql ="update users set longitude=?,latitude=? where username=?";
			PreparedStatement preparedstatement= (PreparedStatement)conn.prepareStatement(sql);
			preparedstatement.setString(1,myLongitude);
			preparedstatement.setString(2,myLatitude);
			preparedstatement.setString(3,myusername);
			preparedstatement.executeUpdate();
			preparedstatement.close();
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
}
