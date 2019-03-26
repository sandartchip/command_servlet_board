<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.board.vo.BoardVO"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
	<title>수정 페이지</title>
</head>

<%
	String title = "gg";
	String content = "gg";
	String content_id = "gg";
	//requset.setCharacterEncoding("UTF-8");
	title = (String) request.getAttribute("name");
	
	System.out.println("edit page로 넘어온 제목=" + title);

/*
	System.out.println("수정 게시물로 넘어오기");
	String title, content;
	int content_id;

	//request.setCharacterEncoding("UTF-8");
	
	BoardVO vo = (BoardVO) request.getAttribute("original_data");
	
	content_id = vo.getContent_id();
	title = vo.getTitle();
	content = vo.getContent();
	//String reg_date = vo.getRegDate();
	//String data_mod_date = vo.getModDate();
	System.out.println("넘어온 원래  게시물 제목" + title);
	System.out.println("게시물 번호" + content_id);*/
%>
<body>
	hihihi 
	
</body>
</html> 