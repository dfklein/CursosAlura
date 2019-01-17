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
		// Aqui você não fez o inner join fetch para trazer os preços. Você fez uma
		// configuração para fazer com
		// que a transação durasse todo o tempo da requisição, dentro do filtro. Por
		// isso, ao abrir a página que evoca o preço ele
		// consegue inicializar a coleção de preços.
		// Veja seu arquivo de configurações de servlets (ServletSpringMVC.class) no
		// método getServletFilters
		// OBS: Isso não é necessariamente uma vantagem. Pode gerar uma quantidade alta de queries (para a performance eu pessoalmente acho isso ruim) porque ele faz uma query por cada ítem da coleção. Para isso é melhor usar o JOIN FETCH mesmo.
		List<Produto> produtos = produtoDAO.listar();
		
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("produtos", produtos);
		
		return mv;
	}
}
