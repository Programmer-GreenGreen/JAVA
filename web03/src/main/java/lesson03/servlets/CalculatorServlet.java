package lesson03.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
@WebServlet("/calc") //@WebServlet("���� �̸�")
					 //���� �� ���� URL ����
					 //@WebServlet(urlPatterns="/calc") =>�Ϲ����� ���ڿ��� ǥ�� ����
					 //@WebServlet(urlPatterns={"/calc"}) =>�߱�ȣ�� �迭 ǥ��
					 //���� ���� ���� URL ����
					 //@WebServlet(urlPatterns="~","~~","~") =>�߱�ȣ�� �迭 ǥ��
					 //value = urlPatterns �������̼��� �������� �Ӽ� �̸��� value��� ���� ����
					 //value �Ӽ� �� �ٸ� �Ӽ����� �Բ� �����Ѵٸ� value ���� �Ұ���
public class CalculatorServlet extends GenericServlet {

	@Override
	public void service(ServletRequest reqeust, ServletResponse reponse) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int a = Integer.parseInt(reqeust.getParameter("a"));
		int b = Integer.parseInt(reqeust.getParameter("b"));
		
		//reponse.setContentType("text/plain"); 
		//setContentType => ����� �����Ͱ� �ؽ�Ʈ�̰� ������ ��Ÿ������ ���� ������ �ؽ�Ʈ�̴�
		//reponse.setCharacterEncoding("UTF-8");
		//setCharacterEncoding => ����� �������� ���� ������ ���� �����ڵ带 UTF-8 �������� ��ȯ
		reponse.setContentType("text/plain;charset=UTF-8");
		
		
		PrintWriter wirter = reponse.getWriter();
		//Ŭ���̾�Ʈ�� ����� �� �ֵ��� ��� ��Ʈ�� ��ü�� ��ȯ
		//getWriter�� ȣ���ϱ� ���� setContentType or setCharacterEncoding�� ȣ���ؾ��Ѵ�
		
		wirter.println("a="+a+","+"b="+b+"�� ������Դϴ�");
		wirter.println("a + b = " + (a+b));
		wirter.println("a - b = " + (a-b));
		wirter.println("a * b = " + (a*b));
		wirter.println("a / b = " + ((float)a/(float)b));
		wirter.println("a % b = " + (a%b));
		
		
	}

}
