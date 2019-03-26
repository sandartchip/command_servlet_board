package com.board.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BoardDAO { //������ DB ��ü ����!!
	
	Connection conn = null; 
	Statement stmt = null;
	ResultSet rs = null;
	
	public BoardDAO() {
		
	}
	
	public Connection DBConnection() {
		System.out.println("~~������������");

		try{
			System.out.println("~~~DB CONNECT~~ WELCOME~~~~");

			//1. JDBC ����̹� �ε� 	
			String driverName = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverName); 
			
			//2. DB ���� ����
			String dbUrl = "jdbc:mysql://localhost:3306/boardDB?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
			String dbUser = "root";
			String dbPassword = "ROOT";
			
			System.out.println("~~~DB CONNECT~~ WELCOME~~~~222");
			
			//���� URL������ ��Ʈ ��ȣ, SID 			
			
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			//������ ���� Ŭ������ import�Ͽ� Connection ��ü�� �����Ѵ�.
			
		} catch(SQLException es) { 
			es.printStackTrace();
		} catch(ClassNotFoundException ec) {
			ec.printStackTrace();
		} catch(Exception ex) { //��Ÿ ����ó��
			ex.printStackTrace();
		} 
		return conn;  //���� �̻�....
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