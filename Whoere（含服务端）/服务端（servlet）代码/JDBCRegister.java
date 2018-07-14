package server;
import java.sql.*;
import com.mysql.jdbc.Connection.*;
public class JDBCRegister {
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
	public static boolean register(String username,String password,String nickname,String sex,String intro){
		try {
		Connection conn = connection();
		//提交注册信息
		String sql ="insert into users(username,password,nickname,sex,intro)values(?,?,?,?,?)";
		PreparedStatement preparedstatement= (PreparedStatement)conn.prepareStatement(sql);
		preparedstatement.setString(1,username);
		preparedstatement.setString(2,password);
		preparedstatement.setString(3,nickname);
		preparedstatement.setString(4,sex);
		preparedstatement.setString(5,intro);
		//检查注册用户名与昵称是否重复
	    String sqlfind = "select *from users";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sqlfind);
		while(rs.next()){
			if(rs.getString("username").equals(username)||rs.getString("nickname").equals(nickname)) {
				rs.close();
				statement.close();
				preparedstatement.close();
				conn.close();
				return false;
		    }		
		}
		preparedstatement.executeUpdate();
		//在chat表中进行注册
		
		PreparedStatement preparedstatement2 = (PreparedStatement)conn.prepareStatement("insert into chat (speakers,username) values(?,?)");
		preparedstatement2.setString(1, nickname);
		preparedstatement2.setString(2, username);
		preparedstatement2.executeUpdate();
		rs.close();
		statement.close();
		preparedstatement.close();
		preparedstatement2.close();
		conn.close();
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		return true;
	}
}
