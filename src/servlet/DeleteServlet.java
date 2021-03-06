package servlet;

import java.io.IOException;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDAO;

/**
 * Servlet implementation class DeleteServlet
 */
//@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String content_id_str = request.getParameter("content_id");
		System.out.println("content id= "+ content_id_str);
		
		int content_id;
		content_id = Integer.parseInt(content_id_str);
		BoardDAO new_board_dao = new BoardDAO();
		
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		conn = new_board_dao.DBConnection();
		
		String delete_sql = "DELETE FROM board_table WHERE content_id=?";
		try {
			pstmt = conn.prepareStatement(delete_sql);
			pstmt.setString(1, content_id_str);
			int r = pstmt.executeUpdate();
			
			///
			
			/*  rearrange     */
			
			Statement stmt;
			stmt = conn.createStatement();
			
			String rearrange_idx_sql = 
					"ALTER TABLE board_table drop content_id;";
					// IDX 재조정 위해 컬럼삭제
			stmt.executeUpdate(rearrange_idx_sql);
				
			rearrange_idx_sql ="ALTER TABLE board_table ADD content_id int primary key auto_increment";
			//FIRST 구문은 나중에
			
			stmt.executeUpdate(rearrange_idx_sql);
		  	
		  	stmt.close();
			
			///일단 제거해도 됨
			
			
			new_board_dao.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		response.sendRedirect("/Servlet_BBS/list");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
