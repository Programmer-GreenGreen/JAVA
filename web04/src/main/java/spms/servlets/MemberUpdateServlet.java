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
			//Class.forName(jdbc diver 클래스 이름) java.sql.driver 구현한 클래스 로딩
			//패키지 이름 포함 필수 Qname
			// web.xml <init-param>에서 값을 얻어옴 getInitParameter()이용히여 값 호출, 반환하는 값은 문자열 
			//this.getInitParameter("driver") = > "com.mysql.jdbc.Driver"
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			//<init-param> 에서 db연결 매개변수 호출
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select MNO,EMAIL,MNAME,CRE_DATE from members " 
			+ "where MNO=" + req.getParameter("no"));
			//select MNO,EMAIL,MNAME,CRE_DATE from members where MNO = "1"
			rs.next();
			//회원 번호를 가지고 회원 정보 질의, 단 한명의 회원정보를 가져옴 next() 1회만 호출
			
			
			resp.setContentType("text/html ;  charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>회원정보</title></head>");
			out.println("<body><h1>회원정보</h1>");
			out.println("<form action ='update' method='post'>");
			out.println("번호 : <input type='text' name = 'no' value ='" + 
			req.getParameter("no") + "' readonly><br>"); //readonly 속성 읽기전용, 값 없이 속성이름만 추가 가능	
			out.println("이름 : *<input type ='text' name='name'" + " value = '" 
			+ rs.getString("MNAME") + "'><br>");
			out.println("이메일 : *<input type ='text' name='eamil'" + " value = '" 
			+ rs.getString("EMAIL") + "'><br>");
			out.println("가입일 : " +rs.getDate("CRE_DATE") + "<br>");
			out.println("<input type='submit' value='저장'>");
			out.println("<input type='button' value='삭제'" + 
			 "onclick = 'location.href=\"delete?no=" + req.getParameter("no")+"\";'>");
			//'delete?no=" + rs.getInt("MNO")
			out.println("<input type='button' value='취소'" + " onclick = 'location.href=\"list\"'>"); 
			//취소 버튼 클릭시 회원 목록 페이지로 이동
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
		//characterEncodingFilter에서 처리
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
			//회원정보 변경 
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
