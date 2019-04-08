package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;
import br.com.casadocodigo.loja.validacao.ProdutoValidation;

@Controller
@RequestMapping(value="/produtos")
public class ProdutosController {
	
	// Você já sabe, mas @Autowired é a anotação de injeção de dependência do Spring.
	@Autowired
	private ProdutoDAO produtoDao;
	
	@Autowired // NÃO ESQUEÇA: a classe FileSaver só pode ser injetada se você colocá-la no @ComponentScan da sua classe de configurações web (AppWebConfiguration.class)
	private FileSaver fileSaver;
	
	
	// @InitBinder é a anotação que indica ao Spring qual método faz a ligação entre uma classe
	// entidade e sua classe de validação. Veja a classe ProdutoValidation.class para ver 
	// a implementação.
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
		
	}
	
	// não é necessário especificar no mapeamento se ele está recebendo um método GET ou POST. A não
	// ser que você queira utilizar uma mesma URL para duas funções diferentes e especificar qual dos
	// métodos http você está mapeando (ver gravar(Produto produto) e listar())
	@RequestMapping(value="/form")
	public ModelAndView form(Produto produto) { // O produto recebido aqui como argumento é um tipo de injeção da entidade para atachá-la à requisição. A tela quebra sem isso quando você utiliza o form:input
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
	// *** Observe que se você colocar nos argumentos do método o mesmo nome que você deu para eles no
	// JSP (atributo "name") o Spring automaticamente faz a conversão do atributo da requisição para
	// um argumento recebido pelo método que mapeia a requisição. Isto é chamado de "binding".
	// O binding pode ser feito em atributos do argumento, como no exemplo:
	// ----> public String salvar(String titulo, String descricao, int paginas) {
	// Ou ele pode procurar pelos nomes de atributos de um objeto que você diz que é o que ele recebe,
	// como na implementação abaixo:
	// *** @Valid: exige que para aceitar o objeto como argumento, ele seja aprovado pelo validador
	// que você fez na classe ProdutoValidation.class. A ligação entre o @Valid e o validador
	// desejado é feito aqui nesta classe no método initBinder() que precisa da anotação @InitBinder
	// para ser chamado como quem faz esta ligação. A interface BindingResult recebida aqui como
	// argumento faz parte desta validação.
	// ATENÇÃO: a ordem dos argumentos é importante e o construtor do método exige o BindigResult logo
	// após o argumento que possui o @Valid
	// *** MultipartFile é uma interface do Spring que lida com arquivos. Para fazer o upload de
	// arquivos você vai precisar que:
	// 	1 - O construtor do seu método receba um MultipartFile
	//  2 - Que a declaração do form no seu JSP possua o atributo 'enctype="multipart/form-data"' (ver arquivo form.jsp)
	//  3 - Uma configuração na sua classe de configurações web (ver o arquivo AppWebConfiguration.class - multipartResolver())
	//  4 - Uma configuração na sua classe de configuração de servlets (ver o arquivo ServletSpringMVC.class - customizeRegistration())
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes att) {
		
		System.out.println(sumario.getOriginalFilename());
		
		
		if(result.hasErrors()) {
			System.out.println("Erro de validação");
			return form(produto);
		}
		
		// NÃO ESQUEÇA: a classe FileSaver só pode ser injetada se você colocá-la no @ComponentScan da sua classe de configurações web (AppWebConfiguration.class)
		produto.setSumarioPath(fileSaver.write("arquivos-sumario", sumario));
		
		System.out.println(produto);
		produtoDao.gravar(produto);
		
		// Na aula 5 o retorno foi alterado para que ao final de uma inclusão, fosse exibida a listagem de produtos.
		// ----> return "/produtos/ok";
		
		// A primeira tentativa foi chamando o método listar. Mas fazendo desta forma, você permite ao
		// usuário que dando refresh no navegador, já na página de listagem após incluir um novo livro,
		// a requisição seja reenviada com os dados do formulário preenchidos anteriormente. Isto criaria
		// várias inserções na base de dados com o mesmo preenchimento do formulário. Isto porque
		// você não está fazendo o redirect com o navegador (http status 302).
		// ----> return listar();
		
		// Para mandar o navegador fazer o redirect (por baixo dos panos: devolver um status 302 ao navegador
		// ao invés do 200 quando o formulário for submetido) e não submeter novamente o formulário:
		ModelAndView mv = new ModelAndView("redirect:produtos");
		
		// Ao fazer um redirect, você está dizendo ao navegador que ele deve alterar o método da última
		// requisição de POST para GET. Quando o Spring detecta o redirect mas vê que você está tentando
		// atachar um parâmetro à requisição (como na linha comentada abaixo), ele vai automaticamente
		// converter este objeto para tentar passá-lo via GET (ou seja: vai adicionar os parâmetros à URL).
		// Desta forma a mensagem não será exibida:
		// ----> mv.addObject("sucesso", "Produto cadastrado com sucesso!");
		
		// para fazer isso você adicionou aos argumentos deste método um objeto chamado RedirectAttributes
		// Ele vai carregar na memória flash um objeto que precisa ser mantido até a requisição do redirecionamento.
		// O Flash Scoped é um escopo onde os objetos que adicionamos nele através do método addFlashAttribute
		// ficam vivos de um request para outro, enquanto o navegador executa um redirect (usando o código de
		// status 302).
		att.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		
		
		return mv;
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/produtos/lista");
		List<Produto> produtos = produtoDao.listar();
		
		mv.addObject("produtos", produtos);
		
		return mv;
	}

	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("produtos/detalhe");
		
		Produto produto = produtoDao.find(id);
		mv.addObject("produto", produto);
		
		return mv;
		
	}
}
