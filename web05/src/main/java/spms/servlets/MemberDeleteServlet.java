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
		request.setCharacterEncoding("UTF-8"); //���ڵ� ���� ����
		Connection conn = null;
		PreparedStatement stmt  = null;
		
		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.prepareStatement( 
					//prepareStatement => �ݺ����� ����.�Է��� �Ű������� ���� ��쿡 ���	
					"delete from members "
					+ " where MNO=" + request.getParameter("no")); 
			stmt.executeUpdate(); //��������
			response.sendRedirect("list");

		} catch (Exception e) { 
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);	//���ܹ߻��� ServletException�� ���� �����̳ʿ� ����		
		}finally {
			try {if (stmt!= null) stmt.close();} catch(Exception e) {}
			
		}						
	}				
}