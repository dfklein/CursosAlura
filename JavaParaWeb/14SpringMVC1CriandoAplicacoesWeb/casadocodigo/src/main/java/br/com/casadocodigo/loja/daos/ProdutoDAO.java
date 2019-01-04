package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.model.Produto;

// @Component é uma anotação que diz ao Spring que ela deve ser gerenciada por ele,
// permitindo que em uma outra ponta ela seja injetada com o @Autowired.
// No entanto, para os DAOs, é utilizada a @Repository. Ela deixa o Spring ciente de que esta é
// uma classe de acesso a dados que herda @Component (em outras palavras: também torna esta classe
// gerenciável pelo Spring utilizando @Autowired onde ela precisar ser instanciada).
// OBS: não se esqueça de incluir o pacote deste DAO no @ComponentScan do seu arquivo de configurações
// (neste projeto em AppWebConfiguration.class)
@Repository
// O @Transactional é o que fará com que o Spring realmente controle as transações nesta classe.
// Observe que isto só funciona se você tiver sua classe de configurações de JPA (JPAConfiguration.class)
// anotada com @EnableTransactionManagement;
// OBS: Tome cuidado para importar o @Transactional correto (o javax.persistence também tem esta anotação)
@Transactional
public class ProdutoDAO {

	// Para a injeção do persistence funcionar no Spring, você precisa fazer uma classe de configuração
	// que vai criar o EntityManagerFactory para ele (diferente do JavaEE, ele não vai ter a implementação
	// do servidor de aplicação). Além disso, o Spring não trabalha com um arquivo de 
	// configuração persistence.xml, sendo necessário configurar o acesso ao banco também em uma classe
	// gerenciada pelo Spring. Veja a classe JPAConfigurationAdapter.class
	// OBS: Não esqueça de incluir JPAConfigurationAdapter.class nas configurações de servlet (ServletSpringMVC.class)
	@PersistenceContext
	private EntityManager manager;
	
	public void gravar(Produto produto) {
		manager.persist(produto);
		
	}

	public List<Produto> listar() {
		return manager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
	}

	public Produto find(Integer id) {
		// return manager.find(Produto.class, id); // assim vai dar pau de Lazy
		return manager.createQuery("SELECT DISTINCT p FROM Produto p INNER JOIN FETCH p.precos precos WHERE p.id = :id", Produto.class)
				.setParameter("id", id)
				.getSingleResult();
	}
}
