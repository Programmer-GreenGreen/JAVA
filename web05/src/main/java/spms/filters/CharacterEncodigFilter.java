package spms.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
//애노테이션 배치 정보 설정 
@WebFilter(urlPatterns="/*",
		initParams= {@WebInitParam(name="encoding", value="UTF-8")})
public class CharacterEncodigFilter implements Filter {
	FilterConfig config;
	

	@Override
	public void init(FilterConfig config) throws ServletException {
		// 1번만 호출 매개변수는 filterConfing객체, 객체로 필터 초기화 매개변수 값 호출
		//dofilter에서 사용하기 위해 변수에 저장
		this.config = config;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain nextFilter)
			throws IOException, ServletException {
		//필터와 연결된 url 요청이 들어오면 이 메서드가 호출, 필터가 할 일을 작성하면된다. do
		
		//서블릿 실행 되기 전 처리할 작업 
		request.setCharacterEncoding(config.getInitParameter("encoding"));
		//다음 필터를 호출한다. 필터가 없으면 service()호출
		nextFilter.doFilter(request, response);
		//서블릿 실행후 클라이언트에게 응답하기 전 해야할 작업
		//nextfilter.dofilte는 다음 필터를 호출, 
	}

	@Override
	public void destroy() {
		// 웹앱 종료 전 필터들에 대해 마무리 작업
		
	}
}
