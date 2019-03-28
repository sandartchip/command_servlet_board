package mvc.command;

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.dao.BoardDAO;
import com.board.vo.BoardVO;

/**
 * Servlet implementation class ListHandler
 */
//@WebServlet("/ListHandler")
public class ListHandler extends HttpServlet implements CommandHandler  {
	private static final long serialVersionUID = 1L;
	ArrayList<BoardVO> vo_list; 
    public ListHandler() {
        super(); 
    }
    
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		
		//RequestDispatcher rd = context.getRequestDispatcher("/view/list.jsp");
		//������Ʈ ��� + list.jsp (WebContent �ȿ� �ִ°� ��)
		
		/*
		 * ServletContext context = getServletContext(); //������Ʈ ��ΰ� ���´�.  
		//������Ʈ
		
		System.out.println("ServletContext = " + context);

		 * RequestDispatcher rd = context.getRequestDispatcher("/list.jsp");
		rd.forward(request, response);*/
		//String list_url = "/Servlet_BBS/view/list.jsp";
		//String list_url = "/view/list.jsp";
		String list_url = "/WEB-INF/view/list.jsp";
		//RequestDispatcher rd = request.getRequestDispatcher(hello_url);
		//rd.forward(request, response);
		RequestDispatcher rd = request.getRequestDispatcher(list_url);
		rd.forward(request, response);
		 
	}
 
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}
}
