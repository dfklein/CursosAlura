package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteBuscaConta {

	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		// O find devolve um objeto no estado MANAGED. Isto significa que a JPA trabalha sincronizando os dados no banco com a aplica��o.
		// Caso seja aplicada alguma altera��o a este objeto, ele ser� atualizado automaticamente no banco.
		Conta conta = em.find(Conta.class, 1);
		System.out.println(conta.getTitular());
		
		// Executa um update no banco por estar em um estado managed.
		conta.setTitular("Jo�o");
		
		// Se voc� setasse para um valor que j� � o atual do objeto, ele n�o faria o update.
		
		em.getTransaction().commit();
		
		em.close();
	}
	
}
