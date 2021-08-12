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
		// ���� �����̳� ����, ����, ��Ȱ��ȭ �� ȣ��
		//���� ������ ���� Ȯ���ߴ� �ڿ��� �����Ѵٰų� �����͸� �����ϴ� ���� ������ �۾� �ۼ�
		System.out.println("destroy() ȣ���");
	}

	@Override
	public ServletConfig getServletConfig() {
		// ������ ���� ������ ���ڿ��� ��ȯ
		System.out.println("getServletConfig() ȣ���");
		return this.config;
		
	}

	@Override
	public String getServletInfo() {
		// ���� ���� ������ �ٷ�� ServletConfig ��ü ��ȯ
		System.out.println("getServletInfo() ȣ���");
		return "version=1.0; author=eomjinyoung;copyright=eomjinyoung 2013";
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// ���� �����̳ʰ� ������ ������ �� �ʱ�ȭ �۾��� �����ϱ� ���� ȣ���ϴ� �޼���
		System.out.println("init() ȣ���");
		this.config = config;
		
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) 
			throws ServletException, IOException {
		// Ŭ���̾�Ʈ�� ��û�� �� ���� ȣ��Ǵ� �޼���
		System.out.println("service() ȣ���");
	}
}
