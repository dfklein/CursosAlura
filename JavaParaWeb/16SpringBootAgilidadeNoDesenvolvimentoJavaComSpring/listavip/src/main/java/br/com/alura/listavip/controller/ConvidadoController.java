package br.com.alura.listavip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.listavip.model.Convidado;
import br.com.alura.listavip.repository.ConvidadoRepository;

@Controller
public class ConvidadoController {
	
	@Autowired
	private ConvidadoRepository repository;

	@RequestMapping("/")
	public String index() {
		// Observe que apenas por adotar a convenção do Spring Boot (colocar seus templates na pasta
		// resources) você não precisou fazer todas aquelas configurações de servlet que você fez nos
		// cursos de mvc para retornar o nome do arquivo da página de resposta da requisição.
		return "index";
	}
	
	@RequestMapping("listaconvidados")
	public String listaConvidados(Model model){
		Iterable<Convidado> convidados = repository.findAll();
		
		// Ele não utilizou o ModelAndView, passou a receber um objeto Model como argumento.
		model.addAttribute("convidados", convidados);
		
	    return "listaconvidados";
	}
}
