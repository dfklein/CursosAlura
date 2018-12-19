package br.com.casadocodigo.loja.conf;

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
		return new Class[] {AppWebCongiruation.class};
	}

	// Este método é o que mapeia as servlets do Spring. O array de Strings que é retornado
	// contém todas as URLs que utilizarão esta servlet como controller.
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
