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

//UI 출력 코드 제거 UI 생성 및 출력을 JSP에게 위임
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
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.createStatement();
			// sql 실행 객체 준비 createStatement()의 반환은 java.sql.Statement 인터페이스 구현체
			rs = stmt.executeQuery( // 데이터베이스에 sql문 보냄
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + " FROM members" + " ORDER BY MNO ASC");

			response.setContentType("text/html; charset=UTF-8");// 회원목록 출력할 html페이지

			ArrayList<Member> members = new ArrayList<Member>();
			//Arraylist 객체 준비
			while (rs.next()) { // select 결과 가져오기				
			members.add(new Member()
					.setNo(rs.getInt("MNO"))
					.setName(rs.getString("MNAME"))
					.setEmail(rs.getString("EMAIL"))
					.setCreatedDate(rs.getDate("CRE_DATE")));
			}
		//request에 회원 목록 데이터 보관
			request.setAttribute("members", members); 
		
			
		//RequestDispatcher = 출력 위임 할 때 사용하는 객체 , 경로설정필수
		RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
		//다른 서블릿 이나 jsp로 작업을 위임 할때 사용하는 객체 => RequestDispatcher		
		rd.include(request, response);
		//include => 위임 후 제어권을 넘긴 후 작업이 끝나면다시 제어권 가져옴
		
		
		} catch (Exception e) {
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		} finally { // JDBC 프로그래밍 마무리 , 자원해제 finally 블록은 try catch를 벗어나기전에 반드시 수행
			try {if (rs != null)rs.close();} catch (Exception e) {}
			try {if (stmt != null)stmt.close();} catch (Exception e) {}
		
		}
	}
}
