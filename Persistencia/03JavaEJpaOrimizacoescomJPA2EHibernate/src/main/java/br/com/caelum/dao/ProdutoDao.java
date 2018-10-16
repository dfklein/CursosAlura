package br.com.caelum.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.caelum.model.Loja;
import br.com.caelum.model.Produto;

@Repository
public class ProdutoDao {

	@PersistenceContext
	private EntityManager em;

	public List<Produto> getProdutos() {
		return em.createQuery("from Produto", Produto.class).getResultList();
	}

	public Produto getProduto(Integer id) {
		Produto produto = em.find(Produto.class, id);
		return produto;
	}

	/**
	 * Nesta versão ele usa o Criteria da JPA2, que é completamente diferente da Criteria do Hibernate.
	 * Abaixo deste método tem uma versão igual utilizando o Criteria do Hibernate. 
	 */
	public List<Produto> getProdutos(String nome, Integer categoriaId, Integer lojaId) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = query.from(Produto.class);

		Path<String> nomePath = root.<String> get("nome");
		Path<Integer> lojaPath = root.<Loja> get("loja").<Integer> get("id");
		Path<Integer> categoriaPath = root.join("categorias").<Integer> get("id");

		List<Predicate> predicates = new ArrayList<>();

		if (!nome.isEmpty()) {
//			Predicate nomeIgual = criteriaBuilder.like(nomePath, nome);
			Predicate nomeIgual = criteriaBuilder.like(nomePath, "%" + nome + "%");
			predicates.add(nomeIgual);
		}
		if (categoriaId != null) {
			Predicate categoriaIgual = criteriaBuilder.equal(categoriaPath, categoriaId);
			predicates.add(categoriaIgual);
		}
		if (lojaId != null) {
			Predicate lojaIgual = criteriaBuilder.equal(lojaPath, lojaId);
			predicates.add(lojaIgual);
		}

		
		// o método where aceita var args como argumento. 
		// Aqui não funciona por causa dos IFs que inicializam essas variáveis.
		// Se não tivessem os IFs, ele estaria procurando "xxx IS NULL"
		// query.where(nomeIgual, categoriaIgual, lojaIgual); 
		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Produto> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();

	}
	
	@Transactional // esta anotação do Spring diz que dentro de todo esse método deverá ter uma transação ativa e portanto um EntityManager
	public List<Produto> getProdutosHibernate(String nome, Integer categoriaId, Integer lojaId) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Produto.class);
		
		if (!nome.isEmpty()) {
		    criteria.add(Restrictions.like("nome", "%" + nome + "%"));
		}

		if (lojaId != null) {
		    criteria.add(Restrictions.like("loja.id", lojaId));
		}
		
		if (categoriaId != null) {
		    criteria.setFetchMode("categorias", FetchMode.JOIN)
		            .createAlias("categorias", "c")
		            .add(Restrictions.like("c.id", categoriaId));
		}
		
		return (List<Produto>) criteria.list();
	}

	public void insere(Produto produto) {
		if (produto.getId() == null)
			em.persist(produto);
		else
			em.merge(produto);
	}

}
