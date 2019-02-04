package br.com.alura.alurator.reflexao;

public class Reflexao {
	
	public ManipuladorClasse refleteClasse(String pacoteBase, String fqn) {
		try {
			Class<?> objetoReflexao = Class.forName(pacoteBase + fqn);
			return new ManipuladorClasse(objetoReflexao);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public Object invoca() {
//		return objetoReflexao;
		return null;
	}
	
	

}
