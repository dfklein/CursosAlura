package br.com.caelum.financas.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.financas.modelo.Categoria;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.util.JPAUtil;

public class TesteMovimentacaoPorCategoria {

	public static void main(String[] args) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		Categoria categoria = new Categoria();
		categoria.setId(2);
		
		String query = "SELECT m FROM Movimentacao m "
				+ " JOIN m.categorias c "
				+ " WHERE c = :pCategoria ";
		
		Query q = em.createQuery(query);
		q.setParameter("pCategoria", categoria);
		List<Movimentacao> resultado = q.getResultList();
	
		resultado.forEach(m -> {
			System.out.println(m.getDescricao());	
			System.out.println(m.getTipo());	
			
		});
		
		
		em.getTransaction().commit();
		em.close();
	}
}
