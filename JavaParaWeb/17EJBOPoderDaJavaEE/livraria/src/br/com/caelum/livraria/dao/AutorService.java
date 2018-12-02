package br.com.caelum.livraria.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.caelum.livraria.modelo.Autor;

@Stateless
public class AutorService {

	@Inject
	private AutorDao autorDao;
	
	// Uma vez que foi realizada a inversão de controle, não é necessário gerenciar a transação. O próprio servidor de aplicação saberá fazer isto sem que você.
		// Se você chamasse o método getTransaction() do EntityManager, você receberia um IllegalStateException dizendo que um EntityManager do tipo JTA não pode usar este método.
		// o @TransactionAttribute define as formas do container gerenciar as transações em cada método:
		// 1 - REQUIRED: Significa que a JTA garante que haverá uma transação ao usar este método. Caso ele não componha uma transação já aberta (se vier de um método que já possui uma transação, por exemplo), o container se encarrega de criá-la
		// 2 - MANDATORY: Significa que é um método que necessariamente vem de outra transação. Se o método for chamado e uma transação não tiver sido criada anteriormente, o container lança uma exceção.
		//				  No curso é sugerido que se anote os métodos do DAO como MANDATORY e que exista uma classe de serviço onde seus métodos abrem as transações são abertas (com REQUIRED, por exemplo)
		// 3 - REQUIRES_NEW: Significa que sempre será criada uma nova transação para aquela operação. Caso o método seja chamado dentro de uma transação em curso, esta transação será SUSPENSA e a nova transação será criada.
		// 4 - NEVER: Significa que o método nunca poderá ser executado em um contexto transacional. Ou seja: você receberá uma exceção caso ele seja chamado dentro de uma transação.
		// 5 - SUPPORTS: O código será executado com ou sem transação.
		// 6 - NOT_SUPPORTED o código deverá ser executado sem transação, caso alguma transação esteja aberta, ela será suspensa temporariamente até a execução do método acabar.
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void adiciona(Autor autor) {
		// **** Regras de negócio
		autorDao.salva(autor);
		
	}

	public List<Autor> todosAutores() {
		return autorDao.todosAutores();
	}
	
}
