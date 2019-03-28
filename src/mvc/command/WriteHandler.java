package mvc.command;

import java.io.IOException;
import java.net.URLDecoder;
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


public class WriteHandler extends HttpServlet implements CommandHandler {
	private static final long serialVersionUID = 1L;
        
    public WriteHandler() {
        super();
         
    } 
    
    @Override
    public void init() throws ServletException{
    	//response.setCharacterEncoding("utf-8");
    }
    
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 그냥 이건 페이지 이동용.
    	request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		System.out.println(" 등록 구간으로 넘어 옴 ");		
		
		/* 입력 jsp 페이지 띄우기  */
		String whereTogo = "";
		whereTogo = request.getParameter("goto");
		
		//System.out.println("여기로 이동= "+whereTogo);
		
//		if(whereTogo.equals("goto_enroll")) { //등록 페이지로 이동
			
		//response.sendRedirect("/WEB-INF/view/write.jsp"); //파라미터 없으니까 redirect

		if(whereTogo.equals("goto_enroll")) { 
			String write_url = "/WEB-INF/view/write.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(write_url);			
			rd.forward(request, response); 
		}
	}
 
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 서블릿의 생성 init 과정?
		// 1. 넘어올 때 제대로 안 넘어옴 ??
		
		// 2. 넘어올 때는 utf-8로 변환되서 넘어왔는데
		//  
		System.out.println("writehandler 인코딩 타입="+request.getCharacterEncoding());
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String whereTogo = "";
		whereTogo = request.getParameter("goto");
		System.out.println("whereToGo"+whereTogo);
		if(whereTogo.equals("goto_enroll")) { 
			return; // 저장이 아니라 페이지 이동일 때는 뒷부분 수행X 
		}
		if(whereTogo.equals("data_enroll")) {
			// 저장버튼 클릭 시 게시글 등록 후 list로 이동
			System.out.println(" -- 저장 --  ");
			
			String title="", content="";
			String regDate, modDate;	
			
			
			title = request.getParameter("title"); //post방식으로 넘어온 값
			content = request.getParameter("content");
			String utf_name = URLDecoder.decode(title, "UTF-8");
			
			System.out.println("============================");
			System.out.println("넘어온 title= "+title);
			System.out.println("content= "+content);
			System.out.println("넘어온 title= "+utf_name);

		//	title="새거";
		//	content="하2";
			/* 현재 시간 구하기 */
			long time = System.currentTimeMillis(); 

			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String str = dayTime.format(new Date(time));

			modDate = str;
			regDate = str;
			
			//db connect
			
			BoardDAO new_board_dao = new BoardDAO();
					
			Connection conn;
			PreparedStatement pstmt=null;
			ResultSet rs =null;
			ServletContext context; //프로젝트 경로 받아 옴
			RequestDispatcher rd;
			
			conn = new_board_dao.DBConnection();
			
			String insert_sql =
					"INSERT INTO board_table (title, content, regDate, modDate) values(?,?,?,?)";
			try {
				pstmt = conn.prepareStatement(insert_sql);
				//sql 쿼리
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.setString(3, regDate);
				pstmt.setString(4, modDate);
				pstmt.executeUpdate();
				/*
				new_board_dao.closeConnection();
				context = getServletContext();//프로젝트 경로 받아 옴
				rd = context.getRequestDispatcher("/list");
				rd.forward(request, response);
				*/
				
				String view_url = "/Servlet_BBS/controller?cmd=list";
//				String view_url = "/Servlet_BBS/controller?cmd=list";
				//rd = request.getRequestDispatcher(view_url);
				response.sendRedirect(view_url);
				
				//rd.forward(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
