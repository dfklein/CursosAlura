package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteBuscaConta {

	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		// O find devolve um objeto no estado MANAGED. Isto significa que a JPA trabalha sincronizando os dados no banco com a aplicação.
		// Caso seja aplicada alguma alteração a este objeto, ele será atualizado automaticamente no banco.
		Conta conta = em.find(Conta.class, 1);
		System.out.println(conta.getTitular());
		
		// Executa um update no banco por estar em um estado managed.
		conta.setTitular("João");
		
		// Se você setasse para um valor que já é o atual do objeto, ele não faria o update.
		
		em.getTransaction().commit();
		
		em.close();
	}
	
}
