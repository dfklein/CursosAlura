package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.model.CarrinhoCompras;

// Diz para o Spring que esta é uma classe de configuração da servlet principal.
@EnableWebMvc
// Nas configurações você pode indicar ao Spring o pacote base das classes de controller que
// você está criando, utilizando a anotação @ComponentScan e passando o pacote em String como argumento
// Por exemplo:
// ---> @ComponentScan(basePackages= {"br.com.casadocodigo.loja.controllers"})
// Porém você pode fazer isto indicando as classes diretamente (você indica uma classe e ele varre
// todo o pacote que ela estiver, e não apenas a própria classe), o que é melhor pois você pode pegar
// alterações de pacotes você pode detectar os problemas em tempo de compilação:
@ComponentScan(basePackageClasses={HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class})
// A classe WebMvcConfigurerAdapter não é bem explicada no curso. Você a extendeu para poder
// sobrescrever o método configureDefaultServletHandling. Isto faz com que o Spring conceda permissão
// ao Tomcat para acessar a pasta resources, o que foi feito para que a aplicação tivesse acesso aos
// arquivos de CSS.
public class AppWebCongiruation extends WebMvcConfigurerAdapter {

	// A anotação @Bean é um tipo de injeção de controle. Ela indica ao Spring que aquele método
	// vai gerar um retorno que ele deve gerenciar (neste caso, gerencia para configurar o retorno
	// de views da aplicação).
	// Na verdade o que você faz aqui é dizer ao Spring que toda vez que ele tiver
	// a injeção de um recurso (@Autowired em qualquer outra classe) com um tipo de retorno igual 
	// ao de qualquer método desta classe aqui anotado com @Bean que a injeção deve chamar este
	// método para construir o objeto, permitindo que ele seja configurado.
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		// Esta configuração torna todos os beans da aplicação atributos expostos para a JSP.
		// ---> resolver.setExposeContextBeansAsAttributes(true);
		// No entanto isto deixaria muito código exposto. Você pode incluir apenas classes específicas.
		// Abaixo segue o que realmente foi utilizado (feito para acessar o carrinho de compras direto da
		// jsp e visualizar a quantidade do carrinho de compras.
		resolver.setExposedContextBeanNames("carrinhoCompras");
		
		return resolver;
		
	}
	
	// Bean de configuração do Bundle de mensagens.
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource msgSource = new ReloadableResourceBundleMessageSource();
		
		msgSource.setBasename("/WEB-INF/messages");
		msgSource.setDefaultEncoding("UTF-8");
		// A configuração abaixo seta de quanto em quanto tempo ele verifica o cacheamento do seu arquivo
		// de mensagens, evitando que você tenha que subir o servidor sempre que altera algo. O argumento
		// indica de quanto em quantos segundos ele recarrega o arquivo.
		msgSource.setCacheSeconds(1);
		
		return msgSource;
	}
	
	// Este Bean é o que configura globalmente conversões de dados. Neste caso aqui foi utilizado
	// Para a conversão de formato de data.
	// Atenção: neste caso aqui o SpringMVC exige que o nome do método seja esse utilizado aqui
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService dfcService = new DefaultFormattingConversionService();

		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(dfcService);

		return dfcService;
		
	}
	
	// Configuração do MultipartpartResolver -> para receber arquivos em requisições POST.
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	// **** EXPLICAÇÃO DO MÉTODO ABAIXO COPIADA DE EXPLICAÇÃO QUE CONSTA NA DECLARAÇÃO DA CLASSE
	// A classe WebMvcConfigurerAdapter não é bem explicada no curso. Você a extendeu para poder
	// sobrescrever o método configureDefaultServletHandling. Isto faz com que o Spring conceda permissão
	// ao Tomcat para acessar a pasta resources, o que foi feito para que a aplicação tivesse acesso aos
	// arquivos de CSS.
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	    configurer.enable();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
