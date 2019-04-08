package br.com.alura.alurator.reflexao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ManipuladorConstrutor {

	private Constructor<?> construtorPadrao;

	public ManipuladorConstrutor(Constructor<?> construtorPadrao) {
		this.construtorPadrao = construtorPadrao;
		
	}

	public Object invoca() {
		try {
			return this.construtorPadrao.newInstance();
			
		} catch (InvocationTargetException e) {
			System.out.println("O controller lançou uma exceção ao ser instanciado: " + e.getTargetException().getClass().getName());
			throw new RuntimeException(e);
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
			throw new RuntimeException(e);
			
		}
	}

}
