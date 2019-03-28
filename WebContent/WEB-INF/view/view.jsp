<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.ArrayList"  %>
<%@ page import = "com.board.vo.BoardVO"  %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
	
<% 
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>상세 보기 페이지 입니다</title>
</head>

<%
	request.setCharacterEncoding("UTF-8");
	System.out.println("상세보기 페이지");
	
	BoardVO vo = (BoardVO) request.getAttribute("board_data");
	//안에서 깨지는 줄 알았음.
	//근데 안이 아니라 밖에서 깨져서 온 거 였음. 그 받아온 값 안써도 이미 화면전체가 깨진다.
	
	String data_title, data_reg_date, data_mod_date, data_content;
	int content_id = vo.getContent_id();
	
	
	data_title = vo.getTitle();
	data_content = vo.getContent();
	
	data_reg_date = vo.getRegDate();	
	data_mod_date = vo.getModDate();	
	System.out.println("받아온 제목= " + data_title);
	
	//request 스코프에 BoardVO값이 유지
%>
 
<body>
	<h1>상세보기 페이지</h1>
		
	<table>
		<tr>
			<td>글 제목</td>
			<td><%=data_title %></td>
		</tr>
		<tr>
			<td>글 내용</td>
			<td><%= data_content %> </td>
		</tr>
		<tr>
			<td>등록 일자</td>
			<td><%= data_reg_date %></td>
		</tr>
		<tr>
			<td>수정 일자</td>
			<td><%= data_mod_date %></td>
		</tr>
	</table>
	<a href="/Servlet_BBS/controller?cmd=delete&content_id=<%=content_id%>">DELETE</a>
	<a href="/Servlet_BBS/controller?cmd=modify&content_id=<%=content_id%>&goto=goto_modify"> MODIFY</a>
</body>
</html>