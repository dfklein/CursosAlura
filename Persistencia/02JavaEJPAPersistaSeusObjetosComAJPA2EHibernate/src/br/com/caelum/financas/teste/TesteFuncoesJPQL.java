package br.com.caelum.financas.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.dao.MovimentacaoDAO;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAUtil;

public class TesteFuncoesJPQL {

	public static void main(String[] args) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		
		Conta conta = new Conta();
		conta.setId(2);
		
		MovimentacaoDAO dao = new MovimentacaoDAO(em);
		List<Double> resultado = dao.getMediasPorDiaETipo(TipoMovimentacao.SAIDA, conta);
		
		System.out.println("Resultado média por dia: " + resultado);
		// Ou usando uma named query:
		TypedQuery<Double> nq = em.createNamedQuery("MediasPorDiaETipo", Double.class);
		nq.setParameter("pConta", conta);
		nq.setParameter("pTipo", TipoMovimentacao.SAIDA);
		
		List<Double> medias = nq.getResultList();
		
		em.getTransaction().commit();
		em.close();
	}
}
