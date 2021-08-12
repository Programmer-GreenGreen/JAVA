package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

//UI ��� �ڵ� ���� UI ���� �� ����� JSP���� ����
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
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.createStatement();
			// sql ���� ��ü �غ� createStatement()�� ��ȯ�� java.sql.Statement �������̽� ����ü
			rs = stmt.executeQuery( // �����ͺ��̽��� sql�� ����
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + " FROM members" + " ORDER BY MNO ASC");

			response.setContentType("text/html; charset=UTF-8");// ȸ����� ����� html������

			ArrayList<Member> members = new ArrayList<Member>();
			//Arraylist ��ü �غ�
			while (rs.next()) { // select ��� ��������				
			members.add(new Member()
					.setNo(rs.getInt("MNO"))
					.setName(rs.getString("MNAME"))
					.setEmail(rs.getString("EMAIL"))
					.setCreatedDate(rs.getDate("CRE_DATE")));
			}
		//request�� ȸ�� ��� ������ ����
			request.setAttribute("members", members); 
		
			
		//RequestDispatcher = ��� ���� �� �� ����ϴ� ��ü , ��μ����ʼ�
		RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
		//�ٸ� ���� �̳� jsp�� �۾��� ���� �Ҷ� ����ϴ� ��ü => RequestDispatcher		
		rd.include(request, response);
		//include => ���� �� ������� �ѱ� �� �۾��� ������ٽ� ����� ������
		
		
		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		} finally { // JDBC ���α׷��� ������ , �ڿ����� finally ����� try catch�� ��������� �ݵ�� ����
			try {if (rs != null)rs.close();} catch (Exception e) {}
			try {if (stmt != null)stmt.close();} catch (Exception e) {}
		
		}
	}
}
