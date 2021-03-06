package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException , IOException{
		request.setCharacterEncoding("UTF-8"); //인코딩 형식 지정
		Connection conn = null;
		PreparedStatement stmt  = null;
		
		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.prepareStatement( 
					//prepareStatement => 반복적인 질의.입력할 매개변수가 많은 경우에 사용	
					"delete from members "
					+ " where MNO=" + request.getParameter("no")); 
			stmt.executeUpdate(); //쿼리실행
			response.sendRedirect("list");

		} catch (Exception e) { 
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);	//예외발생시 ServletException을 서블릿 컨테이너에 던짐		
		}finally {
			try {if (stmt!= null) stmt.close();} catch(Exception e) {}
			
		}						
	}				
}