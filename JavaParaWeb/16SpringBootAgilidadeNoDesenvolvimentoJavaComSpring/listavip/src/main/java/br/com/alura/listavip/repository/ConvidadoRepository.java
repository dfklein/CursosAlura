package br.com.alura.listavip.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.alura.listavip.model.Convidado;

/**
 * O Spring tem essa coisa meio bizarra de que se você mantiver a convenção dos nomes dos métodos
 * ele vai entender pelo nome do método o que ele precisa fazer quando estiver buscando algo por
 * um atributo. Basta você declará-lo nesta interface com o nome findBy...
 * 
 * Veja na documentação: https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
 */
public interface ConvidadoRepository extends CrudRepository<Convidado, Long>{

	// Neste exemplo você buscando um objeto Convidado pelo atributo nome.
	List<Convidado> findByNome(String nome);
	
	
}
