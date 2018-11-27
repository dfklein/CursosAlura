package br.com.alura.gerenciador.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

//@WebFilter(urlPatterns= {"/entrada"})
// Os filtros foram comentados para ver a implementação deles no web.xml
public class MonitoramentoFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		long inicio = System.currentTimeMillis();
		System.out.println("--- filtro do MonitoramentoFilter");
		
		String acao = request.getParameter("acao");
		
		
		// A diferença entre o filtro e um servlet comum é que o filtro permite parar a requisição.
		// Se você não chama o doFilter, a requisição não segue para seus servlets.
		chain.doFilter(request, response);
		
		long fim = System.currentTimeMillis();
		System.out.println("Tempo da ação " + acao + " é " + (fim-inicio));
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	
	}
	
	@Override
	public void destroy() {
		
	
	}

}
