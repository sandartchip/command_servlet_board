package mvc.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandHandler {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	/*
	 * public String process(HttpServletRequest request, HttpServletResponse response) 
		throws Exception;
	*/
}
