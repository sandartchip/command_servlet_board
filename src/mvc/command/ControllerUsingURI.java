package mvc.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ControllerUsingURI extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// 설정 파일에 커맨드(어떤 command인가)와 클래스의 관계 명시 ( "boardList 커맨드 -> ListHandler클래스. )
	
	// <커맨드, 핸들러 인스턴스> 매핑 정보를 설정 파일에 저장
	
	private Map<String, CommandHandler> commandHandlerMap = 
			new HashMap<>();
	
	public void init() throws ServletException {
		// 서블릿 생성하고 초기화 할 때 호출되는 메서드.
		System.out.println(" 컨트롤러 using file 서블릿!! ");
		String configFile = getInitParameter("configFile");
		
		configFile = "/WEB-INF/commandHandler.properties";
		System.out.println("config File = "+configFile);
		
		Properties prop = new Properties();	
		String configFilePath = getServletContext().getRealPath(configFile);
		File input = new File(configFilePath);
		// 프로퍼티를 관리함
		// web.xml의 configFile에 properties경로 등록 되어 있음 
		// 환경설정 파일의 경로 읽어옴
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(input);
			prop.load(fis);
		} catch (Exception e2) {
		}
		Iterator keyIter = prop.keySet().iterator();
		
		while(keyIter.hasNext()) {
			String command = (String) keyIter.next();
			// 프로퍼티 이름: 커맨드 명
			
			String handlerClassName = prop.getProperty(command);
			//커맨드 이름에 해당하는 핸들러 클래스 이름을 property에서 구한다.
			
			try {
				Class<?> handlerClass = Class.forName(handlerClassName);
				//핸들러 클래스 이름을 이용해서 class 객체를 구한다..
				
				CommandHandler handlerInstance = 
						(CommandHandler) handlerClass.newInstance();
				// commandHandlerMap에서 요청을 처리할 핸들러 객체를 구한다. 
				//(listHandler, ViewHandler ...)
				
				commandHandlerMap.put(command, handlerInstance); //(명령어, 핸들러)
				// commandHandlerMap에 (커맨드, 핸들러 객체) 매핑 정보를 저장한다. 
			} catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		process(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		process(request, response);
	}
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String command = request.getParameter("cmd"); 
		System.out.println("넘어온 command= " + command);
		//브라우저에서 넘어온 cmd 파라미터 값 받아옴
		
		/*
		String command = request.getRequestURI(); 
		
		if(command.indexOf(request.getContextPath())==0) {
			command = command.substring(request.getContextPath().length());
			// substring은 뒤에서부터 자른다.
			// 전체 요청 URI: chap18/guestbook/list.do이지만
			// 웹어플리케이션 경로 제외하면 /guestbook/list.do 
			// request.getContextPath()에 해당하는 부분을 경로에서 제거. 
		}*/
		//클라이언트가 요청한 명령어를 get방식을 통해 받아옴.
		//System.out.println("get 파라미터로 받아온 명령어= " + command);
		
		CommandHandler handler = commandHandlerMap.get(command);
		// 커맨드 파라미터를 키 로 사용해서, 
		// 커맨드에 따라 각각 다른 핸들러 클래스 받아옴. 
		
		if(handler == null) {
			handler = new NullHandler();
		}
		
		// 각각의 핸들러에 따라 적절한 요청 처리. 
		handler.doGet(request, response);
		handler.doPost(request, response);
		/*
	  	String viewPage = null;		
		try {
			viewPage = handler.process(request, response);
			//리턴 값 = 결과 보여줄 view 페이지
			//클라이언트의 요청을 처리한 후
			//view 페이지에 보여줄 결과값을 request나 session의 속성에 저장.
		} catch (Exception e) {
			throw new ServletException(e);
		}
		if ( viewPage != null) {
			//command에 따라서 view 페이지 달라짐
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
		}*/	
	 }
}
