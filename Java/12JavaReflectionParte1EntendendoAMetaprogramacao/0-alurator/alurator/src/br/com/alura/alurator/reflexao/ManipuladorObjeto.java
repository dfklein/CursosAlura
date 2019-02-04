package br.com.alura.alurator.reflexao;

import java.lang.reflect.Method;

public class ManipuladorObjeto {

	private Object instancia;

	public ManipuladorObjeto(Object instancia) {
		this.instancia = instancia;
		
	}

	public ManipuladorMetodo getMetodo(String nomeMetodo) {
		Method metodo;
		try {
			metodo = instancia.getClass().getMethod(nomeMetodo);
			return new ManipuladorMetodo(metodo, this.instancia);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
