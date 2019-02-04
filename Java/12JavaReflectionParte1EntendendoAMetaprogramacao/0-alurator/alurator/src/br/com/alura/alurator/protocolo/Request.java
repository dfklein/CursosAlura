package br.com.alura.alurator.protocolo;

public class Request {
	
	private String nomeController;
	private String nomeMetodo;

	public Request(String url) {
		
		String[] partesUrl = url.startsWith("/") ? url.replaceFirst("/", "").split("/") : url.split("/");
		
		
		nomeController = partesUrl[0].concat("Controller");
		nomeController = nomeController.substring(0, 1).toUpperCase() + nomeController.substring(1);
		
		nomeMetodo = partesUrl.length <= 2 ? partesUrl[1] : null;
	}

	public String getNomeController() {
		return this.nomeController;
	}
	
	public String getNomeMetodo() {
		return nomeMetodo;
	}
}
