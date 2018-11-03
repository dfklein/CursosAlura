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
 * � o formul�rio que posta para esta servelet
 * 
 * JSP: Veja o arquivo novaEmpresaCriada.jsp
 */
@WebServlet("/novaEmpresa")
public class NovaEmpresaServelet extends HttpServlet {

	private static final long serialVersionUID = -37322050198019998L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		
			// "nome" � como voc� chamou o atributo no input atravez da propriedade name
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

			// Nos coment�rios abaixo voc� usa o out do response e gera o html diretamente.
			// PrintWriter out = response.getWriter();
			// out.println("<html><body>Nome: " + nomeEmpresa + "</body></html>");
			
			// Mas faz mais sentido chamar um JSP:
			// Primeiro voc� processa a requisi��o que foi enviada ao servelet (o c�digo
			// acima) e no final set atributos � requisi��o.
			request.setAttribute("empresa", empresa.getNome());
			
			// **************************************************************************

			// Depois voc� usar� o m�todo forward da requisi��o, que vai transform�-la em resposta.
			// RequestDispatcher rd = request.getRequestDispatcher("/novaEmpresaCriada.jsp");
			// rd.forward(request, response);
			
			// Este bloco foi comentado para poder mostrar o racioc�nio sobre os problemas que o dispatcher pode causar no redirecionamento. Veja abaixo disso.
			
			// **************************************************************************
			
			// Nesta vers�o aqui, ao inv�s de chamar um JSP voc� est� chamando outro servlet (feito dentro do pr�prio servidor, server side, que � o que o dispatcher faz), criando uma esp�cie corrente entre elas
			// Para isto voc� alterou o m�todo implementado de /listaEmpresa para service pois o doGet n�o daria suporte (uma vez que este formul�rio aqui usa m�todo POST)
			// Por�m isso causa um problema: se voc� fizer desta forma e der F5 no navegador, ele vai repetir a requisi��o, repostando os mesmos dados.
			// Ou seja: voc� vai gerar um novo registro id�ntico sem nem ter preenchido o formul�rio.
			// RequestDispatcher rd = request.getRequestDispatcher("/listaEmpresas");
			// rd.forward(request, response);
		
			// Para resolver este problema, voc� precisa criar um redirecionamento client side
			// Ou seja: voc� envia a instru��o de redirecionamento para o navegador chamar o pr�ximo servlet, ao inv�s de cham�-lo diretamente do primeiro servlet.
			// O redirecionamento devolve para o navegador um c�digo da fam�lia 300
			response.sendRedirect("listaEmpresas"); // ATEN��O: n�o use a /
			
			// No entanto, com este redirecionamento, o navegador recebe o seu par�metro empresa (declarado na linha 55) mas n�o o redireciona para o servlet (ent�o voc� n�o v� a msg de empresa criada com sucesso em cima da lista)
			// Isto acontece porque o objeto tem a sua vida atrelada a um escopo de requisi��o e voc� precisa que ele esteja num escopo maior.
			
		} catch (ParseException e) {
			throw new ServletException(e);
		}
	}


}
