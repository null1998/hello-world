package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
public class JDBCGetLocation {
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
	
	public static String getLocation(String ToUsername){
		String jsonData="";
		String longitude="",latitude="";
		try{
		Connection conn = connection();
		//提交注册信息
		String sql ="select longitude,latitude from users where username=?";
		PreparedStatement preparedstatement= (PreparedStatement)conn.prepareStatement(sql);
		preparedstatement.setString(1,ToUsername);
		ResultSet rs= preparedstatement.executeQuery();
		while(rs.next()){
		longitude=rs.getString("longitude");
		latitude=rs.getString("latitude");
		}
		}catch(SQLException e){
			e.printStackTrace();
		}
		jsonData="{"+"\""+"longitude"+"\""+":"+"\""+longitude+"\""+","+"\""+"latitude"+"\""+":"+"\""+latitude+"\""+"}";
		
		return jsonData;
	}
}
