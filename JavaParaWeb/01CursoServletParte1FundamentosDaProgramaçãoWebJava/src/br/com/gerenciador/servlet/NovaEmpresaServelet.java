package br.com.gerenciador.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		try {
		
			// "nome" é como você chamou o atributo no input atravez da propriedade name
			// dele.
			String nomeEmpresa = request.getParameter("nome");
			String paramDataEmpresa = request.getParameter("data");

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date dataEmpresa = sdf.parse(paramDataEmpresa);

			Empresa empresa = new Empresa();
			empresa.setNome(nomeEmpresa);
			empresa.setDataAbertura(dataEmpresa);

			Banco banco = new Banco();
			banco.adiciona(empresa);

			// Nos comentários abaixo você usa o out do response e gera o html diretamente.
			// PrintWriter out = response.getWriter();
			// out.println("<html><body>Nome: " + nomeEmpresa + "</body></html>");
			
			// Mas faz mais sentido chamar um JSP:
			// Primeiro você processa a requisição que foi enviada ao servelet (o código
			// acima) e no final set atributos à requisição.
			request.setAttribute("empresa", empresa.getNome());
			
			// **************************************************************************

			// Depois você usará o método forward da requisição, que vai transformá-la em resposta.
			// RequestDispatcher rd = request.getRequestDispatcher("/novaEmpresaCriada.jsp");
			// rd.forward(request, response);
			
			// Este bloco foi comentado para poder mostrar o raciocínio sobre os problemas que o dispatcher pode causar no redirecionamento. Veja abaixo disso.
			
			// **************************************************************************
			
			// Nesta versão aqui, ao invés de chamar um JSP você está chamando outro servlet (feito dentro do próprio servidor, server side, que é o que o dispatcher faz), criando uma espécie corrente entre elas
			// Para isto você alterou o método implementado de /listaEmpresa para service pois o doGet não daria suporte (uma vez que este formulário aqui usa método POST)
			// Porém isso causa um problema: se você fizer desta forma e der F5 no navegador, ele vai repetir a requisição, repostando os mesmos dados.
			// Ou seja: você vai gerar um novo registro idêntico sem nem ter preenchido o formulário.
			// RequestDispatcher rd = request.getRequestDispatcher("/listaEmpresas");
			// rd.forward(request, response);
		
			// Para resolver este problema, você precisa criar um redirecionamento client side
			// Ou seja: você envia a instrução de redirecionamento para o navegador chamar o próximo servlet, ao invés de chamá-lo diretamente do primeiro servlet.
			// O redirecionamento devolve para o navegador um código da família 300
			response.sendRedirect("listaEmpresas"); // ATENÇÃO: não use a /
			
			// No entanto, com este redirecionamento, o navegador recebe o seu parâmetro empresa (declarado na linha 55) mas não o redireciona para o servlet (então você não vê a msg de empresa criada com sucesso em cima da lista)
			// Isto acontece porque o objeto tem a sua vida atrelada a um escopo de requisição e você precisa que ele esteja num escopo maior.
			
		} catch (ParseException e) {
			throw new ServletException(e);
		}
	}


}
