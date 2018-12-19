package br.com.casadocodigo.loja.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;

// Diz para o Spring que esta é uma classe de configuração da servlet principal.
@EnableWebMvc
// Nas configurações você pode indicar ao Spring o pacote base das classes de controller que
// você está criando, utilizando a anotação @ComponentScan e passando o pacote em String como argumento
// Por exemplo:
// ---> @ComponentScan(basePackages= {"br.com.casadocodigo.loja.controllers"})
// Porém você pode fazer isto indicando as classes diretamente, o que é melhor pois você pode pegar
// alterações de pacotes você pode detectar os problemas em tempo de compilação:
@ComponentScan(basePackageClasses={HomeController.class})
public class AppWebCongiruation {

	// A anotação @Bean é um tipo de injeção de controle. Ela indica ao Spring que aquele método
	// vai gerar um retorno que ele deve gerenciar (neste caso, gerencia para configurar o retorno
	// de views da aplicação).
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		return resolver;
		
	}
	
}
