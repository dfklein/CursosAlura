package br.com.casadocodigo.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Repository
@Transactional
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager manager;
	
	public void gravar(Produto produto) {
		manager.persist(produto);
	}

	// Aqui você não fez o inner join fetch para trazer os preços. Você fez uma configuração para fazer com
	// que a transação durasse todo o tempo da requisição, dentro do filtro. Por isso, ao abrir a página que evoca o preço ele
	// consegue inicializar a coleção de preços.
	// Veja seu arquivo de configurações de servlets (ServletSpringMVC.class) no método getServletFilters
	// OBS: Isso não é necessariamente uma vantagem. Pode gerar uma quantidade alta de queries (para a performance eu pessoalmente acho isso ruim) porque ele faz uma query por cada ítem da coleção. Para isso é melhor usar o JOIN FETCH mesmo.
	public List<Produto> listar() {
		return manager.createQuery("select distinct(p) from Produto p", Produto.class)
				.getResultList();
	}

	public Produto find(Integer id) {
        return manager.createQuery("select distinct(p) from Produto p join fetch p.precos precos where p.id = :id", Produto.class).setParameter("id", id).getSingleResult();
	}
	
	public BigDecimal somaPrecosPorTipoPreco(TipoPreco tipoPreco) {
		
		TypedQuery<BigDecimal> query = manager.createQuery(" SELECT SUM(preco.valor) FROM Produto p JOIN p.precos preco WHERE preco.tipo = :tipoPreco", BigDecimal.class);
		query.setParameter("tipoPreco", tipoPreco);
		
		return query.getSingleResult();
	}
}