package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
 * Servlet implementation class ViewServlet
 */
//@WebServlet("/ViewServlet")
public class ViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String content_id_str = request.getParameter("content_id");
		
		int content_id;
		
		System.out.println("---view servlet---");
		System.out.println("---View�� �Ѿ�� content id �� Ȯ�� : ---"+content_id_str);
		content_id = Integer.parseInt(content_id_str);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		out.println("�󼼺��� SERVLET");		
		
		BoardDAO new_board_dao = new BoardDAO(); //���ο� DB���� ��ü ����!!!
		
		Connection conn;
		Statement stmt = null;
		ResultSet rs = null;
		
		conn = new_board_dao.DBConnection();
		System.out.println("---------View SERVLET --------------");
		
		String detail_view_sql = "SELECT * FROM board_table WHERE content_id =" + content_id_str;			
		String title="", content="", regDate="", modDate="";
		
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(detail_view_sql);
			
			while(rs.next()){ //�ش� �Խù� ���� ����
				int i=1;	
				title = rs.getString("title");
				content = rs.getString("content");
				regDate = rs.getString(i++);
				modDate = rs.getString(i++);
			}
			BoardVO vo = new BoardVO();
			vo.setTitle(title);
			vo.setContent(content);
			vo.setRegDate(regDate);
			vo.setContent_id(content_id);			
			
			request.setAttribute("board_data", vo);	
			//VO��ü�� board_data��� �̸����� view.jsp�� �Ѱ���.
			
			//RequestDispatcher rd = context.getRequestDispatcher("/view/list.jsp");
			//������Ʈ ��� + list.jsp (WebContent �ȿ� �ִ°� ��)
			
			ServletContext context = getServletContext(); //������Ʈ ���
			System.out.println("ServletContext = " + context);
			RequestDispatcher rd = context.getRequestDispatcher("/view.jsp");
			rd.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			new_board_dao.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}