package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.alura.gerenciador.acao.Acao;

//@WebFilter("/entrada")
//Os filtros foram comentados para ver a implementação deles no web.xml
public class ControladorFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	
	}
	
	@Override
	public void destroy() {
		
	
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		try {
			// O HttpServletRequest implementa o ServletRequest. Portanto você precisa fazer um cast para ter acesso a alguns recursos.
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			
			String paramAcao = request.getParameter("acao");
			
			String nomeDaClasse = "br.com.alura.gerenciador.acao." + paramAcao;
			Class<?> clazz = Class.forName(nomeDaClasse);
			Acao acao = (Acao) clazz.newInstance();
			
			String nome = acao.executa(request, response);
			
			String[] tipoEndereco = nome.split(":");

			if (tipoEndereco[0].equals("forward")) {
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/" + tipoEndereco[1]);
				rd.forward(request, response);
			} else {
				response.sendRedirect(tipoEndereco[1]);
			}
			
			// Neste filtro você não usa o chain.doFilter porque as ações são o forward e o redirect 
			// chain.doFilter(request, response);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

}
