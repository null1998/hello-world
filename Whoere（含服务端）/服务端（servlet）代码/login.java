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
		//���ÿͻ��˵Ľ��뷽ʽΪutf-8  
	        response.setContentType("text/html;charset=utf-8");  
	         
	        response.setCharacterEncoding("UTF-8");  
	    //�������仰ʮ����Ҫ,����ͻ����޷����ܷ��ص�����  
	  
	        //���ݱ�ʾ����ȡJSP�ļ��б��������Ĳ���  
	        String username=request.getParameter("username");  
	        String password=request.getParameter("password"); 
	        System.out.println(username+"login...");
	        isTrue=JDBCLogin.login(username,password);//ʹ��ģ�Ͷ��˺ź����������֤������һ��boolean���͵Ķ��� 
	        
	        PrintWriter out = response.getWriter();//��Ӧ����  
	        if(isTrue){  //�����֤���Ϊ�棬��ת����¼�ɹ�ҳ��  
	            result = "success";      
	        } 
	        else {  //�����֤���Ϊ�٣���ת����¼ʧ��ҳ��  
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
