package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
/**
 * Servlet implementation class ResetData
 */
@WebServlet("/ResetData")
public class ResetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetData() {
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
		//
        request.setCharacterEncoding("UTF-8");  
		 //设置客户端的解码方式为utf-8  
        response.setContentType("text/html;charset=utf-8");  
        //  
        response.setCharacterEncoding("UTF-8");  
  
        boolean isTrue = false;   
        String myusername=request.getParameter("myusername");  
        String password=request.getParameter("password");  
        String nickname=request.getParameter("nickname");  
        String sex = request.getParameter("sex");
        String intro=request.getParameter("intro");
        System.out.println("reset:"+myusername);
        String result = "";  
        isTrue=JDBCResetData.resetData(myusername,nickname,sex,intro,password);
        if(isTrue){
        	result="success";
        	System.out.println(myusername+" resetData success");
        }
        else{
        	result="fail";
        	System.out.println(myusername+" resetData fail");
        }
        PrintWriter out= response.getWriter();
        out.write(result);  
        out.flush();  
        out.close();  
	}

}
