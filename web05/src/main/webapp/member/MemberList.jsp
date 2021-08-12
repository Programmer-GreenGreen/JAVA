<!-- 화면 생성 출력 -->
<!-- 뷰 컴포넌트 -->

<%@page import="spms.vo.Member"%>
<%@page import="java.util.ArrayList"%>
<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 목록</title>
</head>
<body>
<!-- Header.jsp include -->
<jsp:include page="/Header.jsp"></jsp:include>
<h1>회원목록</h1>
<p><a href='add'>신규 회원</a></p>
<%
ArrayList<Member> members = (ArrayList<Member>)request.getAttribute(
								"members"); //getAttribute  호출
for(Member member : members) {
%>
<%=member.getNo()%>,
<a href='update?no=<%=member.getNo()%>'><%=member.getName()%></a>,
<%=member.getEmail()%>,
<%=member.getCreatedDate()%>
<a href='delete?no=<%=member.getNo()%>'>[삭제]</a><br>
<%} %>
<!-- tail.jsp include -->
<jsp:include page="/Tail.jsp"></jsp:include>
</body>
</html>        