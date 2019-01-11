package br.com.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;

@Controller
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class HomeController {
	
	@Autowired
	private ProdutoDAO produtoDAO;

	@RequestMapping("/")
	// O Spring possui um cache que pode ser utilizado em controllers através desta anotação. 
	// Ele vai cachear a resposta desta requisição, útil para quando isto não muda muito.
	// Para que esta anotação funcione, você precisa habilitá-la nas configurações web da aplicação (aqui em AppWebController.class)
	// Habilitar o cache nas configuraçoes web compreende uma anotação na declaração da classe e escrever um Bean para isso.
	// É obrigatório dar um nome para o cache (atributo value) para você indicar quando este cache passa a ser invalidado
	// Para invalidar o cache você precisa usar a anotação @CacheEvict na ação que invalida este cache (ver gravar() da classe ProdutosController.class)
	// Apesar dele não ter citado isto no curso, você descobriu que funciona mesmo se o escopo do controller for de requisição.
	@Cacheable(value="produtosHome")
	public ModelAndView index() {
		List<Produto> produtos = produtoDAO.listar();
		
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("produtos", produtos);
		
		
		return mv;
	}
}
