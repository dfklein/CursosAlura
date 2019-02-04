package br.com.alura.alurator.reflexao;

import java.lang.reflect.Constructor;

public class ManipuladorClasse {

	private Class<?> classe;

	public ManipuladorClasse(Class<?> objetoReflexao) {
		this.classe = objetoReflexao;
		// TODO Auto-generated constructor stub
	}

	public ManipuladorConstrutor getConstrutorPadrao() {
		try {
			Constructor<?> construtorPadrao = classe.getDeclaredConstructor();
			return new ManipuladorConstrutor(construtorPadrao);
			
			
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public ManipuladorObjeto criaInstancia() {
		Object instancia = this.getConstrutorPadrao().invoca();
		return new ManipuladorObjeto(instancia);
		
	}

}
