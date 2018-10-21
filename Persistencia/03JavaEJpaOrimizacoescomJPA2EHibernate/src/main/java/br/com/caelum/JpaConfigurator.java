package br.com.caelum;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class JpaConfigurator {

	@Bean
	public DataSource getDataSource() throws PropertyVetoException {
		
		// Esta � uma implementa��o de pool de conex�es
		ComboPooledDataSource ds = new ComboPooledDataSource();
		
		ds.setDriverClass("com.mysql.jdbc.Driver");
		ds.setUser("root");
		ds.setPassword("");
		ds.setJdbcUrl("jdbc:mysql://localhost/projeto_jpa");
		
		// Voc� configura aqui o menor n�mero de conex�es j� instanciadas que voc� vai ter. Ele ir� instanciar novas caso necess�rio
		ds.setMinPoolSize(3);
		
		// Por padr�o o m�ximo � 15
		ds.setMaxPoolSize(5);
		
		// Aqui voc� configura threads ass�ncronas para o banco usar em tarefas mais trabalhosas. O padr�o � 3.
		ds.setNumHelperThreads(5);
		
		// Aqui voc� configura para que a cada 1 segundo a JPA teste conex�es ociosas.
		// QUAL A IMPORT�NCIA DISSO?
		// Imagine que a conex�o do banco caiu e depois voltou. Se a JPA n�o revisar as conex�es abertas que est�o no pool, ele tentar�-usar uma que ele criou anteriormente mas que n�o existe mais (pq o banco foi reiniciado)
		// Com isso aqui voc� faz ele testar as conex�es do pool e se desfazer delas caso o banco n�o as tenha mais (e cria novas para substitu�-las)
		ds.setIdleConnectionTestPeriod(1);
		
		// Veja na classe TestaPool.java alguns m�todos importantes para o gerenciamento do pool
		
		return ds;
		// Este era o data source original da aplica��o. Ele gera uma conex�o com 
		// o banco a cada chamada. Foi comentado para a implementa��o de pool de conex�es.
//	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//	    dataSource.setUrl("jdbc:mysql://localhost/projeto_jpa");
//	    dataSource.setUsername("root");
//	    dataSource.setPassword("");
//
//	    return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setPackagesToScan("br.com.caelum");
		entityManagerFactory.setDataSource(dataSource);

		entityManagerFactory
				.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties props = new Properties();

		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		
		props.setProperty("hibernate.cache.use_second_level_cache", "true");
		props.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
		props.setProperty("hibernate.cache.use_query_cache", "true");
		
		props.setProperty("hibernate.generate_statistics", "true");

		entityManagerFactory.setJpaProperties(props);
		return entityManagerFactory;
	}
	
	@Bean
	public Statistics statistics(EntityManagerFactory emf) {
		SessionFactory factory = emf.unwrap(SessionFactory.class);
		return factory.getStatistics();
	}
	

	@Bean
	public JpaTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

}
