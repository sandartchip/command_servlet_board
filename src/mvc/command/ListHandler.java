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
		response.getWriter().append("여기서 served!").append(request.getContextPath());

		PrintWriter out = response.getWriter();
		
		out.println("<html>"+"<body>"+"<h1> 리스트 S E R V L E T YES! </h1>");
		out.println("<html>"+"<body>"+"<h2> servlet </h2>");
		//리스트 요청 오면 db연결을 하자!!!!!!
		System.out.println("DB로 고고씽");
		
		BoardDAO new_board_dao = new BoardDAO(); //새로운 DB연결 객체 생성!!!
		Connection conn;
		Statement stmt = null;
		ResultSet rs = null;
		
		vo_list = new ArrayList<BoardVO>(); //선언은 전역으로. 값은 여기서 .
		
		conn = new_board_dao.DBConnection();
		
		try {
			String list_sql = "SELECT * FROM board_table";
			stmt = conn.createStatement();
			int idx=0;  //row 용 idx
			rs = stmt.executeQuery(list_sql);
			while(rs.next()){ //다음 값이 있을 때까지
				int col_i=1; //컬럼 용 idx	
				String title = rs.getString(col_i++);
				String content = rs.getString(col_i++);
				String regDate = rs.getString(col_i++);
				String modDate = rs.getString(col_i++);
				int content_id = rs.getInt(col_i++);
				
				//DB에서 받아온 객체를
				//하나의 VO로 만들어서
				BoardVO data = new BoardVO();
				data.setTitle(title);
				data.setContent(content);
				data.setRegDate(regDate);
				data.setModDate(modDate);
				data.setContent_id(content_id); //각 멤버변수 값 가진 BoardVO 객체1개 생성.				
				//data를 list.jsp로 전달
				
				vo_list.add(data);
				idx++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//데이터 다 가져온 뒤에 DB 연결 끊기
		try {
			//DB에서 데이터 가져온 뒤 DB와 연결 해제
			new_board_dao.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		
		//new_board_dao.getConnection();
		System.out.println("DB로 고고씽22");
		
		/* arraylist로 정보 모으고난 후에는 DB Close() */
		
		String test= "test_string";
		request.setAttribute("test", test);
		request.setAttribute("list_item", vo_list); 
		
		//RequestDispatcher rd = context.getRequestDispatcher("/view/list.jsp");
		//프로젝트 경로 + list.jsp (WebContent 안에 있는건 함)
		
		/*
		 * ServletContext context = getServletContext(); //프로젝트 경로가 나온다.  
		//프로젝트
		
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
