package server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetLocation
 */
@WebServlet("/GetLocation")
public class GetLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLocation() {
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
		String jsonData = "";
		request.setCharacterEncoding("UTF-8");
		//���ÿͻ��˵Ľ��뷽ʽΪutf-8  
	    response.setContentType("text/html;charset=utf-8");  
	         
	    response.setCharacterEncoding("UTF-8");  
	    //�������仰ʮ����Ҫ,����ͻ����޷����ܷ��ص�����  
	  
	    //���ݱ�ʾ����ȡJSP�ļ��б��������Ĳ���  
	    String ToUsername=request.getParameter("ToUsername"); 
	    System.out.println("aim:"+ToUsername);
	    jsonData=JDBCGetLocation.getLocation(ToUsername);
	    PrintWriter out = response.getWriter();//��Ӧ����
	    out.write(jsonData);
        out.flush(); 
        out.close();
	    System.out.println("his location:"+jsonData);
	}

}
