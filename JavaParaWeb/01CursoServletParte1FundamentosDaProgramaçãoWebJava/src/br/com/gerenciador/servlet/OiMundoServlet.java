package br.com.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(urlPatterns="/oi") // comentado para demonstrar o mapeamento pelo web.xml
public class OiMundoServlet extends HttpServlet {

	private static final long serialVersionUID = 3665823450861138203L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Primeiro servlet!</h1>");
		out.println("</body>");
		out.println("</html>");
		
	}
	
	// OBS: Todas as servlets s�o singletons por padr�o. S�o instanciadas pelo middleware por invers�o de controle.
	// Claro que isto falando de servlets e n�o de frameworks de mais alto n�vel como o JSF.

}
