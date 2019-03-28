<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.board.vo.BoardVO"  %>
<% request.setCharacterEncoding("utf-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>수정 페이지</title>
</head>

<%

	BoardVO original_vo = (BoardVO) request.getAttribute("original_data");
 
	String title, content;
	int content_id;
	
	title = original_vo.getTitle(); 
	content = original_vo.getContent();
	content_id = original_vo.getContent_id();
%>
<body>
	<form action="/Servlet_BBS/controller?cmd=modify" method="post" onsubmit="return formCheck();">
		
		제목 : <input type="text" name="title" value="<%=title %>" />
		내용 : <input type="text" name="content" value="<%=content %>" />	
		<input type="hidden" name="content_id_str" value="<%=content_id %>" />
		<input type="hidden" name="goto" value="modify_enroll" />	 
		<input type="submit" value = "저장"/>
	</form>
	<script>
		function formCheck(){
 
			//첫번째 form의 제목의 값
			var title = document.forms[0].title.value;
			var content = document.forms[0].content.value;
			if(title.length == 0)
				alert("제목 없음");
			
			if(content.length == 0 )
				alert("내용 없음");
			
			if( title.length >80) 
				alert("제목의 길이가 너무 깁니다");
			console.log("제목: " + title + "내용  : " + content);

			if( ( title.length >80)|| (content.length == 0 ) || (title.length == 0))
				return false;
		} 
	</script> 
</body>
</html> 