package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteConta {

	public static void main(String[] args) {
		
		Conta conta = new Conta();
		conta.setTitular("Leonardo");
		conta.setBanco("Caixa Econômica");
		conta.setAgencia("123");
		conta.setNumero("456");
		// Este objeto criado aqui está no estado chamado TRANSIENT, o que significa que ele não é um objeto relacionado a um registro no banco (nunca foi persistida).
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		// Inicia a transação
		em.getTransaction().begin();
		
		// A partir do momento em que você chama persist no objeto, ele passa a ser MANAGED, ou seja: a JPA sincroniza seu estado com o registro do mesmo no banco de dados.
		// Você não é obrigado a declarar o persist depois do begin() para salvá-lo no banco, mas com certeza precisa ser antes do commit().
		em.persist(conta); 
		
		// Se o objeto for managed (ou seja, já foi persistido) e você alterar o valor de algum atributo dele, a JPA executará um update (neste exemplo ele faz um INSERT e depois um UPDATE do mesmo objeto)
		conta.setBanco("Itaú");
		
		// Commit
		em.getTransaction().commit();
		
		// Fecha a conexão com o banco de dados para liberar recursos utilizados.
		// A partir deste momento, todas as entidades deixam de ser MANAGED, ou seja: a JPA não sincroniza mais as entidades com os registros no banco.
		// Este é o estado chamado de DETACHED 
		em.close();

		// SOBRE O ESTADO DETACHED:
		// 		-> se você adquirir um novo EntityManager e tentar fazer persist() este objeto, vai tomar o infame "detached entity passed to persist"
		// 		-> No entanto você pode usar merge() em um novo entity manager que ele reconhecerá como o update de um objeto
		//			-> O merge não é exatamente um UPDATE: o método fará um SELECT pela chave primária para averiguar a existência ou não deste objeto e se ele realmente está diferente do seu estado na base, fazendo UPDATE em caso afirmativo e um INSERT em caso negativo.
		EntityManager em2 = new JPAUtil().getEntityManager();
		em2.getTransaction().begin();
		conta.setAgencia("0000");
		// em2.persist(conta); -> "detached entity passed to persist"
		em2.merge(conta);
		em2.getTransaction().commit();
		em2.close();
		
		
		// Por fim, o método remove só pode ser utilizado em uma entidade que está MANAGED.
		EntityManager em3 = new JPAUtil().getEntityManager();
		em3.getTransaction().begin();
		// em2.remove(conta); // Não funciona, lança uma exceção por estar tentando remover uma entidade detached.
		conta = em3.find(Conta.class, conta.getId());
		em3.remove(conta);
		em3.getTransaction().commit();
		em3.close();
		// 
		
//		emf.close(); // É uma prática correta fechar o Factory também. No caso ele está no JPAUtil então não pode mais ser fechado aqui.
		
		/**
		RESUMÃO SOBRE OS ESTADOS DAS ENTIDADES:
		1 - Uma entidade que nunca foi inserida no banco é TRANSIENT.
		2 - O método persist() torna uma entidade TRANSIENT em uma entidade MANAGED (ou seja: sincronizada com o banco e por isso ela é inserida no mesmo)
		3 - Para obter uma entidade MANAGED não persistida por aquele EntityManager, mas que está no banco, é preciso trazê-la com o find()
		4 - Quando um EntityManager é fechado, todas as entidades que ele gerenciou tornam-se DETACHED e não tem mais seu estado sincronizado com o banco (não pode mais ser persistida -> detached entity passed to persist).
		5 - O método merge() tornará um objeto que já foi gerenciado por outro EntityManager mas foi fechado em MANAGD novamente, verificando pela chave primária se aquela entidade existe ou não no banco (select por chave primária), fazendo UPDATE em caso afirmativo e um INSERT em caso negativo.
		6 - Por fim, o estado REMOVED significa que o objeto possui representação em memória mas já foi apagado do banco de dados.  
		*/
		
	}
	
}
