package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteConta {

	public static void main(String[] args) {
		
		Conta conta = new Conta();
		conta.setTitular("Leonardo");
		conta.setBanco("Caixa Econ�mica");
		conta.setAgencia("123");
		conta.setNumero("456");
		// Este objeto criado aqui est� no estado chamado TRANSIENT, o que significa que ele n�o � um objeto relacionado a um registro no banco (nunca foi persistida).
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		// Inicia a transa��o
		em.getTransaction().begin();
		
		// A partir do momento em que voc� chama persist no objeto, ele passa a ser MANAGED, ou seja: a JPA sincroniza seu estado com o registro do mesmo no banco de dados.
		// Voc� n�o � obrigado a declarar o persist depois do begin() para salv�-lo no banco, mas com certeza precisa ser antes do commit().
		em.persist(conta); 
		
		// Se o objeto for managed (ou seja, j� foi persistido) e voc� alterar o valor de algum atributo dele, a JPA executar� um update (neste exemplo ele faz um INSERT e depois um UPDATE do mesmo objeto)
		conta.setBanco("Ita�");
		
		// Commit
		em.getTransaction().commit();
		
		// Fecha a conex�o com o banco de dados para liberar recursos utilizados.
		// A partir deste momento, todas as entidades deixam de ser MANAGED, ou seja: a JPA n�o sincroniza mais as entidades com os registros no banco.
		// Este � o estado chamado de DETACHED 
		em.close();

		// SOBRE O ESTADO DETACHED:
		// 		-> se voc� adquirir um novo EntityManager e tentar fazer persist() este objeto, vai tomar o infame "detached entity passed to persist"
		// 		-> No entanto voc� pode usar merge() em um novo entity manager que ele reconhecer� como o update de um objeto
		//			-> O merge n�o � exatamente um UPDATE: o m�todo far� um SELECT pela chave prim�ria para averiguar a exist�ncia ou n�o deste objeto e se ele realmente est� diferente do seu estado na base, fazendo UPDATE em caso afirmativo e um INSERT em caso negativo.
		EntityManager em2 = new JPAUtil().getEntityManager();
		em2.getTransaction().begin();
		conta.setAgencia("0000");
		// em2.persist(conta); -> "detached entity passed to persist"
		em2.merge(conta);
		em2.getTransaction().commit();
		em2.close();
		
		
		// Por fim, o m�todo remove s� pode ser utilizado em uma entidade que est� MANAGED.
		EntityManager em3 = new JPAUtil().getEntityManager();
		em3.getTransaction().begin();
		// em2.remove(conta); // N�o funciona, lan�a uma exce��o por estar tentando remover uma entidade detached.
		conta = em3.find(Conta.class, conta.getId());
		em3.remove(conta);
		em3.getTransaction().commit();
		em3.close();
		// 
		
//		emf.close(); // � uma pr�tica correta fechar o Factory tamb�m. No caso ele est� no JPAUtil ent�o n�o pode mais ser fechado aqui.
		
		/**
		RESUM�O SOBRE OS ESTADOS DAS ENTIDADES:
		1 - Uma entidade que nunca foi inserida no banco � TRANSIENT.
		2 - O m�todo persist() torna uma entidade TRANSIENT em uma entidade MANAGED (ou seja: sincronizada com o banco e por isso ela � inserida no mesmo)
		3 - Para obter uma entidade MANAGED n�o persistida por aquele EntityManager, mas que est� no banco, � preciso traz�-la com o find()
		4 - Quando um EntityManager � fechado, todas as entidades que ele gerenciou tornam-se DETACHED e n�o tem mais seu estado sincronizado com o banco (n�o pode mais ser persistida -> detached entity passed to persist).
		5 - O m�todo merge() tornar� um objeto que j� foi gerenciado por outro EntityManager mas foi fechado em MANAGD novamente, verificando pela chave prim�ria se aquela entidade existe ou n�o no banco (select por chave prim�ria), fazendo UPDATE em caso afirmativo e um INSERT em caso negativo.
		6 - Por fim, o estado REMOVED significa que o objeto possui representa��o em mem�ria mas j� foi apagado do banco de dados.  
		*/
		
	}
	
}
