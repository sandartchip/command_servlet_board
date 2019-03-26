<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.ArrayList"  %>
<%@ page import = "com.board.vo.BoardVO"  %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록 페이지</title>
</head>
<body>
	<H1>게시글 목록 페이지</H1>
	<% 
		// ListServlet.java에서 dispatcher 통해  페이지 이동 및 파라미터 동시에 넘어 옴.
		String content = (String) request.getAttribute("test");
		System.out.println("서블릿에서 온 데이터 = "+content); 
	%>
	
	<div>
		<table>
			<thead>
				<tr>
					<th>글번호</th>
					<th width="100px">제목</th>
				</tr>
			</thead>
			<tbody>			
				<c:forEach items="${list_item}" var="vo">
					<tr>
						<td>${ vo.content_id }</td>					
						<td> <a href="/Servlet_BBS/view?content_id=${vo.content_id}"> ${vo.title} </a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- <a href="/Servlet_BBS/write.jsp">등록 페이지</a> -->
	<a href="/Servlet_BBS/write?goto=moveToEnrollPage">등록 페이지</a>
</body>
</html>