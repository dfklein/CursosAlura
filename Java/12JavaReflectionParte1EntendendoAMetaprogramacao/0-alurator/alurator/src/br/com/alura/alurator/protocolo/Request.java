package br.com.alura.alurator.protocolo;

import java.util.HashMap;
import java.util.Map;

public class Request {
	
	private String nomeController;
	private String nomeMetodo;
	private Map<String, Object> queryParams;

	/*
	 * Casos possiveis:
	 * /controlador/metodo
	 * /controlador/metodo?param1=valor1&param2=valor2
	 * 
	 * /produto/filtra?nome=produto&marca=marca 1
	 * /produto/filtra?nome=marca 1&nome=produto
	 */
	public Request(String url) {
		
		String[] partesUrl = url.startsWith("/") ? url.replaceFirst("/", "").split("[?]") : url.split("[?]");
		
		String[] controleEMetodo = partesUrl[0].split("/");
		
		nomeController = controleEMetodo[0].concat("Controller");
		nomeController = nomeController.substring(0, 1).toUpperCase() + nomeController.substring(1);
		
		nomeMetodo = controleEMetodo[1];
		
		queryParams = partesUrl.length > 1 ? new QueryParamsBuilder().withParams(partesUrl[1]).build() : new HashMap<String, Object>();
	}

	public String getNomeController() {
		return this.nomeController;
	}
	
	public String getNomeMetodo() {
		return nomeMetodo;
	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}
	
}
