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
	 * Se o m�todo que voc� sobrescreve � o service(...), a sua servelet vai aceitar m�todos GET e POST.
	 */
//	@Override
//	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	/**
	 * No entanto, se voc� quiser garantir que a sua servelet aceite apenas um 
	 * determinado m�todo (ou que tenha um comportamento diferente para cada m�todo)
	 * ent�o voc� pode implementar outros m�todos. Aqui o service(...) foi trocado
	 * por doPost, que s� aceita m�todo POST
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ***** FORMAS DE OBTER PAR�METROS ***** //

		// 1 - voc� pode passar pela URL e obt�-lo pelo request.
		// ex: http://localhost:8080/gerenciador/novaEmpresa?nome=Alura&numero=2
		// (OBS: o exemplo s� vai funcionar se voc� substituir o m�todo sobrescrito por service(...) ou doGet(...)
		String param1 = request.getParameter("nome");
		String param2 = request.getParameter("numero");
		System.out.println(param1);
		System.out.println(param2);
		
		// 2 - A outra maneira � chamando a servlet com o m�todo post. 
		// Para isso a sua p�gina precisa ter um formul�rio.
		// 		- Na tag <form> o atributo "action" defina a URL da servelet.
		//		- Nas tags <input> o "name" define o nome dos par�metros.
		//		- Voc� precisa ter um <input type="submit"/> para submeter o formul�rio
		// 		- Adicionar � tag <form> atributo method="post" 
		// 		- Se voc� pular o passo acima (n�o declarar "method" no form), o formul�rio vai gerar um GET passando os par�metros pela URL. 
		// ex: ver o arquivo formNovaEmpresa.html (teste: http://localhost:8080/gerenciador/formNovaEmpresa.html)
		
		
		PrintWriter out = response.getWriter(); 
		out.println("<html><body>Nome: " + param1 +"<br />N�mero: " + param2 + "</body></html>");
		
	}


}
