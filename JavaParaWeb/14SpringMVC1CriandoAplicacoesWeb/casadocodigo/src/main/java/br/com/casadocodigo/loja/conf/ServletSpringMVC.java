package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// É necessário implementar esta classe abstrata para que o Spring utilize esta classe como
// controller central da aplicação. (Lembre-se do curso de Servlets e da criação de um controller
// genérico com uma única servlet que por reflection chamava outras classes (senhdo aqui aqulas que
// você anotou com @Controller), de forma a deixar apenas esta primeira entrada lidando com 
// requisições e as demais lidando com a lógica dos objetos que trafegam nela. 
public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	
	// Aqui você deve retornar todas as classes de configuração das servlets
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {AppWebCongiruation.class, JPAConfiguration.class};
	}

	// Este método é o que mapeia as servlets do Spring. O array de Strings que é retornado
	// contém todas as URLs que utilizarão esta servlet como controller.
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	// Este método registra os filtros da aplicação. Não é obrigado a sobrescrevê-lo. Você o fez aqui
	// por ser a maneira de configurar o encoding como UTF-8.
	@Override
	protected Filter[] getServletFilters() {
		// Filtro para encoding
		CharacterEncodingFilter encodingFlter = new CharacterEncodingFilter();
		encodingFlter.setEncoding("UTF-8");
		
		return new Filter[] {encodingFlter};
	}
}
