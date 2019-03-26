<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>글 작성 페이지</title>
</head>
<body>

<!--  post 방식으로 writeServlet (/write) 로 데이터 넘어감 -->
	<form action="/Servlet_BBS/write" method="post" onsubmit="return formCheck();">
	
		제목: <input type="text" name="title" />
		내용: <input type="text" name="content" style="height:100px" />
		
		<input type="submit" value="저장" />	
	</form>
	<script>
		function formCheck(){
			var title = document.forms[0].title.value;
			var content = document.forms[0].content.value;
			
			if(title.length==0) 
				alert("제목 업음");
			if(content.length ==0)
				alert("내용 없음");
			if(title.length>80)
				alert("제목의 길이가 너무 깁니다");
			if(  (title.length> 80) || (content.length==0) || (title.length==0) )
				return false;
		}
	</script>

</body>
</html>