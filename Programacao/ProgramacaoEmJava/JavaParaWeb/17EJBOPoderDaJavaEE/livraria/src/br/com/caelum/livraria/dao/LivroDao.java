package br.com.caelum.livraria.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Livro;


@Stateless
public class LivroDao {
	
	@PersistenceContext(name="livraria")
	private EntityManager manager;

//	private Banco banco = new Banco();
	
	public void salva(Livro livro) {
		manager.persist(livro);
//		banco.save(livro);
	}
	
	public List<Livro> todosLivros() {
		TypedQuery<Livro> query = manager.createQuery("SELECT l FROM Livro l", Livro.class);
		return query.getResultList();
//		return banco.listaLivros();
	}

	public List<Livro> livrosPeloNome(String nome) {
		TypedQuery<Livro> q = this.manager.createQuery("select l from Livro l where l.titulo like :pTitulo", Livro.class);
		q.setParameter("pTitulo", "%" + nome + "%");
		return q.getResultList();
		
		
	}
	
}
