package com.board.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BoardDAO { //尻衣廃 DB 梓端 軒渡!!
	
	Connection conn = null; 
	Statement stmt = null;
	ResultSet rs = null;
	
	public BoardDAO() {
		
	}
	
	public Connection DBConnection() {
		System.out.println("~~しぞしぞしぞ");

		try{
			System.out.println("~~~DB CONNECT~~ WELCOME~~~~");

			//1. JDBC 球虞戚獄 稽漁 	
			String driverName = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverName); 
			
			//2. DB 辞獄 羨紗
			String dbUrl = "jdbc:mysql://localhost:3306/boardDB?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
			String dbUser = "root";
			String dbPassword = "ROOT";
			
			System.out.println("~~~DB CONNECT~~ WELCOME~~~~222");
			
			//羨紗 URL舛左人 匂闘 腰硲, SID 			
			
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			//是拭辞 幻窮 適掘什研 import馬食 Connection 梓端研 持失廃陥.
			
		} catch(SQLException es) { 
			es.printStackTrace();
		} catch(ClassNotFoundException ec) {
			ec.printStackTrace();
		} catch(Exception ex) { //奄展 森須坦軒
			ex.printStackTrace();
		} 
		return conn;  //杭亜 戚雌....
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