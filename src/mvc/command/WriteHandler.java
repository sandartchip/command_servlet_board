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
    	// �׳� �̰� ������ �̵���.
    	request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		System.out.println(" ��� �������� �Ѿ� �� ");		
		
		/* �Է� jsp ������ ����  */
		String whereTogo = "";
		whereTogo = request.getParameter("goto");
		
		//System.out.println("����� �̵�= "+whereTogo);
		
//		if(whereTogo.equals("goto_enroll")) { //��� �������� �̵�
			
		//response.sendRedirect("/WEB-INF/view/write.jsp"); //�Ķ���� �����ϱ� redirect

		if(whereTogo.equals("goto_enroll")) { 
			String write_url = "/WEB-INF/view/write.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(write_url);			
			rd.forward(request, response); 
		}
	}
 
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ������ ���� init ����?
		// 1. �Ѿ�� �� ����� �� �Ѿ�� ??
		
		// 2. �Ѿ�� ���� utf-8�� ��ȯ�Ǽ� �Ѿ�Դµ�
		//  
		System.out.println("writehandler ���ڵ� Ÿ��="+request.getCharacterEncoding());
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String whereTogo = "";
		whereTogo = request.getParameter("goto");
		System.out.println("whereToGo"+whereTogo);
		if(whereTogo.equals("goto_enroll")) { 
			return; // ������ �ƴ϶� ������ �̵��� ���� �޺κ� ����X 
		}
		if(whereTogo.equals("data_enroll")) {
			// �����ư Ŭ�� �� �Խñ� ��� �� list�� �̵�
			System.out.println(" -- ���� --  ");
			
			String title="", content="";
			String regDate, modDate;	
			
			
			title = request.getParameter("title"); //post������� �Ѿ�� ��
			content = request.getParameter("content");
			String utf_name = URLDecoder.decode(title, "UTF-8");
			
			System.out.println("============================");
			System.out.println("�Ѿ�� title= "+title);
			System.out.println("content= "+content);
			System.out.println("�Ѿ�� title= "+utf_name);

		//	title="����";
		//	content="��2";
			/* ���� �ð� ���ϱ� */
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
				/*
				new_board_dao.closeConnection();
				context = getServletContext();//������Ʈ ��� �޾� ��
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
