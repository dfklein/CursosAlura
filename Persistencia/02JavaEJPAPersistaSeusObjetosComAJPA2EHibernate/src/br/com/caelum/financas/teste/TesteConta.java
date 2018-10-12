package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.caelum.financas.modelo.Conta;

public class TesteConta {

	public static void main(String[] args) {
		Conta conta = new Conta();
		conta.setTitular("Denis");
		conta.setBanco("Itau");
		conta.setAgencia("0786");
		conta.setNumero("460786-6");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("financas");
		EntityManager em = emf.createEntityManager();
		
		// Inicia a transação
		em.getTransaction().begin();
		
		em.persist(conta);
		
		// Commit
		em.getTransaction().commit();
		
		// Fecha a conexão com o banco de dados para liberar recursos utilizados.
		em.close();
		emf.close();
	}
	
}
