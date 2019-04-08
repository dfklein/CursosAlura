package br.com.caelum.financas.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteTodasAsMovimentacoesDasContas {

	public static void main(String[] args) {
		EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();
        
        // O intuito desta aula era mostrar que se você adicionar o join fetch a JPA vai colocar um join na query e não vai fazer uma query para cada movimentação.
        // O que ele não fala é que ele vai trazer registros duplicados nesse caso. Ou seja: o resultado não é inicializado como lista.
        // Além disso o join é implicitamente INNER, portanto não trará registros que não possuam movimentações
        // Na aula seguinte ele explica que isto se chama RESULTADO CARDINAL e que a melhor maneira de "resolver" isto é usando o distinct.
        String jpql = "select distinct c from Conta c left join fetch c.movimentacoes ";
        Query q = em.createQuery(jpql);
        
        List<Conta> todasContas = q.getResultList();
        System.out.println(todasContas.size());
        todasContas.forEach(c -> {
        	System.out.println("*****************");
//        	if(!c.getMovimentacoes().isEmpty()) {
	        	System.out.println("Titular: " + c.getTitular());
	        	System.out.println("Movimentacoes: ");
	        	c.getMovimentacoes().forEach(m -> {
	        		System.out.println(m.getDescricao());
	        	});
	        	System.out.println("*****************");
	        	System.out.println("");
//        	}
        });
        em.getTransaction().commit();    
        em.close(); 
	}
}
