package server;
import com.mysql.jdbc.Connection.*;
import java.sql.*;
public class JDBCChat {
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
	public static String chat_get(String username) {
		String message = "";
		try {
		Connection conn = connection();
		PreparedStatement preparedstatement = (PreparedStatement)conn.prepareStatement("select content from chat where username=? and content is not null");
		preparedstatement.setString(1,username);
		ResultSet rs = preparedstatement.executeQuery();
		while(rs.next()){
			//若不为空，在获取后将自己的content更新为空
		    message = rs.getString("content");
			PreparedStatement preparedstatementUpdate= (PreparedStatement)conn.prepareStatement("update chat set content = ? where content=? and username = ?");
			preparedstatementUpdate.setString(1, null);
			preparedstatementUpdate.setString(2, message);
			preparedstatementUpdate.setString(3, username);
			preparedstatementUpdate.executeUpdate();
			//preparedstatementUpdate.close();
		}
		rs.close();
		preparedstatement.close();
		conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return message;
	}
	//通过对方的用户名和自己想说的话更新对方的content
		public static boolean chat_send(String content,String username,String myusername,String mynickname){
			try {
				String nickname="";
				Connection conn = connection();
				PreparedStatement preparedstatementcheck= (PreparedStatement)conn.prepareStatement("select content from chat where username=? and content is null");
				PreparedStatement preparedstatementcheck2= (PreparedStatement)conn.prepareStatement("select speakers from chat where username=?");
				preparedstatementcheck.setString(1, username);
				preparedstatementcheck2.setString(1, myusername);
				ResultSet rs = preparedstatementcheck.executeQuery();
				ResultSet rs2 = preparedstatementcheck2.executeQuery();
				rs2.next();
				nickname=rs2.getString("speakers");
				System.out.println("nickname"+nickname);
				while(rs.next()){
				PreparedStatement preparedstatement= (PreparedStatement)conn.prepareStatement("update chat set content=? where username=?");
				preparedstatement.setString(1,nickname+"["+myusername+"]:"+content);
				preparedstatement.setString(2, username);
				preparedstatement.executeUpdate();
				rs.close();
				preparedstatementcheck.close();
				preparedstatement.close();
				conn.close();	
				return true;
				};	
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
			
		}
}
