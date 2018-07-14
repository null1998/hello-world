package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.*;
import java.util.*;
import net.sf.json.*;
public class JDBCSearchUsers {
	
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
	public static String searchUsers(String query){
		
		List<Users> userlist = new ArrayList<Users>();
		
		try{
			Connection conn = connection();
		
			String sql ="select * from users where intro is not null";
			Statement statement = (Statement)conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				if(query!=null&&rs.getString("intro").contains(query)){
					String SQL = "select *from users where username=?";
					PreparedStatement preparedstatement= (PreparedStatement)conn.prepareStatement(SQL);
					preparedstatement.setString(1,rs.getString("username"));
					ResultSet rss = preparedstatement.executeQuery();
					Users users = new Users();
					while(rss.next()){
					users.setId(rss.getString("id"));
					users.setUsername(rss.getString("username"));
					users.setPassword(rss.getString("password"));
					users.setNickname(rss.getString("nickname"));
					users.setLongitude(rss.getString("longitude"));
					users.setLatitude(rss.getString("latitude"));
					users.setIntro(rss.getString("intro"));
					users.setSex(rss.getString("sex"));
					}
					userlist.add(users);
					rss.close();
					preparedstatement.close();
					
				}
					
			}
			rs.close();
			statement.close();
			connection().close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		String jsonData = changeIntoJson(userlist);
		
		return jsonData;
	}
	
	private static String changeIntoJson(List<Users> userlist){
		String jsonData = "";
	
		for(int i=0;i<userlist.size();i++){
			
			String tmp ="{"+"\""+"username"+"\""+":"+"\""+userlist.get(i).getUsername()+"\""+","+"\""+"intro"+"\""+":"+"\""+userlist.get(i).getIntro()+"\""+"}";
			if(i<userlist.size()-1){
				tmp=tmp+",";
			}
			jsonData = jsonData+tmp;
		}
		return "["+jsonData+"]" ;
	}
	
}
