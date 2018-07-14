package server;
import com.mysql.jdbc.Connection.*;
import java.sql.*;

public class JDBCLogin {
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
	public static boolean login(String username,String password) {		
		try {
		String sql = "select *from users";
		Connection conn= connection();
		if(conn==null)
			System.out.println("error");
		Statement statement=conn.createStatement();
		
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
		if(rs.getString("username").equals(username)&&rs.getString("password").equals(password)) {
			rs.close();
			statement.close();
			connection().close();
			return true;
		}
		}
		rs.close();
		statement.close();
		connection().close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

}
