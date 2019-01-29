package br.com.alura.listavip;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

// Esta é a anotação que configura esta classe como a de configuração do Spring Boot. Ela agrega
// diversas anotações do SpringMVC em uma só. Ver mais em 
// https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-using-springbootapplication-annotation.html
@SpringBootApplication
public class Configuracao {

	// Este é o método main da aplicação. Veja que é como uma aplicação java qualquer (inclusive
	// você pode empacotá-la como um jar e rodá-la em linha de comando!). O método run() 
	// de SpringApplication recebe a classe de configuração da sua aplicação e se encarrega de
	// executar a sua aplicação.
	public static void main(String[] args){
	    SpringApplication.run(Configuracao.class, args);
	}
	
	@Bean
	public DataSource dataSource(){
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl("jdbc:mysql://localhost:3306/listavip");
	    dataSource.setUsername("root");
	    dataSource.setPassword("");
	    return dataSource;
	}
	
}
