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
	protected void doGet( //doGet 오버라이딩, 이 클래스가 해야 할 일을 작성
			//GET 요청 발생 경우 => 웹 브라우저의 주소창에 url입력 후 엔터
			//a 태그로 만들어진 링크 클릭
			HttpServletRequest request, HttpServletResponse response
			)
			throws ServletException , IOException{
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'><br>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
	}
		
		@Override
		protected void doPost(
				HttpServletRequest request, HttpServletResponse response)
				throws ServletException , IOException{
			//request.setCharacterEncoding("UTF-8");
			//CharcterEncoingFilter에서 처리
			Connection conn = null;
			PreparedStatement stmt  = null;
			
			try {
				ServletContext sc = this.getServletContext();
				Class.forName(sc.getInitParameter("driver"));
				conn = DriverManager.getConnection(
						sc.getInitParameter("url"), //JDBC URL
						sc.getInitParameter("username"),	// DBMS 사용자 아이디
						sc.getInitParameter("password"));	// DBMS 사용자 암호
				stmt = conn.prepareStatement( 
						//prepareStatement => 반복적인 질의.입력할 매개변수가 많은 경우에 사용	
						 "INSERT INTO members(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"  
						+"values (?,?,?,NOW(),NOW())");
				
				stmt.setString(1, request.getParameter("email")); //1부터 시작
				stmt.setString(2, request.getParameter("password"));
				stmt.setString(3, request.getParameter("name"));
				stmt.executeUpdate(); //executeUpdate()를 호출해서 sql실행
				
				//클라이언트에 실행 결과 출력
				/*리다이렉트는 html을 출력하지 않는다.
				 * response.setContentType("text/html; charset = UTF-8");
				
				PrintWriter out = response.getWriter();
				out.println("<html><head><title>회원등록결과</title></head>");
				out.println("<meta http-equiv='Refresh' content='1;url=list'>");
				//meta 태그는 head안에 선언, 작업결과 출력 후 페이지 이동 =>refresh , 결과출력 X 페이지 이동 => redirect
				out.println("<body>");
				out.println("<p>등록 성공입니다</p>");
				out.println("</body></html>");	*/
				response.addHeader("Refresh", "1;url=list"); 
				//url => 다시 요청할 서비스 주소
				//응답 본문을 출력하고 1초뒤에 다시 서비스 요청
			} catch (Exception e) { 
				throw new ServletException(e);	//예외발생시 ServletException을 서블릿 컨테이너에 던짐		
			}finally {
				try {if (stmt!= null) stmt.close();} catch(Exception e) {}
				try {if (conn!= null) stmt.close();} catch(Exception e) {}
			}						
		}				
	}
	