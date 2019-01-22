package br.com.alura.lojaweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/contato"})
public class Contato extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
        writer.println("<html><h2>Entre em Contato</h2></html>");
        writer.close();
	}

}
