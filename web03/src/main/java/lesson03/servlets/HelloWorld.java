package lesson03.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloWorld implements Servlet{
	ServletConfig config;

	@Override
	public void destroy() {
		// 서블릿 컨테이너 종료, 중지, 비활성화 시 호출
		//서비스 수행을 위해 확보했던 자원을 해제한다거나 데이터를 저장하는 등의 마무리 작업 작성
		System.out.println("destroy() 호출됨");
	}

	@Override
	public ServletConfig getServletConfig() {
		// 서블릿에 대한 정보를 문자열을 반환
		System.out.println("getServletConfig() 호출됨");
		return this.config;
		
	}

	@Override
	public String getServletInfo() {
		// 서블릿 설정 정보를 다루는 ServletConfig 객체 반환
		System.out.println("getServletInfo() 호출됨");
		return "version=1.0; author=eomjinyoung;copyright=eomjinyoung 2013";
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// 서블릿 컨테이너가 서블릿을 생성한 후 초기화 작업을 수행하기 위해 호출하는 메서드
		System.out.println("init() 호출됨");
		this.config = config;
		
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) 
			throws ServletException, IOException {
		// 클라이언트가 요청할 때 마다 호출되는 메서드
		System.out.println("service() 호출됨");
	}
}
