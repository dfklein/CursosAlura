package br.com.gerenciador.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editarEmpresaServlet")
public class EditarEmpresaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer idEmpresa = Integer.valueOf(request.getParameter("id"));
		Empresa emp = new Banco().getEmpresaById(idEmpresa);
		
		request.setAttribute("empresa", emp);
		// para popular os inputs com os valores deste objeto, você utiliza o atributo "value" dentro do input.
		// Ver como ficou o arquivo formAlteraEmpresa.jsp
		
		RequestDispatcher rd = request.getRequestDispatcher("/formAlteraEmpresa.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Integer id = Integer.valueOf(request.getParameter("id"));
			String nomeEmpresa = request.getParameter("nome");
			String paramDataEmpresa = request.getParameter("data");

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date dataEmpresa = sdf.parse(paramDataEmpresa);
			
			Empresa emp = new Banco().getEmpresaById(id);
			emp.setNome(nomeEmpresa);
			emp.setDataAbertura(dataEmpresa);
			
			response.sendRedirect("listaEmpresas"); // ATENÇÃO: não use a /
			
		} catch (ParseException e) {
			throw new ServletException(e);
		}
	}

}
