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
 * Servlet implementation class TestServlet
 */
//@WebServlet("/listServlet")->web.xml����.
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<BoardVO> vo_list;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().append("���⼭ served!").append(request.getContextPath());

		PrintWriter out = response.getWriter();
		
		out.println("<html>"+"<body>"+"<h1> ����Ʈ S E R V L E T YES! </h1>");
		out.println("<html>"+"<body>"+"<h2> servlet </h2>");
		//����Ʈ ��û ���� db������ ����!!!!!!
		System.out.println("DB�� ����");
		
		BoardDAO new_board_dao = new BoardDAO(); //���ο� DB���� ��ü ����!!!
		Connection conn;
		Statement stmt = null;
		ResultSet rs = null;
		
		vo_list = new ArrayList<BoardVO>(); //������ ��������. ���� ���⼭ .
		
		conn = new_board_dao.DBConnection();
		
		try {
			String list_sql = "SELECT * FROM board_table";
			stmt = conn.createStatement();
			int idx=0;  //row �� idx
			rs = stmt.executeQuery(list_sql);
			while(rs.next()){ //���� ���� ���� ������
				int col_i=1; //�÷� �� idx	
				String title = rs.getString(col_i++);
				String content = rs.getString(col_i++);
				String regDate = rs.getString(col_i++);
				String modDate = rs.getString(col_i++);
				int content_id = rs.getInt(col_i++);
				
				//DB���� �޾ƿ� ��ü��
				//�ϳ��� VO�� ����
				BoardVO data = new BoardVO();
				data.setTitle(title);
				data.setContent(content);
				data.setRegDate(regDate);
				data.setModDate(modDate);
				data.setContent_id(content_id); //�� ������� �� ���� BoardVO ��ü1�� ����.				
				//data�� list.jsp�� ����
				
				vo_list.add(data);
				idx++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//������ �� ������ �ڿ� DB ���� ����
		try {
			//DB���� ������ ������ �� DB�� ���� ����
			new_board_dao.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		
		//new_board_dao.getConnection();
		System.out.println("DB�� ����22");
		
		/* arraylist�� ���� ������ �Ŀ��� DB Close() */
		
		String test= "test_string";
		request.setAttribute("test", test);
		request.setAttribute("list_item", vo_list);
		
		ServletContext context = getServletContext(); //������Ʈ ��ΰ� ���´�.  
		//������Ʈ
		
		System.out.println("ServletContext = " + context);
		
		//RequestDispatcher rd = context.getRequestDispatcher("/view/list.jsp");
		//������Ʈ ��� + list.jsp (WebContent �ȿ� �ִ°� ��)
		RequestDispatcher rd = context.getRequestDispatcher("/list.jsp");
		rd.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
