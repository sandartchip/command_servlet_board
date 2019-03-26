<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.ArrayList"  %>
<%@ page import = "com.board.vo.BoardVO"  %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세 보기 페이지 입니다</title>
</head>

<%	

	request.setCharacterEncoding("UTF-8");
	BoardVO vo = (BoardVO) request.getAttribute("board_data");	
	
	int content_id = vo.getContent_id();
	String data_title = vo.getTitle();
	String data_reg_date = vo.getRegDate();
	String data_mod_date = vo.getModDate();
	String data_content = vo.getContent();
	System.out.println(data_title);

	//request 스코프에 BoardVO값이 유지
%>
<script language="javascript">
	function moveToDeletePage(content_id) {
		console.log("삭제 페이지로 이동"+content_id);
		location.href="/Servlet_BBS/delete?content_id="+content_id;
	}
	
	function moveToModifyPage(content_id){
		location.href="/Servlet_BBS/modify?content_id="+content_id;	
	}
</script>
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
	<button type="button" value="DELETE BUTTON" onclick ="javascript:moveToDeletePage(<%=content_id%>)">
	</button>
	<a href="/Servlet_BBS/modify?content_id=<%=content_id%>"> MODIFY</a>
</body>
</html>