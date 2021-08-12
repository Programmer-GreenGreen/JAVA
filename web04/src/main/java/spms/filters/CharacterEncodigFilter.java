package spms.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
//�ֳ����̼� ��ġ ���� ���� 
@WebFilter(urlPatterns="/*",
		initParams= {@WebInitParam(name="encoding", value="UTF-8")})
public class CharacterEncodigFilter implements javax.servlet.Filter {
	FilterConfig config;
	

	@Override
	public void init(FilterConfig config) throws ServletException {
		// 1���� ȣ�� �Ű������� filterConfing��ü, ��ü�� ���� �ʱ�ȭ �Ű����� �� ȣ��
		//dofilter���� ����ϱ� ���� ������ ����
		this.config = config;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain nf)
			throws IOException, ServletException {
		//���Ϳ� ����� url ��û�� ������ �� �޼��尡 ȣ��, ���Ͱ� �� ���� �ۼ��ϸ�ȴ�. do
		
		//���� ���� �Ǳ� �� ó���� �۾� 
		req.setCharacterEncoding(config.getInitParameter("econding"));
		//���� ���͸� ȣ���Ѵ�. ���Ͱ� ������ service()ȣ��
		nf.doFilter(req, resp);
		//���� ������ Ŭ���̾�Ʈ���� �����ϱ� �� �ؾ��� �۾�
		//nextfilter.dofilte�� ���� ���͸� ȣ��, 
	}

	@Override
	public void destroy() {
		// ���� ���� �� ���͵鿡 ���� ������ �۾�
		
	}
}
