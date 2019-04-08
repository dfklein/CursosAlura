package br.com.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosPagamento;
import br.com.casadocodigo.loja.models.Usuario;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// Um e-mail precisa de um objeto que cuida do envio. Para isso você injeta um MailSender.
	// Não esqueça de incluir o Bean do MailSender no seu arquivo de configurações web (AppWebConfiguration.class)
	// IMPORTANTE: o nome do método do Bean PRECISA SER mailSender()
	@Autowired
	private MailSender sender;
	
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	// @AuthenticationPrincipal traz do Spring Security o usuário autenticado da requisição
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario, RedirectAttributes model){
		return () -> {
			String uri = "http://book-payment.herokuapp.com/payment";
			
			try {
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
				model.addFlashAttribute("sucesso", response);
				// A chamada abaixo está comentada porque o envio de e-mail está esbarrando em configurações de segurança do próprio gmail.
				// ----> enviaEmailCompraProduto(usuario);
				System.out.println(response);
				return new ModelAndView("redirect:/produtos");
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				model.addFlashAttribute("falha", "Valor maior que o permitido");
				return new ModelAndView("redirect:/produtos");
			}
		};
	}

	private void enviaEmailCompraProduto(Usuario usuario) {
		// O email em si pode ser feito através de um objeto que o próprio Spring possui para isso
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra finalizada com sucesso");
		// email.setTo(usuario.getEmail());
		email.setTo("dklein@gmail.com");
		email.setText("Compra aprovada com sucesso no valor de " + carrinho.getTotal());
		email.setFrom("compras@casadocodigo.com.br");

		// Um e-mail precisa de um objeto que cuida do envio. Para isso você injeta um MailSender.
		// Não esqueça de incluir o Bean do MailSender no seu arquivo de configurações web (AppWebConfiguration.class)
		// IMPORTANTE: o nome do método do Bean PRECISA SER mailSender()
		sender.send(email);
		
	}
}