package spms.servlets;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AppInitServlet extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		//init()는 서블릿 객체 생성될 때 한번만 호출, 공유 자원 준비 코드
		System.out.println("AppInitServlet 준비...");
		//17 line 새로운 작업 추가
		super.init(config);
		
		try {
			
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			Connection conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));	
			//database 커넥션 준비. 모든 서블릿이 사용할 수 있도록 객체에 저장
			sc.setAttribute("conn", conn);
		}catch(Throwable e) {
			throw new ServletException(e);
		}
	}

	@Override
	public void destroy() {
		//destroy() => 서블릿이 언로드 될 때 호출 데이터베이스 연결 종료
		System.out.println("AppInitServlet 마무리...");
		super.destroy();
		Connection conn = (Connection)this.getServletContext().getAttribute("conn");	
	try {
		if (conn!= null&& conn.isClosed() ==  false){
			conn.close();
		}
	}catch(Exception e) {} 
	
	
	}

	
}
