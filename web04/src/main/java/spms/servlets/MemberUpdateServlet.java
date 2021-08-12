package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings("serial")
@WebServlet("/member/update") //updateServlet url
public class MemberUpdateServlet extends HttpServlet {

	


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
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
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			//<init-param> ���� db���� �Ű����� ȣ��
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select MNO,EMAIL,MNAME,CRE_DATE from members " 
			+ "where MNO=" + req.getParameter("no"));
			//select MNO,EMAIL,MNAME,CRE_DATE from members where MNO = "1"
			rs.next();
			//ȸ�� ��ȣ�� ������ ȸ�� ���� ����, �� �Ѹ��� ȸ�������� ������ next() 1ȸ�� ȣ��
			
			
			resp.setContentType("text/html ;  charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>ȸ������</title></head>");
			out.println("<body><h1>ȸ������</h1>");
			out.println("<form action ='update' method='post'>");
			out.println("��ȣ : <input type='text' name = 'no' value ='" + 
			req.getParameter("no") + "' readonly><br>"); //readonly �Ӽ� �б�����, �� ���� �Ӽ��̸��� �߰� ����	
			out.println("�̸� : *<input type ='text' name='name'" + " value = '" 
			+ rs.getString("MNAME") + "'><br>");
			out.println("�̸��� : *<input type ='text' name='eamil'" + " value = '" 
			+ rs.getString("EMAIL") + "'><br>");
			out.println("������ : " +rs.getDate("CRE_DATE") + "<br>");
			out.println("<input type='submit' value='����'>");
			out.println("<input type='button' value='����'" + 
			 "onclick = 'location.href=\"delete?no=" + req.getParameter("no")+"\";'>");
			//'delete?no=" + rs.getInt("MNO")
			out.println("<input type='button' value='���'" + " onclick = 'location.href=\"list\"'>"); 
			//��� ��ư Ŭ���� ȸ�� ��� �������� �̵�
			out.println("</form>");
			out.println("</body></html>");
													
		}catch (Exception e) {
			throw new ServletException(e);
		}finally {
			try  {if (rs !=null) rs.close();} catch(Exception e) {}
		      try {if (stmt !=null) rs.close();} catch(Exception e) {}
		      try {if (conn !=null) rs.close();} catch(Exception e) {}
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//characterEncodingFilter���� ó��
		//req.setCharacterEncoding("UTF-8");
		Connection conn = null;
		PreparedStatement stmt = null;
		try { 
			//Class.forName(this.getInitParameter("dirver"));
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			stmt = conn.prepareStatement("update members set EMAIL=?,MNAME=?,MOD_DATE=now()"
					+ " where MNO=?"); 
			//ȸ������ ���� 
			stmt.setString(1, req.getParameter("emial"));
			stmt.setString(2, req.getParameter("name"));
			stmt.setInt(3, Integer.parseInt(req.getParameter("no")));
			
			stmt.executeUpdate();
			
			resp.sendRedirect("list");
			
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
		finally {
			 try {if (stmt !=null) stmt.close();} catch(Exception e) {}
		      try {if (conn !=null) conn.close();} catch(Exception e) {}
		}
	}
	

}
