package mvc.command;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDAO;

/**
 * Servlet implementation class DeleteHandler
 */
@WebServlet("/DeleteHandler")
public class DeleteHandler extends HttpServlet implements CommandHandler{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		System.out.println("-------delete-------");
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
		

		String view_url = "/Servlet_BBS/controller?cmd=list";
//		String view_url = "/Servlet_BBS/controller?cmd=list";
		//rd = request.getRequestDispatcher(view_url);
		response.sendRedirect(view_url);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
