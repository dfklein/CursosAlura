package br.com.casadocodigo.loja.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
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
	
	// Observe em contentNegotiationViewResolver() que você precisou utilizar este método para o seu
	// negociador de retorno.
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
    
    // Este é o Bean que foi criado para o método detalhe() de ProdutosController com o objetivo
    // de fazer com que o Spring resolva, a partir de um mesmo método, se o retorno dele será uma
    // view (JSP) ou um JSON.
    // A partir da implementação abaixo, ao adicionar ".json" ao final da sua URL, o sistema retornará
    // uma representação JSON dos objetos que foram atachados ao seu ModelAndView.
    // Por exemplo:
    //	---> http://localhost:8080/casadocodigo/produtos/detalhe/1 -> retorna a página de detalhes do produto de id 1
    //	---> http://localhost:8080/casadocodigo/produtos/detalhe/1.json -> retorna JSON do produto de id 1
    // Uma forma de obter o JSON sem colocar o ".json" no final da URL é seguindo os seguites passos:
    // 1 - Usar o método GET
    // 2 - Adicionar ao header da requisição a propriedade "Accept" com o valor "application/json"
    @Bean
    public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager) {
    	List<ViewResolver> viewResolvers = new ArrayList<>();
    	viewResolvers.add(internalResourceViewResolver());
    	viewResolvers.add(new JsonViewResolver());
    	

    	ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(viewResolvers);
		resolver.setContentNegotiationManager(manager);
    	
    	return resolver;
    }
    
    /**
     * Interceptor utilizado para que alterações no Locale da requisição possam ser lidos em caso
     * de alteração. 
     * 
     * O método localeResolver() também faz parte desta implementação
     * 
     * Veja cabecalho.jsp (as opções de alteração de idioma da aplicação) 
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new LocaleChangeInterceptor());
    }
    
    /**
     * Este Bean garante que as informações de Locale sejam guardados pelo navegador cliente
     * dentro de um cookie. 
     */
    @Bean
    public LocaleResolver localeResolver() {
    	return new CookieLocaleResolver();
    	// Na versão comentada abaixo, você guarda as informações de Locale na sessão ao invés de um cookie.
    	// ----> return new SessionLocaleResolver();
    }
    
    // Bean para a injeção do MailSender.
    // Não se esqueça que o método de criação deste Bean PRECISA ser mailSender()
    @Bean
    public MailSender mailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername("alura.springmvc@gmail.com");
        mailSender.setPassword("alura2015");
        mailSender.setPort(587);

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smpt.starttls.enable", true);

        mailSender.setJavaMailProperties(mailProperties);
        return mailSender;
    }
}
	