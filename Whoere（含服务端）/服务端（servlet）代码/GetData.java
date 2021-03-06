package server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
/**
 * Servlet implementation class getData
 */
@WebServlet("/getData")
public class GetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetData() {
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
		String jsonData="";
        response.setContentType("text/html;charset=utf-8");  
         
        response.setCharacterEncoding("UTF-8");  
        //以上两句话十分重要,否则客户端无法接受返回的数据  
  
        //根据标示名获取JSP文件中表单所包含的参数  
        String myusername=request.getParameter("myusername");  
        System.out.println(myusername+" want to getData...");
        jsonData=JDBCResetData.getData(myusername);
        PrintWriter out = response.getWriter();
        out.write(jsonData);
        out.flush(); 
        out.close();  
        System.out.println(jsonData);
	}

}
