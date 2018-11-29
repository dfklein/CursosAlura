package br.com.caelum.livraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.caelum.livraria.modelo.Autor;

// Cada instância de um bean EJB só pode ser acessada por uma thread por vez.
// Se outra thread chama este bean e ele tem uma instância ocupada, o container vai necessariamente direcionar o uso para uma outra instância do bean (criando a, caso ainda não exista no pool)
// Ou seja: ele é absolutamente thread safe sem que você precise fazer qualquer configuração ou lógica durante o desenvolvimento.
// *********************************************************************
// O bean do tipo stateless é aquele que não possui estado e é criado pelo container sob demanda.
// O container por baixo dos panos pode criar algumas instâncias dele e deixar em um pool, utilizando estas instâncias conforme este tipo de bean é requerido.
// Você configura este pool de conexões nas configurações do container, e não na aplicação (ex: Wildfly -> standalone.xml -> tag  <subsystem xmlns="urn:jboss:domain:ejb3:X.X">)
@Stateless
public class AutorDao {

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
		banco.save(autor);
	}
	
	public List<Autor> todosAutores() {
		return banco.listaAutores();
	}

	public Autor buscaPelaId(Integer autorId) {
		Autor autor = this.banco.buscaPelaId(autorId);
		return autor;
	}
	
}
