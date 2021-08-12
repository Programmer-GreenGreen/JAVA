package lesson03.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
@WebServlet("/calc") //@WebServlet("서블릿 이름")
					 //서블릿 한 개의 URL 설정
					 //@WebServlet(urlPatterns="/calc") =>일반적인 문자열로 표기 가능
					 //@WebServlet(urlPatterns={"/calc"}) =>중광호로 배열 표기
					 //서블릿 여러 개의 URL 설정
					 //@WebServlet(urlPatterns="~","~~","~") =>중광호로 배열 표기
					 //value = urlPatterns 에노테이션의 문법에서 속성 이름이 value경우 생략 가능
					 //value 속성 외 다른 속성값도 함께 설정한다면 value 생략 불가능
public class CalculatorServlet extends GenericServlet {

	@Override
	public void service(ServletRequest reqeust, ServletResponse reponse) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int a = Integer.parseInt(reqeust.getParameter("a"));
		int b = Integer.parseInt(reqeust.getParameter("b"));
		
		//reponse.setContentType("text/plain"); 
		//setContentType => 출력할 데이터가 텍스트이고 별도의 메타정보가 없는 순수한 텍스트이다
		//reponse.setCharacterEncoding("UTF-8");
		//setCharacterEncoding => 출력할 데이터의 문자 집합을 지정 유니코드를 UTF-8 형식으로 변환
		reponse.setContentType("text/plain;charset=UTF-8");
		
		
		PrintWriter wirter = reponse.getWriter();
		//클라이언트로 출력할 수 있도록 출력 스트림 객체를 반환
		//getWriter를 호출하기 전에 setContentType or setCharacterEncoding을 호출해야한다
		
		wirter.println("a="+a+","+"b="+b+"의 계산결과입니다");
		wirter.println("a + b = " + (a+b));
		wirter.println("a - b = " + (a-b));
		wirter.println("a * b = " + (a*b));
		wirter.println("a / b = " + ((float)a/(float)b));
		wirter.println("a % b = " + (a%b));
		
		
	}

}
