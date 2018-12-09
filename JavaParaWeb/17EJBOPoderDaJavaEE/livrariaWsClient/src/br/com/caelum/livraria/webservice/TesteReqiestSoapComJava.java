package br.com.caelum.livraria.webservice;

import java.rmi.RemoteException;

public class TesteReqiestSoapComJava {

	public static void main(String[] args) throws RemoteException {
		
		LivrariaWS cliente = new LivrariaWSProxy();
		
		Livro[] livros = cliente.getLivrosPeloNome("Teste");
		
		for (Livro livro : livros) {
			System.out.println("resposta ws: ID = " + livro.getId());
			System.out.println("resposta ws: Autor = " + livro.getAutor().getNome());
			System.out.println("resposta ws: Título = " + livro.getTitulo());
		}
		
	}
}
