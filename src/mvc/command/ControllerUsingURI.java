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
    
	// ���� ���Ͽ� Ŀ�ǵ�(� command�ΰ�)�� Ŭ������ ���� ��� ( "boardList Ŀ�ǵ� -> ListHandlerŬ����. )
	
	// <Ŀ�ǵ�, �ڵ鷯 �ν��Ͻ�> ���� ������ ���� ���Ͽ� ����
	
	private Map<String, CommandHandler> commandHandlerMap = 
			new HashMap<>();
	
	public void init() throws ServletException {
		// ���� �����ϰ� �ʱ�ȭ �� �� ȣ��Ǵ� �޼���.
		System.out.println(" ��Ʈ�ѷ� using file ����!! ");
		String configFile = getInitParameter("configFile");
		
		configFile = "/WEB-INF/commandHandler.properties";
		System.out.println("config File = "+configFile);
		
		Properties prop = new Properties();	
		String configFilePath = getServletContext().getRealPath(configFile);
		File input = new File(configFilePath);
		// ������Ƽ�� ������
		// web.xml�� configFile�� properties��� ��� �Ǿ� ���� 
		// ȯ�漳�� ������ ��� �о��
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(input);
			prop.load(fis);
		} catch (Exception e2) {
		}
		Iterator keyIter = prop.keySet().iterator();
		
		while(keyIter.hasNext()) {
			String command = (String) keyIter.next();
			// ������Ƽ �̸�: Ŀ�ǵ� ��
			
			String handlerClassName = prop.getProperty(command);
			//Ŀ�ǵ� �̸��� �ش��ϴ� �ڵ鷯 Ŭ���� �̸��� property���� ���Ѵ�.
			
			try {
				Class<?> handlerClass = Class.forName(handlerClassName);
				//�ڵ鷯 Ŭ���� �̸��� �̿��ؼ� class ��ü�� ���Ѵ�..
				
				CommandHandler handlerInstance = 
						(CommandHandler) handlerClass.newInstance();
				// commandHandlerMap���� ��û�� ó���� �ڵ鷯 ��ü�� ���Ѵ�. 
				//(listHandler, ViewHandler ...)
				
				commandHandlerMap.put(command, handlerInstance); //(��ɾ�, �ڵ鷯)
				// commandHandlerMap�� (Ŀ�ǵ�, �ڵ鷯 ��ü) ���� ������ �����Ѵ�. 
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
		System.out.println("�Ѿ�� command= " + command);
		//���������� �Ѿ�� cmd �Ķ���� �� �޾ƿ�
		
		/*
		String command = request.getRequestURI(); 
		
		if(command.indexOf(request.getContextPath())==0) {
			command = command.substring(request.getContextPath().length());
			// substring�� �ڿ������� �ڸ���.
			// ��ü ��û URI: chap18/guestbook/list.do������
			// �����ø����̼� ��� �����ϸ� /guestbook/list.do 
			// request.getContextPath()�� �ش��ϴ� �κ��� ��ο��� ����. 
		}*/
		//Ŭ���̾�Ʈ�� ��û�� ��ɾ get����� ���� �޾ƿ�.
		//System.out.println("get �Ķ���ͷ� �޾ƿ� ��ɾ�= " + command);
		
		CommandHandler handler = commandHandlerMap.get(command);
		// Ŀ�ǵ� �Ķ���͸� Ű �� ����ؼ�, 
		// Ŀ�ǵ忡 ���� ���� �ٸ� �ڵ鷯 Ŭ���� �޾ƿ�. 
		
		if(handler == null) {
			handler = new NullHandler();
		}
		
		// ������ �ڵ鷯�� ���� ������ ��û ó��. 
		handler.doGet(request, response);
		handler.doPost(request, response);
		/*
	  	String viewPage = null;		
		try {
			viewPage = handler.process(request, response);
			//���� �� = ��� ������ view ������
			//Ŭ���̾�Ʈ�� ��û�� ó���� ��
			//view �������� ������ ������� request�� session�� �Ӽ��� ����.
		} catch (Exception e) {
			throw new ServletException(e);
		}
		if ( viewPage != null) {
			//command�� ���� view ������ �޶���
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
		}*/	
	 }
}
