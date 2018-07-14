package server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatGet
 */
@WebServlet("/ChatGet")
public class ChatGet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatGet() {
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
		request.setCharacterEncoding("UTF-8");
		//设置客户端的解码方式为utf-8  
        response.setContentType("text/html;charset=utf-8");  
         
        response.setCharacterEncoding("UTF-8");  
        //以上两句话十分重要,否则客户端无法接受返回的数据  
  
        //根据标示名获取JSP文件中表单所包含的参数  
        String username=request.getParameter("username");
        
        
        
        String message =JDBCChat.chat_get(username);
        PrintWriter out = response.getWriter();
        if(!message.isEmpty()){
        System.out.println("username: ["+username+"] receive a message...");
        out.write(message);
        System.out.println("message:"+message);
        }
        out.flush(); 
        out.close();
        
        
	}

}
