package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.alura.gerenciador.acao.Acao;

// @WebServlet("/entrada") // comentado porque a classe foi transformada em um filtro
@Deprecated
public class UnicaEntradaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String paramAcao = request.getParameter("acao");
			
			// O código abaixo foi implementado em um filtro.
//			HttpSession sessao = request.getSession();
//			
//			boolean usuarioNaoLogado = sessao.getAttribute("usuarioLogado") == null;
//			boolean isAcaoProtegida = !(paramAcao.equals("Login") || paramAcao.equals("LoginForm"));
//			
//			if(usuarioNaoLogado && isAcaoProtegida) {
//				response.sendRedirect("entrada?acao=LoginForm");
//				return;
//			}
			

			String nomeDaClasse = "br.com.alura.gerenciador.acao." + paramAcao;
			Class<?> clazz = Class.forName(nomeDaClasse);
			Acao acao = (Acao) clazz.newInstance();
			
			String nome = acao.executa(request, response);
			
//			String nome = null;
//			if (paramAcao.equals("ListaEmpresas")) {
//				ListaEmpresas acao = new ListaEmpresas();
//				nome = acao.executa(request, response);
//
//			} else if (paramAcao.equals("RemoveEmpresa")) {
//				RemoveEmpresa acao = new RemoveEmpresa();
//				nome = acao.executa(request, response);
//
//			} else if (paramAcao.equals("MostraEmpresa")) {
//				MostraEmpresa acao = new MostraEmpresa();
//				nome = acao.executa(request, response);
//
//			} else if (paramAcao.equals("AlteraEmpresa")) {
//				AlteraEmpresa acao = new AlteraEmpresa();
//				nome = acao.executa(request, response);
//
//			} else if (paramAcao.equals("NovaEmpresa")) {
//				NovaEmpresa acao = new NovaEmpresa();
//				nome = acao.executa(request, response);
//
//			} else if (paramAcao.equals("NovaEmpresaForm")) {
//				NovaEmpresaForm acao = new NovaEmpresaForm();
//				nome = acao.executa(request, response);
//
//			}

			String[] tipoEndereco = nome.split(":");

			if (tipoEndereco[0].equals("forward")) {
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/" + tipoEndereco[1]);
				rd.forward(request, response);
			} else {
				response.sendRedirect(tipoEndereco[1]);
			}

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new ServletException(e);
		}
	}

}
