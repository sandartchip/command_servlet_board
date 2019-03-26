package servlet;

import java.io.IOException;
import java.sql.Connection;
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
			
			//System.out.println("수정할 게시물 제목"+title + "내용"+content);
			request.setAttribute("name", "hihi");
			
			//request.setAttribute("original_data", vo);
						
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
