package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDAO;
import com.board.vo.BoardVO;

/**
 * Servlet implementation class ModifyServlet
 */
//@WebServlet("/ModifyServlet")
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    //원래 데이터 보내주기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// view.jsp -> get방식 으로 content_id 넘어온다.
		String content_id_str;
		
		content_id_str = request.getParameter("content_id");
		
		System.out.println("modify content id = " + content_id_str);
				
		Connection conn;
		Statement stmt=null;
		ResultSet rs =null;

		BoardDAO new_board_dao = new BoardDAO();
		conn = new_board_dao.DBConnection();
		
		String detail_view_sql = "SELECT * FROM board_table WHERE content_id =" + content_id_str;			
		String title="", content="", regDate="", modDate="";
		int content_id;
		content_id = Integer.parseInt(content_id_str);
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(detail_view_sql);
			
			while(rs.next()) {
				title = rs.getString("title");
				content = rs.getString("content");
				regDate = rs.getString("regDate");
				modDate = rs.getString("modDate");
			}			
			
			BoardVO vo = new BoardVO();
			vo.setTitle(title);
			vo.setContent(content);
			vo.setRegDate(regDate);
			vo.setModDate(modDate);	
			vo.setContent_id(content_id);
			
			request.setAttribute("original_data", vo);
						
			// vo에 객체 넣어주고 난 후 DB와 접속 끊는다
			
			ServletContext context; //프로젝트 경로 받아 옴
			context = getServletContext();
			RequestDispatcher rd = context.getRequestDispatcher("/modify.jsp");
			rd.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			new_board_dao.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//수정데이터 저장 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 수정 입력 화면에서 넘어온 데이터 */
		String title, content, content_id_str;
		int content_id;
		String modDate="2019.3.2";
		String regDate="2019.3.1 5:17";
		
		title = request.getParameter("title");
		content = request.getParameter("content");
		content_id_str = request.getParameter("content_id_str");
		content_id = Integer.parseInt(content_id_str);
				
		Connection conn = null;
		PreparedStatement pstmt = null;
		BoardDAO new_board_dao = new BoardDAO();
		conn = new_board_dao.DBConnection();
		
		String modify_sql =
				"UPDATE board_table SET TITLE= ?, CONTENT=?, modDate=?  WHERE content_id=?";
		
		try {
			pstmt = conn.prepareStatement(modify_sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, modDate);
			pstmt.setInt(4, content_id);
			pstmt.executeUpdate(); // 수정 쿼리문 수행
			new_board_dao.closeConnection();
		} catch (SQLException e) {
			
		}
		// Modify에서  view로 이동하면서  수정된 BoardVO 넘긴다
		BoardVO new_vo = new BoardVO();
		
		new_vo.setTitle(title);
		new_vo.setContent(content);
		new_vo.setModDate(modDate);
		new_vo.setRegDate(regDate);
		new_vo.setContent_id(content_id);
		
		//저장 후 상세보기로 이동
		System.out.println("저장 후 상세보기로 이동");
		request.setAttribute("board_data", new_vo);
		//response.sendRedirect("view.jsp");
		
		ServletContext context; //프로젝트 경로 받아 옴
		context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/view.jsp");
		rd.forward(request, response);
	}
}