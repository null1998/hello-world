package server;
import java.sql.*;
import com.mysql.jdbc.Connection.*;
public class JDBCResetData {
	private static Connection connection() {
		String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://47.94.221.23:3306/login?useUnicode=true&characterEncoding=utf-8&useSSL=false";
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
	public static String getData(String myusername){
		String username="",nickname="",password="",sex="",intro="";
		try{
			Connection conn = connection();
			PreparedStatement preparedstatement= (PreparedStatement)conn.prepareStatement("select *from users where username=?");
			preparedstatement.setString(1,myusername);
			ResultSet rs = preparedstatement.executeQuery();
			while(rs.next()){
				username=rs.getString("username");
				nickname=rs.getString("nickname");
				password=rs.getString("password");
				sex=rs.getString("sex");
				intro=rs.getString("intro");
			}
			rs.close();
			preparedstatement.close();
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return "{"+"\""+"username"+"\""+":"+"\""+username+"\""+","+"\""+"nickname"+"\""+":"+"\""+nickname+"\""+","+"\""+"password"+"\""+":"+"\""+password+"\""+","+"\""+"sex"+"\""+":"+"\""+sex+"\""+","+"\""+"intro"+"\""+":"+"\""+intro+"\""+"}";
		
		
	}
	public static boolean resetData(String myusername,String nickname,String sex,String intro,String password) {
		try {
			Connection conn = connection();
			//users表
			PreparedStatement preparedstatement= (PreparedStatement)conn.prepareStatement("update users set nickname=?,sex=?,intro=?,password=? where username=?");
			preparedstatement.setString(1,nickname);
			preparedstatement.setString(2, sex);
			preparedstatement.setString(3,intro);
			preparedstatement.setString(4,password);
			preparedstatement.setString(5,myusername);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("select *from users");
			
			PreparedStatement preparedstatementCheck= (PreparedStatement)conn.prepareStatement("select nickname from users where username=?");
			preparedstatementCheck.setString(1, myusername);
			ResultSet rsCheck = preparedstatementCheck.executeQuery();
			rsCheck.next();
			while(rs.next()){
				if(rs.getString("nickname").equals(nickname)&&!nickname.equals(rsCheck.getString("nickname"))){
					rs.close();
					rsCheck.close();
					preparedstatementCheck.close();
					statement.close();
					preparedstatement.close();
					conn.close();
					return false;
				}
			}
			preparedstatement.executeLargeUpdate();
			//更改chat表
			PreparedStatement preparedstatementChat= (PreparedStatement)conn.prepareStatement("update chat set speakers=?where username =?");
			preparedstatementChat.setString(1, nickname);
			preparedstatementChat.setString(2, myusername);
			preparedstatementChat.executeLargeUpdate();
			rs.close();
			statement.close();
			preparedstatement.close();
			preparedstatementChat.close();
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}
