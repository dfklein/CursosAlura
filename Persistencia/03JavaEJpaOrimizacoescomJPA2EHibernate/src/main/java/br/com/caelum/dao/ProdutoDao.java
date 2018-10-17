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

import br.com.caelum.model.Categoria;
import br.com.caelum.model.Loja;
import br.com.caelum.model.Produto;

@Repository
public class ProdutoDao {

	@PersistenceContext
	private EntityManager em;

	public List<Produto> getProdutos() {
		
		// A linha abaixo só vai funcionar neste projeto se você estiver usando o padrão OpenEntityManagerInView porque ele 
		// inicializa os relacionamentos Lazy quando o JSP tenta iterar pela lista (neste caso o EntityManager continua aberto)
		// Veja no Configurador.java a sobrescrita do método addInterceptors
		return em.createQuery("p from Produto ", Produto.class).getResultList();
		
		// O código abaixo funciona para carregar a página inicial, mas dará o LazyInitializationException se você clicar em "editar".
		// Ele funciona TAMBÉM com o OpenEntityManagerInView
		// return em.createQuery("select distinct p from Produto p inner join fetch p.categorias c ", Produto.class).getResultList();
		
		// Uma outra alternativa MUITO legal para fazer o equivalente ao distinct é o uso de NamedEntityGraphs. É um tipo de named query para inicializar atributos Lazy.
		// Abaixo uma implementação. Para ver a implementação completa olhe o método chamado abaixo e as anotações que foram feitas na entidade Produto. 
		// O parâmetro String passado é o nome do Graph que você chama para inicializar a lista de produtos.
		// Também daria pau se clicasse em Editar (depende do OpenEntityManagerInView)
		// return getProdutoEntityGraphs("produtoComCategoria");
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

	public List<Produto> getProdutoEntityGraphs(String nomeGraph) {
		List<Produto> p = em.createQuery("select distinct p from Produto p ", Produto.class)
                .setHint("javax.persistence.loadgraph", em.getEntityGraph(nomeGraph))
                .getResultList();
		return p;
		
	}

}
