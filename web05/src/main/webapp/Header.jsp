<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="spms.vo.Member" %>
<!-- session을 사용해서 저장값 꺼냄 -->
<% Member member = (Member)session.getAttribute("member"); %>
<div style=background-color:#00008b;color:#ffffff;height:20px;padding:5px;">

SPMS(simple Project Management System)
<span style="float: right;">
<!-- 사용자 로그인 정보 출력 -->
<%=member.getName()%>
<a style="color: white;" href="<%=request.getContextPath()%>/auth/logout">로그아웃</a>
</span>
</div>