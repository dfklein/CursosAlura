package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// Você precisa dizer ao Spring que esta classe permite a ele controle transacional. Sem esta anotação
// voce sobe o projeto mas não consegue persistir nada. Além disso, para tudo funcionar, você deverá
// anotar seu DAO com @Transactional para que o Spring saiba que lá ele deve efetivamente abrir e
// fechar transações (esta anotação apenas habilita esta classe de configurações para transações, sem
// de fato abrir ou fechar uma). Você também precisa associar o controle de transação ao entity manager,
// o que é feito aqui no método transactionManager() desta classe
@EnableTransactionManagement
// Não esqueça de incluir esta classe nas configurações de servlet (ServletSpringMVC.class)
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		
		// Implementação do adaptador para o Spring
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		
		// Configurações de conexão
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setUsername("root");
		datasource.setPassword("");
		datasource.setUrl("jdbc:mysql://localhost:3306/livros");
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		
		// Configurações do Hibernate
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setDataSource(datasource);
		factoryBean.setJpaProperties(props);
		// Aqui você define em que pacote estão as suas classes com entidades
		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.model");
		
		return factoryBean;
		
	}
	
	// Você precisa de um Bean que associe o transaction manager ao seu entity manager.
	// É importante que este método tenha o EntityManagerFactory como argumento. Quando o método
	// está anotado com o @Bean ele será invocado na hora certa de acordo com o seu tipo de 
	// retorno e a lista de argumentos que recebe.
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager();
		
	}
	
}
