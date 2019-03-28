package com.board.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BoardDAO { //연결한 DB 객체 리턴!!
	
	Connection conn = null; 
	Statement stmt = null;
	ResultSet rs = null;
	
	public BoardDAO() {
		
	}
	
	public Connection DBConnection() {
		System.out.println("~~ㅇㅎㅇㅎㅇㅎ");

		try{
			System.out.println("~~~DB CONNECT~~ WELCOME~~~~");

			//1. JDBC 드라이버 로딩 	
			String driverName = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverName); 
			
			//2. DB 서버 접속
			//String dbUrl = "jdbc:mysql://localhost:3306/boardDB?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
			String dbUrl = "jdbc:mysql://localhost:3306/boarddb2?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
			String dbUser = "root";
			String dbPassword = "ROOT";
			
			System.out.println("~~~DB CONNECT~~ WELCOME~~~~222");
			
			//접속 URL정보와 포트 번호, SID 			
			
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			//위에서 만든 클래스를 import하여 Connection 객체를 생성한다.
			
		} catch(SQLException es) { 
			es.printStackTrace();
		} catch(ClassNotFoundException ec) {
			ec.printStackTrace();
		} catch(Exception ex) { //기타 예외처리
			ex.printStackTrace();
		} 
		return conn;  //뭔가 이상....
	} //END OF DBConnection
	
	public void closeConnection() throws SQLException{
		try {
			
		} finally {
			if(conn != null) try {
				conn.close();
			} catch(SQLException ex) {
				
			}
		} 
	}
}