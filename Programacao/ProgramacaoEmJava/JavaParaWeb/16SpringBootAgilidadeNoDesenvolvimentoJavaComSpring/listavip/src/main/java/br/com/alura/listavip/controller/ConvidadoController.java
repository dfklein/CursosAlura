package br.com.alura.listavip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.service.ConvidadosService;

@Controller
public class ConvidadoController {
	
	@Autowired
	private ConvidadosService service;

	@RequestMapping("/")
	public String index() {
		// Observe que apenas por adotar a convenção do Spring Boot (colocar seus templates na pasta
		// resources) vocÃª nÃ£o precisou fazer todas aquelas configurações de servlet que você fez nos
		// cursos de mvc para retornar o nome do arquivo da página de resposta da requisição.
		return "index";
	}
	
	/**
	 * Aparentemente toda requisição do Spring Boot possui um objeto da interface Model que pode ser
	 * recebido como argumento. O que você adicionar a ele é atachado ao response da requisição para
	 * ser utilizado na tela.
	 */
	@RequestMapping("listaconvidados")
	public String listaConvidados(Model model){
		Iterable<Convidado> convidados = service.obterTodos();
		
		// Ele não utilizou o ModelAndView, passou a receber um objeto Model como argumento.
		model.addAttribute("convidados", convidados);
		
	    return "listaconvidados";
	}
	
	@RequestMapping(value= "salvar", method = RequestMethod.POST)
	public String salvar(@RequestParam("nome") String nome, @RequestParam("email") String email, 
            @RequestParam("telefone") String telefone, Model model ){
		
		Convidado novoConvidado = new Convidado(nome, email, telefone);
	    service.salvar(novoConvidado);

	    // No curso ele fez o código abaixo. Eu achei tosco e por isso fiz um redirect
	    // O objeto Model que esse método recebe como argumento serve só pra isso.
		//	    Iterable<Convidado> convidados = repository.findAll();
		//	    model.addAttribute("convidados", convidados);
	    return "redirect:listaconvidados";

	}
}
