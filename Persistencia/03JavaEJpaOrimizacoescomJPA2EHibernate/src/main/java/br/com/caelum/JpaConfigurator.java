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
		
		// Esta é uma implementação de pool de conexões
		ComboPooledDataSource ds = new ComboPooledDataSource();
		
		ds.setDriverClass("com.mysql.jdbc.Driver");
		ds.setUser("root");
		ds.setPassword("");
		ds.setJdbcUrl("jdbc:mysql://localhost/projeto_jpa");
		
		// Você configura aqui o menor número de conexões já instanciadas que você vai ter. Ele irá instanciar novas caso necessário
		ds.setMinPoolSize(3);
		
		// Por padrão o máximo é 15
		ds.setMaxPoolSize(5);
		
		// Aqui você configura threads assíncronas para o banco usar em tarefas mais trabalhosas. O padrão é 3.
		ds.setNumHelperThreads(5);
		
		// Aqui você configura para que a cada 1 segundo a JPA teste conexões ociosas.
		// QUAL A IMPORTÂNCIA DISSO?
		// Imagine que a conexão do banco caiu e depois voltou. Se a JPA não revisar as conexões abertas que estão no pool, ele tentará-usar uma que ele criou anteriormente mas que não existe mais (pq o banco foi reiniciado)
		// Com isso aqui você faz ele testar as conexões do pool e se desfazer delas caso o banco não as tenha mais (e cria novas para substituí-las)
		ds.setIdleConnectionTestPeriod(1);
		
		// Veja na classe TestaPool.java alguns métodos importantes para o gerenciamento do pool
		
		return ds;
		// Este era o data source original da aplicação. Ele gera uma conexão com 
		// o banco a cada chamada. Foi comentado para a implementação de pool de conexões.
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
