package br.com.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ACESSE: http://localhost:8080/gerenciador/formNovaEmpresa.html
 * http://localhost:8080/gerenciador/listaEmpresas
 * 
 * É o formulário que posta para esta servelet
 * 
 * JSP: Veja o arquivo novaEmpresaCriada.jsp
 */
@WebServlet("/novaEmpresa")
public class NovaEmpresaServelet extends HttpServlet {

	private static final long serialVersionUID = -37322050198019998L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nomeEmpresa = request.getParameter("nome");
		
		Empresa empresa = new Empresa();
		empresa.setNome(nomeEmpresa);
		
		Banco banco = new Banco();
		banco.adiciona(empresa);
		
		// Nos comentários abaixo você usa o out do response e gera o html diretamente.
		//		PrintWriter out = response.getWriter(); 
		//		out.println("<html><body>Nome: " + nomeEmpresa + "</body></html>");
		
		// Mas faz mais sentido chamar um JSP:
		// Primeiro você processa a requisição que foi enviada ao servelet (o código acima) e no final set atributos à requisição.
		request.setAttribute("empresa", empresa.getNome());
		// Depois você usará o método forward da requisição, que vai transformá-la em resposta. 
		RequestDispatcher rd = request.getRequestDispatcher("/novaEmpresaCriada.jsp");
		rd.forward(request, response);
		
		
	}


}
