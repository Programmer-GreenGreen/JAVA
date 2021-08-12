package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.vo.Member;


@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		 RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInForm.jsp");
		 //서블릿으로 돌아올 필요X, 포워딩으로 처리
		 rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//회원정보 조회 
	
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.prepareStatement(
					"Select MNAME, EMAIL from members" + " where EMAIL=? and PWD=?");
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			rs = stmt.executeQuery();
			if (rs.next()) {
				//회원정보를 Member에 담는다
				Member member = new Member()
						.setEmail(rs.getString("EMAIL"))
						.setName(rs.getString("MNAME"));
				
				HttpSession session = request.getSession();
				session.setAttribute("member", member); //member객체를 session에 저장
				response.sendRedirect("../member/list"); //로그인 성공 시 리다이렉트
			}
			else {
			
			RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
			//로그인실패시 /auth/LogInFail.jsp로 포워딩
			rd.forward(request, response);
			}
		}
		catch(Exception e){
			throw new ServletException(e);
		}
		finally {
			 try {if (rs !=null) rs.close();} catch(Exception e) {}
			 try {if (stmt !=null) stmt.close();} catch(Exception e) {}
			 
			
		}
		
	}

}
