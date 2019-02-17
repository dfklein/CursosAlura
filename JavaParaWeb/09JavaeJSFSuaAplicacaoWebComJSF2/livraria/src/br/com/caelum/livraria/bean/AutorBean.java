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
		
		// Por padrão o JSF retorna um forward.
		// Isso quer dizer que quando você chama internamente um xhtml, o controller do JSF vai internamente buscar esta view e devolvê-la para o navegador
		// A consequência disso é que o navegador não sabe que ele está, na verdade, vendo outra página (o navegador não foi avisado de que ele recebeu outro arquivo de view, entende?)
		// Um jeito de contornar isto é devolvendo um redirect (como feito abaixo).
		// Quando você retorna um redirect, o controller não devolve a próxima view para o navegador, mas sim a view em que ele está atualmente junto com um comando para fazer a requisição que ele deve fazer para ir para a view que você quer exibir.
		return "livro?redirect=true";
	}
	
}
