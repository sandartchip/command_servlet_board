package servlet;

import java.io.IOException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDAO;

/**
 * Servlet implementation class WriteServlet
 */

//@WebServlet("/WriteServlet")
public class WriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* 입력 jsp 페이지 띄우기  */
		String whereTogo = "";
		whereTogo = request.getParameter("goto");
		
		System.out.println("여기로 이동= "+whereTogo);
		
		if(whereTogo.equals("moveToEnrollPage")) { //등록 페이지로 이동
			
			response.sendRedirect("write.jsp"); //파라미터 없으니까 redirect
		}
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	// 저장버튼 클릭 시 게시글 등록 후 list로 이동
		System.out.println(" -- 저장 --  ");
		
		String title="", content="";
		String regDate, modDate;
		
		title = request.getParameter("title"); //post방식으로 넘어온 값
		content = request.getParameter("content");
		
		System.out.println("넘어온 title= "+title);
		System.out.println("content= "+content);			
		
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		 
		modDate=regDate = dayTime.format(new Date(time));
		
		//db connect
		
		BoardDAO new_board_dao = new BoardDAO();
				
		Connection conn;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		ServletContext context; //프로젝트 경로 받아 옴
		RequestDispatcher rd;
		
		conn = new_board_dao.DBConnection();
		
		String insert_sql =
				"INSERT INTO board_table (title, content, regDate, modDate) values(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(insert_sql);
			//sql 쿼리
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, regDate);
			pstmt.setString(4, modDate);
			pstmt.executeUpdate();
			
			new_board_dao.closeConnection();
			context = getServletContext();//프로젝트 경로 받아 옴
			rd = context.getRequestDispatcher("/list");
			rd.forward(request, response);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
	}
}
