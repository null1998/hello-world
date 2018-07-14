package server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import net.sf.json.*;
/**
 * Servlet implementation class ChatSend
 */
@WebServlet("/ChatSend")
public class ChatSend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatSend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String result = "";
		boolean isTrue =false;
		request.setCharacterEncoding("UTF-8");
		//设置客户端的解码方式为utf-8  
        response.setContentType("text/html;charset=utf-8");  
        response.setCharacterEncoding("UTF-8");  
    //以上两句话十分重要,否则客户端无法接受返回的数据 
        String content =request.getParameter("content");
		String username =request.getParameter("username");
		String myusername=request.getParameter("myusername");
		String mynickname=request.getParameter("mynickname");
		
		isTrue = JDBCChat.chat_send(content,username,myusername,mynickname);
		if(isTrue){
			result="send success";
			System.out.println(myusername+"send:"+content+" to "+username);
		}
		else{
			result="send fail,there are another one send to "+username;
		}
        PrintWriter out = response.getWriter();//回应请求  
        out.write(result);
        out.flush(); 
        out.close(); 
	}

}
