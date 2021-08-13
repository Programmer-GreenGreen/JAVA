package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.vo.Member;
@SuppressWarnings("serial")
@WebServlet("/member/update") //updateServlet url
public class MemberUpdateServlet extends HttpServlet {

	


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			//Class.forName(this.getInitParameter("driver"));
			ServletContext sc = this.getServletContext();
			//Class.forName(jdbc diver Ŭ���� �̸�) java.sql.driver ������ Ŭ���� �ε�
			//��Ű�� �̸� ���� �ʼ� Qname
			// web.xml <init-param>���� ���� ���� getInitParameter()�̿����� �� ȣ��, ��ȯ�ϴ� ���� ���ڿ� 
			//this.getInitParameter("driver") = > "com.mysql.jdbc.Driver"
			conn = (Connection) sc.getAttribute("conn");
			//<init-param> ���� db���� �Ű����� ȣ��
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select MNO,EMAIL,MNAME,CRE_DATE from members " 
			+ "where MNO=" + request.getParameter("no"));
			//select MNO,EMAIL,MNAME,CRE_DATE from members where MNO = "1"
			rs.next();
			//ȸ�� ��ȣ�� ������ ȸ�� ���� ����, �� �Ѹ��� ȸ�������� ������ next() 1ȸ�� ȣ��
			
			Member member = new Member()
					.setNo(rs.getInt("MNO"))
					.setName(rs.getString("MNAME"))
					.setEmail(rs.getString("EMAIL"))
					.setCreatedDate(rs.getDate("CRE_DATE"));
					
			request.setAttribute("member", member);
			response.setContentType("text/html ;  charset=UTF-8");
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
			rd.forward(request, response);
			
		}catch (Exception e) {
			throw new ServletException(e);
		}finally {
			try  {if (rs !=null) rs.close();} catch(Exception e) {}
		      try {if (stmt !=null) rs.close();} catch(Exception e) {}
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//characterEncodingFilter���� ó��
		//req.setCharacterEncoding("UTF-8");
		Connection conn = null;
		PreparedStatement stmt = null;
		try { 
			//Class.forName(this.getInitParameter("dirver"));
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.prepareStatement("update members set EMAIL=?,MNAME=?,MOD_DATE=now()"
					+ " where MNO=?"); 
			//ȸ������ ���� 
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("name"));
			stmt.setInt(3, Integer.parseInt(request.getParameter("no")));
			
			stmt.executeUpdate();
			
			response.sendRedirect("list");
			
		}
		catch (Exception e) {
			/*request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);*/
			throw new ServletException(e);
			
		}
		finally {
			 try {if (stmt !=null) stmt.close();} catch(Exception e) {}
		      
		}
	}
	

}
