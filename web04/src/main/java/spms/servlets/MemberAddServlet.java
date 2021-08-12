package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	
	@Override
	protected void doGet( //doGet �������̵�, �� Ŭ������ �ؾ� �� ���� �ۼ�
			//GET ��û �߻� ��� => �� �������� �ּ�â�� url�Է� �� ����
			//a �±׷� ������� ��ũ Ŭ��
			HttpServletRequest request, HttpServletResponse response
			)
			throws ServletException , IOException{
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>ȸ�� ���</title></head>");
		out.println("<body><h1>ȸ�� ���</h1>");
		out.println("<form action='add' method='post'>");
		out.println("�̸�: <input type='text' name='name'><br>");
		out.println("�̸���: <input type='text' name='email'><br>");
		out.println("��ȣ: <input type='password' name='password'><br>");
		out.println("<input type='submit' value='�߰�'>");
		out.println("<input type='reset' value='���'>");
		out.println("</form>");
		out.println("</body></html>");
	}
		
		@Override
		protected void doPost(
				HttpServletRequest request, HttpServletResponse response)
				throws ServletException , IOException{
			//request.setCharacterEncoding("UTF-8");
			//CharcterEncoingFilter���� ó��
			Connection conn = null;
			PreparedStatement stmt  = null;
			
			try {
				ServletContext sc = this.getServletContext();
				Class.forName(sc.getInitParameter("driver"));
				conn = DriverManager.getConnection(
						sc.getInitParameter("url"), //JDBC URL
						sc.getInitParameter("username"),	// DBMS ����� ���̵�
						sc.getInitParameter("password"));	// DBMS ����� ��ȣ
				stmt = conn.prepareStatement( 
						//prepareStatement => �ݺ����� ����.�Է��� �Ű������� ���� ��쿡 ���	
						 "INSERT INTO members(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"  
						+"values (?,?,?,NOW(),NOW())");
				
				stmt.setString(1, request.getParameter("email")); //1���� ����
				stmt.setString(2, request.getParameter("password"));
				stmt.setString(3, request.getParameter("name"));
				stmt.executeUpdate(); //executeUpdate()�� ȣ���ؼ� sql����
				
				//Ŭ���̾�Ʈ�� ���� ��� ���
				/*�����̷�Ʈ�� html�� ������� �ʴ´�.
				 * response.setContentType("text/html; charset = UTF-8");
				
				PrintWriter out = response.getWriter();
				out.println("<html><head><title>ȸ����ϰ��</title></head>");
				out.println("<meta http-equiv='Refresh' content='1;url=list'>");
				//meta �±״� head�ȿ� ����, �۾���� ��� �� ������ �̵� =>refresh , ������ X ������ �̵� => redirect
				out.println("<body>");
				out.println("<p>��� �����Դϴ�</p>");
				out.println("</body></html>");	*/
				response.addHeader("Refresh", "1;url=list"); 
				//url => �ٽ� ��û�� ���� �ּ�
				//���� ������ ����ϰ� 1�ʵڿ� �ٽ� ���� ��û
			} catch (Exception e) { 
				throw new ServletException(e);	//���ܹ߻��� ServletException�� ���� �����̳ʿ� ����		
			}finally {
				try {if (stmt!= null) stmt.close();} catch(Exception e) {}
				try {if (conn!= null) stmt.close();} catch(Exception e) {}
			}						
		}				
	}
	