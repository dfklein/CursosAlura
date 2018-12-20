package br.com.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;

@Controller
@RequestMapping(value="/produtos")
public class ProdutosController {
	
	// Você já sabe, mas @Autowired é a anotação de injeção de dependência do Spring.
	@Autowired
	private ProdutoDAO produtoDao;
	
	// não é necessário especificar no mapeamento se ele está recebendo um método GET ou POST. A não
	// ser que você queira utilizar uma mesma URL para duas funções diferentes e especificar qual dos
	// métodos http você está mapeando (ver gravar(Produto produto) e listar())
	@RequestMapping(value="/form")
	public ModelAndView form() {
		// Para exibir a lista de tipos de preço na tela, você precisa atachar eles na requisição para
		// ser lido na view JSP (form.jsp).
		// No Spring você não trabalha com o HttpRequest diretamente. Você pode mudar o retorno do método
		// para um objeto chamado ModelAndView que faz parte do framework, onde você atacha um objeto
		// da seguinte forma:
		ModelAndView mv = new ModelAndView("/produtos/form");
		mv.addObject("tipos", TipoPreco.values());
		
		// Se você não precisasse atachar um objeto ao HttpRequest, poderia apenas retornar a String
		// com o caminho da view.
		// ----> return "/produtos/form";
		
		// Como você precisa atachar o objeto, você mudou o retorno do método para o ModelAndView que
		// você criou lá em cima. Note que você colocou o caminho do arquivo JSP no construtor desse
		// ModelAndView. Uma maneira alternativa é setar o caminho do JSP depois da seguinte forma:
		// ----> mv.setViewName("/produtos/form");
		
		return mv;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	// Observe que se você colocar nos argumentos do método o mesmo nome que você deu para eles no
	// JSP (atributo "name") o Spring automaticamente faz a conversão do atributo da requisição para
	// um argumento recebido pelo método que mapeia a requisição. Isto é chamado de "binding".
	// O binding pode ser feito em atributos do argumento, como no exemplo:
	// ----> public String salvar(String titulo, String descricao, int paginas) {
	// Ou ele pode procurar pelos nomes de atributos de um objeto que você diz que é o que ele recebe,
	// como na implementação abaixo:
	public String gravar(Produto produto) {
		
		System.out.println(produto);
		produtoDao.gravar(produto);
		
		return "/produtos/ok";
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/produtos/lista");
		List<Produto> produtos = produtoDao.listar();
		
		mv.addObject("produtos", produtos);
		
		return mv;
	}

}
