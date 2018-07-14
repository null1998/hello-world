package server;

import java.io.IOException;
import net.sf.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.io.*;
/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		    doGet(request, response);
		    
		    boolean isTrue=false;
		    String result = "";
		    request.setCharacterEncoding("UTF-8");
		//设置客户端的解码方式为utf-8  
	        response.setContentType("text/html;charset=utf-8");  
	         
	        response.setCharacterEncoding("UTF-8");  
	    //以上两句话十分重要,否则客户端无法接受返回的数据  
	  
	        //根据标示名获取JSP文件中表单所包含的参数  
	        String username=request.getParameter("username");  
	        String password=request.getParameter("password"); 
	        System.out.println(username+"login...");
	        isTrue=JDBCLogin.login(username,password);//使用模型对账号和密码进行验证，返回一个boolean类型的对象 
	        
	        PrintWriter out = response.getWriter();//回应请求  
	        if(isTrue){  //如果验证结果为真，跳转至登录成功页面  
	            result = "success";      
	        } 
	        else {  //如果验证结果为假，跳转至登录失败页面  
	            result = "fail";       
	        }
	        
	       /* JSONObject jsonObject = new JSONObject();
	        jsonObject.put("result", result);
	        response.getWriter().print(jsonObject);*/
            
	        out.write(result);
	        out.flush(); 
	        out.close();  
            System.out.println(result);
	}

}
