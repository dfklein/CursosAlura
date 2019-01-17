package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO dao;
	
	@Autowired
    private FileSaver fileSaver;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	// Isto invalida o cache de nome "produtosHome"
	// allEntries=true diz que ele deve limpar todas as entradas encontradas no cache.
	@CacheEvict(value="produtosHome", allEntries=true)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, 
				RedirectAttributes redirectAttributes){
		
		if(result.hasErrors()) {
			return form(produto);
		}
		
		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(path);
		
		dao.gravar(produto);
		
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		
		return new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping( method=RequestMethod.GET)
	public ModelAndView listar() {
		// Aqui você não fez o inner join fetch para trazer os preços. Você fez uma configuração para fazer com
		// que a transação durasse todo o tempo da requisição, dentro do filtro. Por isso, ao abrir a página que evoca o preço ele
		// consegue inicializar a coleção de preços.
		// Veja seu arquivo de configurações de servlets (ServletSpringMVC.class) no método getServletFilters
		// OBS: Isso não é necessariamente uma vantagem. Pode gerar uma quantidade alta de queries (para a performance eu pessoalmente acho isso ruim) porque ele faz uma query por cada ítem da coleção. Para isso é melhor usar o JOIN FETCH mesmo.
		List<Produto> produtos = dao.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id){
	    ModelAndView modelAndView = new ModelAndView("/produtos/detalhe");
	    Produto produto = dao.find(id);
	    modelAndView.addObject("produto", produto);
	    
	    return modelAndView;
	}
	
	// Este aqui é um exemplo de como retornar um JSON ao invés de uma view, para realizar integrações
	// com outros sistemas. O método está comentado porque a ideia era mostrar o uso do content negotiation
	// Para prover este mesmo retorno a partir de um método que já existe.
	// (Ver o método contentNegotiationViewResolver() de AppWebConfiguration.class).
	// O @ResponseBody vai converter a sua entidade em JSON.
	// Só funciona se você tiver o Jackson nas dependências do seu projeto.
	//		@RequestMapping("/{id}")
	//		@ResponseBody
	//		public Produto detalheJson(@PathVariable("id") Integer id){
	//			Produto produto = dao.find(id);
	//			return produto;
	//		}
	
	// ExceptionHandler é o tratamento que você dá ao Controller para todas as exceções do tipo declarado
	// no argumento.
	// Lembre-se de que neste exemplo este tratamento é feito APENAS para ProdutosController, não se aplicando a outros controllers da aplicação
	// É possível aplicar um tratamento de exceção para toda a aplicação. Veja a classe ExceptionHandlerController.class
	@ExceptionHandler(NoResultException.class)
	// O método pode possuir dois construtores: sem argumentos ou recebendo uma Exception
//	public ModelAndView trataDetalheNaoEncontrado() {
	public ModelAndView trataDetalheNaoEncontrado(Exception e) {
		System.out.println("Exceção encontrada: " + e.getClass().getName());
		ModelAndView mv = new ModelAndView("error-prodNaoEncontrado");
		mv.addObject("exception", e);
		return mv;
	}
	
}
