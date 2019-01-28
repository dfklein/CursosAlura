package br.com.alura.listavip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// Esta � a anota��o que configura esta classe como a de configura��o do Spring Boot. Ela agrega
// diversas anota��es do SpringMVC em uma s�. Ver mais em 
// https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-using-springbootapplication-annotation.html
@SpringBootApplication
@Controller
public class Configuracao {

	// Este � o m�todo main da aplica��o. Veja que � como uma aplica��o java qualquer (inclusive
	// voc� pode empacot�-la como um jar e rod�-la em linha de comando!). O m�todo run() 
	// de SpringApplication recebe a classe de configura��o da sua aplica��o e se encarrega de
	// executar a sua aplica��o.
	public static void main(String[] args){
	    SpringApplication.run(Configuracao.class, args);
	}
	
	@RequestMapping("/")
	@ResponseBody
	public String ola(){
		return "<h1>Ola, Bem vindo ao sistema Lista VIPs</h1>";
	}
}
