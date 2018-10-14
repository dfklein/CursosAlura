package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAUtil;

public class TesteJPARelacionamento {

	public static void main(String[] args) {
		
		Conta ct = new Conta();
		ct.setAgencia("0102");
		ct.setBanco("Itaú");
		ct.setNumero("123");
		ct.setTitular("Leonardo");
		
		Movimentacao mv = new Movimentacao();
		mv.setData(Calendar.getInstance());
		mv.setDescricao("Churrascaria");
		mv.setTipo(TipoMovimentacao.SAIDA);
		mv.setValor(new BigDecimal("200.0"));
		mv.setConta(ct);
		
		
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(ct); // Não se esqueça de persistir a conta antes.
		em.persist(mv); 
		
		em.getTransaction().commit();
		
		em.close();
	}
}
