package br.com.caelum.livraria.webservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Livro;

// @WebService anotação vem do JAX-WS. Uma vez utilizada no EJB o próprio container a utiliza para publicar
// o session bean como serviço. Por padrão ele vai gerar um serviço SOAP em um endereço que tem o nome da
// classe no caminho: http://localhost:8080/livraria/LivrariaWS.
// OBS 1:Lembre-se de que uma chamada do tipo SOAP usa por padrão o método POST (não funcionaria no navegador 
// que por padrão envia um método GET)
// OBS 2: O EJB saberá gerar uma url para pegar o wsdl deste serviço SOAP. 
// Estará disponível em http://localhost:8080/livraria/LivrariaWS?wsdl
@WebService 
@Stateless
public class LivrariaWS {
	
	@Inject
	private LivroDao dao;

	// @WebResult vai definir o nome do elemento devolvido no XLS da resposta
	@WebResult(name="autores")
	// @WebParam é o nome do parâmetro que será enviado na requisição. 
	public List<Livro> getLivrosPeloNome(@WebParam(name="titulo") String nome) {
		
		System.out.println("Teste WS - recebido o parâmetro \"nome\" com o valor " + nome);

		return dao.livrosPeloNome(nome);
		
	}
}
