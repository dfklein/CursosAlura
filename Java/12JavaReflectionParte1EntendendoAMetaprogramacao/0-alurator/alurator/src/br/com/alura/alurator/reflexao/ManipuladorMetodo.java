package br.com.alura.alurator.reflexao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ManipuladorMetodo {

	private Method metodo;
	private Object instancia;

	public ManipuladorMetodo(Method metodo, Object instancia) {
		this.metodo = metodo;
		this.instancia = instancia;
	}

	public Object invoca() {
		try {
			return metodo.invoke(instancia);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Exeção lançada pelo construtor do método", e);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

}
