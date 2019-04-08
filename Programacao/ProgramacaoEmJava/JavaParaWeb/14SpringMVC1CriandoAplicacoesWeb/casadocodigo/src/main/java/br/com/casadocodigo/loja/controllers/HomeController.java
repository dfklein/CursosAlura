package br.com.casadocodigo.loja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// @Controller para indicar que trata-se de um controller de requisições. Lembre-se que para fazer
// isso você precisa possuir uma servlet principal do Spring que lida com as requisições do servidor.
// Nesta aplicação a classe se chama ServletSpringMVC. 
// Lembre-se e que tudo isso é possível fazer também via XML (mas ninguém merece passar por isso).
@Controller
public class HomeController {

	// @RequestMapping vai indicar o caminho da URL da requisição ao qual o método está atrelado.
	@RequestMapping("/")
	// O retorno dos métodos mapeados deve indicar qual é a view (html) que deve ser devolvida ao
	// navegador quando esta requisição é feita.
	// Se ele nao encontrar o arquivo indicado no retorno na pasta webapp você terá um erro como:
	// ---- > javax.servlet.ServletException: Could not resolve view with name 'home.jsp' in servlet with name 'dispatcher'
	// No entanto, lembre-se de que a pasta webapp é aberta (o usuário pode acessar diretamente com
	// http://.../home.jsp, o que o faria ter acesso à view sem passar pelo seu controller (sem 
	// executar sua lógica). Coloque seus JSPs dentro da WEB-INF, uma vez que o servidor protege
	// o acesso a ela.
	// Você deve indicar ao Spring onde ele pode buscar os arquivos que retorna, o que é feito na
	// classe de configuração das servlets (aqui é a AppWebCongiruation.class, no método
	// internalResourceViewResolver() ) 
	public String index() {
		System.out.println("Entrando na home!");
		
		// Você pode retornar apenas o nome do arquivo sem o caminho da pasta e sem a extensão do
		// arquivo porque você configurou na classe AppWebCongiruation.class no método 
		// internalResourceViewResolver() para que o próprio Spring entendesse isso.
		return "home";
	}
}
