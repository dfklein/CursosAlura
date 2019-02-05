package br.com.alura.alurator;

import java.util.Map;

import br.com.alura.alurator.protocolo.Request;
import br.com.alura.alurator.reflexao.Reflexao;

public class Alurator {
	
	private String pacoteBase;

	public Alurator(String pacoteBase) {
		this.pacoteBase = pacoteBase;
		
	}
	
	@SuppressWarnings("deprecation")
	public Object executa(String url) {
			Request request = new Request(url);
			
			String nomeController = request.getNomeController();
			String nomeMetodo = request.getNomeMetodo();
			Map<String, Object> params = request.getQueryParams();
			
			// O código abaixo foi passado para outra classe apenas por questões de design.
			// O instrutor julga mais adequado você fazer uma classe Builder para isso.
			// ** -	Class<?> classeControle = Class.forName(pacoteBase + nomeController);
			// ** -	Object instanciaController = classeControle.getConstructor().newInstance();
			Object instanciaController = new Reflexao()
				.refleteClasse(pacoteBase, nomeController)
				.criaInstancia()
				.getMetodo(nomeMetodo, params)
				.invoca();
			
			System.out.println(instanciaController);
			
			return instanciaController;
			
	}
}
