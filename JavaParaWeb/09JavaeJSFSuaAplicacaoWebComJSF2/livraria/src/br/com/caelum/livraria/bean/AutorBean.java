package br.com.caelum.livraria.bean;

import javax.faces.bean.ManagedBean;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;

@ManagedBean
public class AutorBean {

	private Autor autor = new Autor();

	public Autor getAutor() {
		return autor;
	}

	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		new DAO<Autor>(Autor.class).adiciona(this.autor);
		
		autor = new Autor();
		
		// Por padr�o o JSF retorna um forward.
		// Isso quer dizer que quando voc� chama internamente um xhtml, o controller do JSF vai internamente buscar esta view e devolv�-la para o navegador
		// A consequ�ncia disso � que o navegador n�o sabe que ele est�, na verdade, vendo outra p�gina (o navegador n�o foi avisado de que ele recebeu outro arquivo de view, entende?)
		// Um jeito de contornar isto � devolvendo um redirect (como feito abaixo).
		// Quando voc� retorna um redirect, o controller n�o devolve a pr�xima view para o navegador, mas sim a view em que ele est� atualmente junto com um comando para fazer a requisi��o que ele deve fazer para ir para a view que voc� quer exibir.
		return "livro?redirect=true";
	}
	
}
