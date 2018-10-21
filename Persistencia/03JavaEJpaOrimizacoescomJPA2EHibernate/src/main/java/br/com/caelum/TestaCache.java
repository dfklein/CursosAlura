package br.com.caelum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.com.caelum.model.Produto;

public class TestaCache {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(JpaConfigurator.class);
		EntityManagerFactory emf = ctx.getBean(EntityManagerFactory.class);
		
		EntityManager em = emf.createEntityManager();
		EntityManager em2 = emf.createEntityManager();
		
		// O EntityManager possui um cache de 1o n�vel (ou seja: um cache que funciona dentro do escopo da vida do Entity Manager)
		// Se um mesmo entity manager faz uma busca pelo mesmo produto duas vezes sem ser fechado, 
		// ele faz apenas um select. No segundo ele j� possui aquela entidade no cache 
		// O cache padr�o do entity manager � conhecido como cache de primeiro n�vel
		Produto produto = em.find(Produto.class, 1);
		System.out.println(produto.getNome());
		// N�o faz o segundo select pois o produto de ID 1 j� est� em cache
		Produto produto2 = em.find(Produto.class, 1);
		System.out.println(produto.getNome());
		// Por�m, na realidade de uma aplica��o voc� ter� v�rios entity managers trabalhando no pool de conex�es.
		// Se a aplica��o pegar outro entity manager para fazer a mesma busca, uma nova consulta ser� feita.
		Produto produto3 = em2.find(Produto.class, 1);
		System.out.println(produto2.getNome());
		//Para tal voc� tem que habilitar o cache de 2o n�vel da JPA.
		// O primeiro passo � adicionar a propriedade "hibernate.cache.use_second_level_cache=true" nas propriedade da JPA (persistence.xml ou equivalente).
		// Voc� tamb�m precisa indicar qual implementa��o de cache de segundo n�vel que ser� utilizada na sua aplica��o e tamb�m declarar no persistence.xml ou equivalente 
		// Ver configura��es no JpaConfigurator.java (que � o equivalente do persistence.xml aqui).
		// Por fim, voc� deve anotar as entidades que dever�o usar cache de segundo n�vel, com a estrat�gia que ser� utilizada.
		// Ver exemplo em Produto.java. (Veja inclusive os relacionamentos)
		
		// O EHCache (implementa��o utilizada aqui) n�o armazena resultados de query por padr�o (como no caso de uma busca repetida na tela inicial)
		// Para faz�-lo voc� precisa fazer o setHint na query.
		// Voc� habilita esta propriedade no persistence.xml ou equivalente ("hibernate.cache.use_query_cache", "true")
		// Veja implementa��o no ProdutoDao.java, no m�todo getProdutos(String nome, Integer categoriaId, Integer lojaId)
		
		// Al�m disso o EHCache permite uma s�rie de configura��es que podem ser feitas pelo arquivo ehcache.xml 
		// ver implementa��o do arquivo (est� comentada mas foi testada)
		
		// ****** SOBRE AS ESTRAT�GIAS (usage) DA ANOTA��O @Cache ****** //
		//		
		//		A estrat�gia READ_ONLY deve ser utilizada quando uma entidade n�o deve ser modificada.
		//
		//		A estrat�gia READ_WRITE deve ser utilizada quando uma entidade pode ser modificada e h� grandes chances que modifica��es em seu estado ocorram simultaneamente. Essa estrat�gia � a que mais consome recursos.
		//
		//		A estrat�gia NONSTRICT_READ_WRITE deve ser utilizada quando uma entidade pode ser modificada, mas � incomum que as altera��es ocorram ao mesmo tempo. Ela consome menos recursos que a estrat�gia READ_WRITE e � ideal quando n�o h� problemas de dados inconsistentes serem lidos quando ocorrem altera��es simult�neas.
		//
		//		A estrat�gia TRANSACTIONAL deve ser utilizada em ambientes JTA, como por exemplo em servidores de aplica��o. Como utilizamos Tomcat com Spring (sem JTA) essa op��o n�o funcionar�.
		
		
		
		em.close();
		
	}

		

}
