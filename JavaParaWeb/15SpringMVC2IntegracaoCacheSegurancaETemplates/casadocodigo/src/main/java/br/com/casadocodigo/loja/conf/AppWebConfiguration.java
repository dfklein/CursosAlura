package br.com.casadocodigo.loja.conf;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
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

import com.google.common.cache.CacheBuilder;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;

@EnableWebMvc
@ComponentScan(basePackageClasses= {HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class})
// Habilitando o cache. Ver o método cacheManager.
//Não se esqueça que para o cache funcionar numa requisição, o método do controller precisa ser anotado com @Cacheable
@EnableCaching
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix( ".jsp");
		
		//resolver.setExposeContextBeansAsAttributes(true);
		resolver.setExposedContextBeanNames("carrinhoCompras");
		
		return resolver;
	}

	@Bean
	public MessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		return messageSource;
	}
	
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = 
				new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);
		
		return conversionService;
	}
	
    @Bean
	public MultipartResolver multipartResolver(){
		return new StandardServletMultipartResolver();
	}
    
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    // Método de cacheamento de requisições. 
    // Não esquecer de usar a anotação @EnableCaching na declaração da classe.
    // Não se esqueça que para o cache funcionar numa requisição, o método do controller precisa ser anotado com @Cacheable
    @Bean
    public CacheManager cacheManager() {
    	// Este é um cache muito simples, do próprio Spring, que não é recomendado para produção.
    	// A própria documentação do Spring não recomenda o uso dele pelo fato dele não permitir uma série de
    	// configurações, como timeout do cache, por exemplo.
    	// ----> return new ConcurrentMapCacheManager();
    	
    	// É recomendado o uso do Guava para isso:
    	CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
    		.maximumSize(100) // qtde máxima de objetos guardados em cache 
    		.expireAfterAccess(5, TimeUnit.MINUTES) // tempo de expiração do cache
    	;
    	
    	GuavaCacheManager cm = new GuavaCacheManager();
    	cm.setCacheBuilder(builder);
    	return cm;
    }
}
	