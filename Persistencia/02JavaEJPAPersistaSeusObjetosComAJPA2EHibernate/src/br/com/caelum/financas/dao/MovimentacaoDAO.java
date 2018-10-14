package br.com.caelum.financas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.TipoMovimentacao;

public class MovimentacaoDAO {
	
	private EntityManager em;
	
	public MovimentacaoDAO(EntityManager em) {
		this.em = em;
	}

	public List<Double> getMediasPorDiaETipo(TipoMovimentacao saida, Conta conta) {
		String query = "SELECT AVG(m.valor) FROM Movimentacao m " 
				+ " WHERE m.conta = :pConta "
				+ " AND m.tipo = :pTipo " 
				+ " GROUP BY day(m.data), month(m.data), year(m.data) ";

		// O TypedQuery serve para você tipar o resultado da sua query. Lembra que você
		// saía anotando tudo com @SupressWarnings? É assim que você faz o compilador
		// aceitar o seu código sem gerar esse warning.
		TypedQuery<Double> q = em.createQuery(query, Double.class);
		q.setParameter("pConta", conta);
		q.setParameter("pTipo", TipoMovimentacao.SAIDA);
		List<Double> resultado = (List<Double>) q.getResultList();
		// TODO Auto-generated method stub
		em.close();
		return resultado;
	}

}
