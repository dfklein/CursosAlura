package br.com.alura.alurator;

import java.lang.reflect.InvocationTargetException;

public class Alurator {
	
	private String pacoteBase;

	public Alurator(String pacoteBase) {
		this.pacoteBase = pacoteBase;
		
	}
	
	public Object executa(String url) {
		try {
			String[] partesUrl = url.startsWith("/") ? url.replaceFirst("/", "").split("/") : url.split("/");
		
			
			String nomeController = partesUrl[0].concat("Controller");
			nomeController = nomeController.substring(0, 1).toUpperCase() + nomeController.substring(1);
			Class<?> classeControle = Class.forName(pacoteBase + nomeController);
			
			Object instanciaController = classeControle.getConstructor().newInstance();
			
			System.out.println(instanciaController);
			
			return null;
			
		} catch (InvocationTargetException e) {
			System.out.println("O controller não foi encontrado: " + e.getTargetException().getClass().getName());
			throw new RuntimeException(e);
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e) {
			System.out.println("Exceção lançada: " + e.getClass().getName());
			throw new RuntimeException(e);
		}
	}
}
