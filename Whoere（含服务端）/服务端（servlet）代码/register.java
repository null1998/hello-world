package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
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
        //���ÿͻ��˵Ľ��뷽ʽΪutf-8  
        response.setContentType("text/html;charset=utf-8");  
        //  
        response.setCharacterEncoding("UTF-8");  
  
        boolean isTrue = false;   
  
        String username=request.getParameter("username");  
        String password=request.getParameter("password");  
        String nickname=request.getParameter("nickname");  
        String sex = request.getParameter("sex");
        String intro=request.getParameter("intro");
        String result = "";  
  
        isTrue=JDBCRegister.register(username,password,nickname,sex,intro);//�������ݿ⣬������û���Ϣ  
         //isTrue = JDBCRegister.register("99999", "12345", "nine", "��");
        PrintWriter out = response.getWriter();//��Ӧ����  
        if(isTrue){  
            result = "success";  
        }  
        else{  
            result = "fail";  
        }  
        out.write(result);  
        out.flush();  
        out.close();  
        System.out.println(result);
    }  
  
	}


