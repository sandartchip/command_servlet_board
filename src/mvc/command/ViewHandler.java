package mvc.command;

import java.io.IOException;

import java.io.PrintWriter;
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
 
public class ViewHandler extends HttpServlet implements CommandHandler {
	private static final long serialVersionUID = 1L;
       
    public ViewHandler() {
        super();
    }
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("---view----�� �̵�");
		String content_id_str = request.getParameter("content_id");
		
		int content_id;
		
		System.out.println("---view servlet---");
		System.out.println("---View�� �Ѿ�� content id �� Ȯ�� : ---"+content_id_str);
		content_id = Integer.parseInt(content_id_str);
		
		
		response.setContentType("text/html;charset=utf-8");
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
			
			request.setAttribute("board_data", vo);	
			//VO��ü�� board_data��� �̸����� view.jsp�� �Ѱ���.
			
			//RequestDispatcher rd = context.getRequestDispatcher("/view/list.jsp");
			//������Ʈ ��� + list.jsp (WebContent �ȿ� �ִ°� ��)
			
			/*ServletContext context = getServletContext(); //������Ʈ ���
			System.out.println("ServletContext = " + context);
			*/
			
			String view_url = "/WEB-INF/view/view.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(view_url);			
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
 
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	}    
}
