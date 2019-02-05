package br.com.alura.alurator.reflexao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

public class ManipuladorMetodo {

	private Method metodo;
	private Object instancia;
	private Map<String, Object> params;

	public ManipuladorMetodo(Method metodo, Object instancia, Map<String, Object> params) {
		this.metodo = metodo;
		this.instancia = instancia;
		this.params = params;
	}

	public Object invoca() {
		try {
			ArrayList<Object> parametros = new ArrayList<Object>();
			
			Stream.of(metodo.getParameters())
				.forEach(p -> parametros.add(params.get(p.getName())));
			
			return metodo.invoke(instancia, parametros.toArray());
			
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Exeção lançada pelo construtor do método", e);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

}
