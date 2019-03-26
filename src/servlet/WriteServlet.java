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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* �Է� jsp ������ ����  */
		String whereTogo = "";
		whereTogo = request.getParameter("goto");
		
		System.out.println("����� �̵�= "+whereTogo);
		
		if(whereTogo.equals("moveToEnrollPage")) { //��� �������� �̵�
			
			response.sendRedirect("write.jsp"); //�Ķ���� �����ϱ� redirect
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	// �����ư Ŭ�� �� �Խñ� ��� �� list�� �̵�
		System.out.println(" -- ���� --  ");
		
		String title="", content="";
		String regDate, modDate;
		
		title = request.getParameter("title"); //post������� �Ѿ�� ��
		content = request.getParameter("content");
		
		System.out.println("�Ѿ�� title= "+title);
		System.out.println("content= "+content);			
		
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		 
		modDate=regDate = dayTime.format(new Date(time));
		
		//db connect
		
		BoardDAO new_board_dao = new BoardDAO();
				
		Connection conn;
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		ServletContext context; //������Ʈ ��� �޾� ��
		RequestDispatcher rd;
		
		conn = new_board_dao.DBConnection();
		
		String insert_sql =
				"INSERT INTO board_table (title, content, regDate, modDate) values(?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(insert_sql);
			//sql ����
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, regDate);
			pstmt.setString(4, modDate);
			pstmt.executeUpdate();
			
			new_board_dao.closeConnection();
			context = getServletContext();//������Ʈ ��� �޾� ��
			rd = context.getRequestDispatcher("/list");
			rd.forward(request, response);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
	}
}
