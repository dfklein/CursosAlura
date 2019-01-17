package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer{

	// Neste método você possui configurações que são executadas já quando o container estiver subindo,
	// e não apenas ao acessar a aplicação (como acontece no getServletConfigClasses()).
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// Além de declarar o SecurityConfiguration (implementação do Spring Security) nas classes 
		// que sobem junto com o Spring, foi necessário passar a JPAConfiguration do getServletConfigClasses()
		// para cá. Isto porque o security possui um UsuarioDAO injetado e se você não carregar as configurações
		// de JPA antes você vai ter um erro de NoSuchBeanDefinition ao subir a aplicação (o Spring ainda não
		// possui conhecimento das configurações da JPA para fazer a injeção).
		// No curso ele acabou passando o AppWebConfiguration para cá também como precaução, mas não sei se 
		// isso é mesmo necessário
		return new Class[] {SecurityConfiguration.class, JPAConfiguration.class, AppWebConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        return new Filter[] {encodingFilter};
    }
	
	//veja tbm https://cursos.alura.com.br/forum/topico-atualizacao-resources-nao-sao-carregados-na-aula-10-58813
	@Override
	protected void customizeRegistration(Dynamic registration) {
			registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	// Este é o método em que você indicou ao Spring qual perfil de conexão com banco de dados
	// deveria ser utilizado para a aplicação web (separando do banco de dados de testes).
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
	    super.onStartup(servletContext);
	    // Adiciona um listener de contextos
	    servletContext.addListener(new RequestContextListener());
	    // Aqui você especifica que o profile ativo seja o de Dev.
	    servletContext.setInitParameter("spring.profiles.active", "dev");
	}
	
}