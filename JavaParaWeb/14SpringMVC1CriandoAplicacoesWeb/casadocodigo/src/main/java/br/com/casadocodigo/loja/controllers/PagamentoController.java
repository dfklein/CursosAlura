package br.com.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.model.CarrinhoCompras;
import br.com.casadocodigo.loja.model.DadosPagamento;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;
	
	// Note que este objeto RestTemplate foi injetado e precisou de configurações adicionais, que podem ser encontradas
	// no arquivo de configurações web da aplicação (AppWebConfig.class)
	@Autowired
	private RestTemplate restTemplate;

	// Observe o uso de Callable no retorno deste método. Isto é feito para tornar esta chamada assíncrona,
	// permitindo que a aplicação não trave e deixando apenas um usuário aguardando pela resposta do servidor.
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public Callable<ModelAndView> finalizar(RedirectAttributes model) {
		
		return () -> {
			
			ModelAndView mv = new ModelAndView("redirect:/produtos");
			
			try {
				// No projeto foi entendido que você chama um serviço externo de meio de pagamento (tipo PayPal).
				// Abaixo a implementação de como é gerada a requisição REST.
				String uri = "http://book-payment.herokuapp.com/payment";
				// Os parâmetros do método postForObject utilizados aqui são respectivamente:
				//	1 - A url da requisição
				//  2 - O objeto convertido em JSON (entrar na classe DadosPagamento para ver detalhes de como o Spring forma este JSON)
				//  3 - O tipo de objeto que será retornado pelo serviço chamado.
				// Note que este objeto RestTemplate foi injetado e precisou de configurações adicionais, que podem ser encontradas
				// no arquivo de configurações web da aplicação (AppWebConfig.class)
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
				
				System.out.println("Resposta do serviço de pagamento: " + response);
				
				model.addFlashAttribute("sucesso", "Pagamento realizado com sucesso!");
			} catch (HttpClientErrorException e) {
				HttpStatus statusCode = e.getStatusCode();
				System.out.println("HttpClientErrorException -> retornou código: " + statusCode);
				model.addFlashAttribute("falha", "Valor maior que o permitido");
			} catch (RestClientException e) {
				// NÃO TINHA NO CURSO: Quando você criou o try/catch o Eclipse sugeriu esta exceção no catch.
				// Saiba que ela é super classe de HttpClientErrorException
				e.printStackTrace();
			}
			
			return mv;
			
		};
		
		
	}
}
