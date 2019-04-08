package br.com.gerenciador.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/removeEmpresaServlet")
public class RemoveEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			Integer paramId = Integer.valueOf(request.getParameter("id"));
			System.out.println("Enviado id " + paramId);
			
			Banco bn = new Banco();
			bn.removeEmpresa(paramId);
			
			response.sendRedirect("listaEmpresas");
			
		} catch (NumberFormatException e) {
			throw new ServletException(e);
		}
	}


}
