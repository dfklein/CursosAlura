package br.com.caelum.livraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.livraria.modelo.Autor;

// Cada instância de um bean EJB só pode ser acessada por uma thread por vez.
// Se outra thread chama este bean e ele tem uma instância ocupada, o container vai necessariamente direcionar o uso para uma outra instância do bean (criando a, caso ainda não exista no pool)
// Ou seja: ele é absolutamente thread safe sem que você precise fazer qualquer configuração ou lógica durante o desenvolvimento.
// *********************************************************************
// Você não está usando aqui, mas no curso ele explica que o @Stateful é um EJB que possui um estado específico para cada cliente que o acessa.
// Pense que o ciclo de vida dele é como o de um HttpSession (ou seja: o navegador acessa e possui aquela instância de EJB até ele se desligar do EJB)
// Ao contrário do @Stateful ele não está em um pool com várias instâncias já prontas aguardando por uso. O que quer dizer uma perda de performance.
// Justamente por você ter um estado com o cliente que é armazenado pelo HttpSession é que ele é pouco interessante em uma aplicação web (performance pior para algo que já existe)
// *********************************************************************
// O bean do tipo stateless é aquele que não possui estado e é criado pelo container sob demanda.
// O container por baixo dos panos pode criar algumas instâncias dele e deixar em um pool, utilizando estas instâncias conforme este tipo de bean é requerido.
// Você configura este pool de conexões nas configurações do container, e não na aplicação (ex: Wildfly -> standalone.xml -> tag  <subsystem xmlns="urn:jboss:domain:ejb3:X.X">)
@Stateless
public class AutorDao {

	// Esta é a anotação utilizada para indicar inverção de controle de instanciação do Entity Manager para o application server (JTA).
	// Se você estivesse utilizando JPA sem um servidor de aplicação (num servlet container ou outro tipo de aplicação com JPA) a anotação seria o @PersistenceUnit e você controlaria toda a abertura e fechamento de transação "no braço".
	@PersistenceContext(name="livraria")
	private EntityManager manager;
	
	@Inject
	private Banco banco;
	
	// O @PostConstruct é uma anotação de controle de ciclo de vida do EJB que chamará um método após 
	// o objeto da presente classe ser instanciado pelo EJB container.
	
	// métodos ligados ao ciclo de vida de um objeto (como neste caso: um método que é chamado ao final da execução do construtor) são também conhecidos como callback.
	@PostConstruct
	void aposCriacao() {
		System.out.println("Teste de post construct na classe AutorDao.");
	}

	public void salva(Autor autor) {
		manager.persist(autor);
//		banco.save(autor);
	}
	
	public List<Autor> todosAutores() {
		return manager.createQuery("SELECT a FROM Autor a ", Autor.class).getResultList();
//		return banco.listaAutores();
	}

	public Autor buscaPelaId(Integer autorId) {
		Autor autor = this.manager.find(Autor.class, autorId);
//		Autor autor = this.banco.buscaPelaId(autorId);
		return autor;
	}
	
}
