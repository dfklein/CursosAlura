package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import br.com.alura.gerenciador.modelo.Banco;
import br.com.alura.gerenciador.modelo.Empresa;

/**
 * Servlet implementation class EmpresasService
 */
@WebServlet("/empresas")
public class EmpresasService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Banco banco = new Banco();
		List<Empresa> empresas = banco.getEmpresas();
		
		// "Accept" é o nome do elemento do cabeçalho que você colocou na requisição. Ela é meio padrão de ter em uma requisição (as geradas por navegador têm)
		// Ver a classe ClienteWebService do projeto cliente.
		String contentType = request.getHeader("Accept");
		System.out.println(contentType);
		
		// Você usou "contains" aqui para testar a requisição feita pelo navegador (que tem um Accept no header da requisição mais complexo que o da aplicação cliente) 
		if (contentType.contains("application/xml")) {
			XStream xstream = new XStream();
			xstream.alias("empresa", Empresa.class); // define um alias para um determinado objeto quando o mesmo é listado no XML
			String json = xstream.toXML(empresas);
			response.setContentType("application/xml");
			response.getWriter().print(json);
		} else {
			Gson gson = new Gson();
			String json = gson.toJson(empresas);

			response.setContentType("application/json");
			response.getWriter().print(json);
			
		}
		
		
	}


}
