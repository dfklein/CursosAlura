package br.com.caelum.financas.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAUtil;

public class TesteJPQL {

	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		Conta conta = new Conta();
		conta.setId(2);
		
		String query = "SELECT m FROM Movimentacao m "
				+ " WHERE m.conta = :pConta "
				+ " AND m.tipo = :pTipo ";
		
		Query q = em.createQuery(query);
		q.setParameter("pConta", conta);
		q.setParameter("pTipo", TipoMovimentacao.SAIDA);
		List<Movimentacao> resultado = q.getResultList();

		resultado.forEach(m -> {
			System.out.println(m.getDescricao());	
			System.out.println(m.getTipo());	
			
		});
		
		
		em.getTransaction().commit();
		em.close();
	}
}
