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
		//���ÿͻ��˵Ľ��뷽ʽΪutf-8  
		String jsonData="";
        response.setContentType("text/html;charset=utf-8");  
         
        response.setCharacterEncoding("UTF-8");  
        //�������仰ʮ����Ҫ,����ͻ����޷����ܷ��ص�����  
  
        //���ݱ�ʾ����ȡJSP�ļ��б��������Ĳ���  
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
