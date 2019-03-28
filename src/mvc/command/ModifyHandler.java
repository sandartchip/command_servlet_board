package mvc.command;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDAO;
import com.board.vo.BoardVO;

/**
 * Servlet implementation class ModifyHandler
 */
@WebServlet("/ModifyHandler")
public class ModifyHandler extends HttpServlet implements CommandHandler{
	private static final long serialVersionUID = 1L;
       
    public ModifyHandler() {
        super(); 
    }
 
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content_id_str;
		String where_togo = request.getParameter("goto");
		
		if(where_togo.equals("modify_enroll")) {
			return; //post니까 실행하지마셈 
		}
		
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
			
			String modify_url = "/WEB-INF/view/modify.jsp";
			//RequestDispatcher rd = request.getRequestDispatcher(hello_url);
			//rd.forward(request, response);
			RequestDispatcher rd = request.getRequestDispatcher(modify_url);
			rd.forward(request, response);
			/*
			ServletContext context; //프로젝트 경로 받아 옴
			context = getServletContext();
			RequestDispatcher rd = context.getRequestDispatcher("/modify.jsp");
			rd.forward(request, response); */
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			new_board_dao.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title, content, content_id_str;
		int content_id;
		
		String where_togo = request.getParameter("goto");
		
		if(where_togo.equals("goto_modify")) {
			return; //post니까 실행하지마셈 
		}
		/* 현재 시간 구하기 */
		String regDate="", modDate;
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dayTime.format(new Date(time));
		modDate = str;
		/* 현재시간 업데이트 */
		
		
		System.out.println(" 수정!!! 저장!!!!!!!!!!!!!");
		title = request.getParameter("title");
		content = request.getParameter("content");
		content_id_str = request.getParameter("content_id_str");
		System.out.println("저장할 게시물 번호"+content_id_str);
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
			
		} catch (SQLException e) {
			
		}
		// Modify에서  view로 이동하면서  수정된 BoardVO 넘긴다
		BoardVO new_vo = new BoardVO();
		
		// DB에서 regDate 가져오기

		ResultSet rs = null;
		String reg_sql = "SELECT regDate from board_table WHERE content_id=?";
		
		try {
			pstmt = conn.prepareStatement(reg_sql);
			pstmt.setInt(1, content_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				regDate = rs.getString("regDate");
			}
			new_board_dao.closeConnection();
		} 
		catch (SQLException e) {			
		}
		
		new_vo.setTitle(title);
		new_vo.setContent(content);
		new_vo.setModDate(modDate);
		new_vo.setRegDate(regDate);
//		new_vo.setRegDate(regDate);
		new_vo.setContent_id(content_id);
		
		//저장 후 상세보기로 이동
		System.out.println("저장 후 상세보기로 이동");
		request.setAttribute("board_data", new_vo);
		//response.sendRedirect("view.jsp");
		/*
		ServletContext context; //프로젝트 경로 받아 옴
		context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/view.jsp");
		rd.forward(request, response);*/
		

		String view_url = "/Servlet_BBS/controller?cmd=view&content_id="+content_id;
//		String view_url = "/Servlet_BBS/controller?cmd=list";
		//rd = request.getRequestDispatcher(view_url);
		response.sendRedirect(view_url);
	}

}
