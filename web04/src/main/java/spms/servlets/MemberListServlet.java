package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/list") // MemberListServlet�� url ���ϰ�
/*public class MemberListServlet extends GenericServlet {
 @Override*/
public class MemberListServlet extends HttpServlet {
	/*public void service(ServletRequest request, ServletResponse reponse) 
	  throws ServletException, IOException*/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// 1. service() ó�� �κ��� JDBC ��ü �ּ� ������ ���� ���� ����
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		// 2. JDBC API��� �� ����ó��
		try {
			// DriverManager�� �̿��Ͽ� java.sql.Driver.registerDriver�� ȣ���Ͽ� �������̽� ����ü ���
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection( // getConnection�� ȣ���ؼ� mysql ����
					sc.getInitParameter("url"), // JDBC URL
					sc.getInitParameter("username"), // DBMS ����� ���̵�
					sc.getInitParameter("password")); // DBMS ����� ��ȣ
			stmt = conn.createStatement();
			// sql ���� ��ü �غ� createStatement()�� ��ȯ�� java.sql.Statement �������̽� ����ü
			rs = stmt.executeQuery( // �����ͺ��̽��� sql�� ����
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + " FROM MEMBERS" + " ORDER BY MNO ASC");

			response.setContentType("text/html; charset=UTF-8");// ȸ����� ����� html������
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>ȸ�����</title></head>");
			out.println("<body><h1>ȸ�����</h1>");
			out.println("<p><a href='add'>�ű� ȸ��</a></p>");
			while (rs.next()) { // select ��� ��������
				out.println(
						rs.getInt("MNO") + "," + "<a href='update?no=" + rs.getInt("MNO") + "'>" + rs.getString("MNAME")
								+ "</a>," + rs.getString("EMAIL") + "," + rs.getDate("CRE_DATE") + 
								"<a href='delete?no=" + rs.getInt("MNO")+ "'>"+"[����]<br>");
			}
			
			out.println("<body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally { // JDBC ���α׷��� ������ , �ڿ����� finally ����� try catch�� ��������� �ݵ�� ����
			try {if (rs != null)rs.close();} catch (Exception e) {}
			try {if (stmt != null)rs.close();} catch (Exception e) {}
			try {if (conn != null)rs.close();} catch (Exception e) {}
		}
	}
}
