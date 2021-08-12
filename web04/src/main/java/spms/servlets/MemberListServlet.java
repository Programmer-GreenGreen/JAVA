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

@WebServlet("/member/list") // MemberListServlet의 url 패턴값
/*public class MemberListServlet extends GenericServlet {
 @Override*/
public class MemberListServlet extends HttpServlet {
	/*public void service(ServletRequest request, ServletResponse reponse) 
	  throws ServletException, IOException*/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// 1. service() 처음 부분은 JDBC 객체 주소 보관할 참조 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		// 2. JDBC API사용 시 예외처리
		try {
			// DriverManager를 이용하여 java.sql.Driver.registerDriver를 호출하여 인터페이스 구현체 등록
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection( // getConnection을 호출해서 mysql 연결
					sc.getInitParameter("url"), // JDBC URL
					sc.getInitParameter("username"), // DBMS 사용자 아이디
					sc.getInitParameter("password")); // DBMS 사용자 암호
			stmt = conn.createStatement();
			// sql 실행 객체 준비 createStatement()의 반환은 java.sql.Statement 인터페이스 구현체
			rs = stmt.executeQuery( // 데이터베이스에 sql문 보냄
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + " FROM MEMBERS" + " ORDER BY MNO ASC");

			response.setContentType("text/html; charset=UTF-8");// 회원목록 출력할 html페이지
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			out.println("<p><a href='add'>신규 회원</a></p>");
			while (rs.next()) { // select 결과 가져오기
				out.println(
						rs.getInt("MNO") + "," + "<a href='update?no=" + rs.getInt("MNO") + "'>" + rs.getString("MNAME")
								+ "</a>," + rs.getString("EMAIL") + "," + rs.getDate("CRE_DATE") + 
								"<a href='delete?no=" + rs.getInt("MNO")+ "'>"+"[삭제]<br>");
			}
			
			out.println("<body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally { // JDBC 프로그래밍 마무리 , 자원해제 finally 블록은 try catch를 벗어나기전에 반드시 수행
			try {if (rs != null)rs.close();} catch (Exception e) {}
			try {if (stmt != null)rs.close();} catch (Exception e) {}
			try {if (conn != null)rs.close();} catch (Exception e) {}
		}
	}
}
