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
		
		// O EntityManager possui um cache de 1o nível (ou seja: um cache que funciona dentro do escopo da vida do Entity Manager)
		// Se um mesmo entity manager faz uma busca pelo mesmo produto duas vezes sem ser fechado, 
		// ele faz apenas um select. No segundo ele já possui aquela entidade no cache 
		// O cache padrão do entity manager é conhecido como cache de primeiro nível
		Produto produto = em.find(Produto.class, 1);
		System.out.println(produto.getNome());
		// Não faz o segundo select pois o produto de ID 1 já está em cache
		Produto produto2 = em.find(Produto.class, 1);
		System.out.println(produto.getNome());
		// Porém, na realidade de uma aplicação você terá vários entity managers trabalhando no pool de conexões.
		// Se a aplicação pegar outro entity manager para fazer a mesma busca, uma nova consulta será feita.
		Produto produto3 = em2.find(Produto.class, 1);
		System.out.println(produto2.getNome());
		//Para tal você tem que habilitar o cache de 2o nível da JPA.
		// O primeiro passo é adicionar a propriedade "hibernate.cache.use_second_level_cache=true" nas propriedade da JPA (persistence.xml ou equivalente).
		// Você também precisa indicar qual implementação de cache de segundo nível que será utilizada na sua aplicação e também declarar no persistence.xml ou equivalente 
		// Ver configurações no JpaConfigurator.java (que é o equivalente do persistence.xml aqui).
		// Por fim, você deve anotar as entidades que deverão usar cache de segundo nível, com a estratégia que será utilizada.
		// Ver exemplo em Produto.java. (Veja inclusive os relacionamentos)
		
		// O EHCache (implementação utilizada aqui) não armazena resultados de query por padrão (como no caso de uma busca repetida na tela inicial)
		// Para fazê-lo você precisa fazer o setHint na query.
		// Você habilita esta propriedade no persistence.xml ou equivalente ("hibernate.cache.use_query_cache", "true")
		// Veja implementação no ProdutoDao.java, no método getProdutos(String nome, Integer categoriaId, Integer lojaId)
		
		// Além disso o EHCache permite uma série de configurações que podem ser feitas pelo arquivo ehcache.xml 
		// ver implementação do arquivo (está comentada mas foi testada)
		
		// ****** SOBRE AS ESTRATÉGIAS (usage) DA ANOTAÇÃO @Cache ****** //
		//		
		//		A estratégia READ_ONLY deve ser utilizada quando uma entidade não deve ser modificada.
		//
		//		A estratégia READ_WRITE deve ser utilizada quando uma entidade pode ser modificada e há grandes chances que modificações em seu estado ocorram simultaneamente. Essa estratégia é a que mais consome recursos.
		//
		//		A estratégia NONSTRICT_READ_WRITE deve ser utilizada quando uma entidade pode ser modificada, mas é incomum que as alterações ocorram ao mesmo tempo. Ela consome menos recursos que a estratégia READ_WRITE e é ideal quando não há problemas de dados inconsistentes serem lidos quando ocorrem alterações simultâneas.
		//
		//		A estratégia TRANSACTIONAL deve ser utilizada em ambientes JTA, como por exemplo em servidores de aplicação. Como utilizamos Tomcat com Spring (sem JTA) essa opção não funcionará.
		
		
		
		em.close();
		
	}

		

}
