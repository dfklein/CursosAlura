package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.model.Produto;

@Controller
public class ProdutosController {
	
	// Você já sabe, mas @Autowired é a anotação de injeção de dependência do Spring.
	@Autowired
	private ProdutoDAO produtoDao;
	
	// não é necessário especificar no mapeamento se ele está recebendo um método GET ou POST.
	@RequestMapping("/produtos/form")
	public String form() {
		
		return "/produtos/form";
	}
	
	@RequestMapping("/produtos")
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

}
