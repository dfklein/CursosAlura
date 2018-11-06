package br.com.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NovaEmpresaServelet
 */
@WebServlet("/old/novaEmpresa")
public class NovaEmpresaServletOLD extends HttpServlet {

	private static final long serialVersionUID = -37322050198019998L;

	/**
	 * Se o método que você sobrescreve é o service(...), a sua servelet vai aceitar métodos GET e POST.
	 */
//	@Override
//	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	/**
	 * No entanto, se você quiser garantir que a sua servelet aceite apenas um 
	 * determinado método (ou que tenha um comportamento diferente para cada método)
	 * então você pode implementar outros métodos. Aqui o service(...) foi trocado
	 * por doPost, que só aceita método POST
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ***** FORMAS DE OBTER PARÂMETROS ***** //

		// 1 - você pode passar pela URL e obtê-lo pelo request.
		// ex: http://localhost:8080/gerenciador/novaEmpresa?nome=Alura&numero=2
		// (OBS: o exemplo só vai funcionar se você substituir o método sobrescrito por service(...) ou doGet(...)
		String param1 = request.getParameter("nome");
		String param2 = request.getParameter("numero");
		System.out.println(param1);
		System.out.println(param2);
		
		// 2 - A outra maneira é chamando a servlet com o método post. 
		// Para isso a sua página precisa ter um formulário.
		// 		- Na tag <form> o atributo "action" defina a URL da servelet.
		//		- Nas tags <input> o "name" define o nome dos parâmetros.
		//		- Você precisa ter um <input type="submit"/> para submeter o formulário
		// 		- Adicionar à tag <form> atributo method="post" 
		// 		- Se você pular o passo acima (não declarar "method" no form), o formulário vai gerar um GET passando os parâmetros pela URL. 
		// ex: ver o arquivo formNovaEmpresa.html (teste: http://localhost:8080/gerenciador/formNovaEmpresa.html)
		
		
		PrintWriter out = response.getWriter(); 
		out.println("<html><body>Nome: " + param1 +"<br />Número: " + param2 + "</body></html>");
		
	}


}
