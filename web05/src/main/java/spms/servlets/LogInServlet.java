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
		 //�������� ���ƿ� �ʿ�X, ���������� ó��
		 rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//ȸ������ ��ȸ 
	
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
				//ȸ�������� Member�� ��´�
				Member member = new Member()
						.setEmail(rs.getString("EMAIL"))
						.setName(rs.getString("MNAME"));
				
				HttpSession session = request.getSession();
				session.setAttribute("member", member); //member��ü�� session�� ����
				response.sendRedirect("../member/list"); //�α��� ���� �� �����̷�Ʈ
			}
			else {
			
			RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
			//�α��ν��н� /auth/LogInFail.jsp�� ������
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
