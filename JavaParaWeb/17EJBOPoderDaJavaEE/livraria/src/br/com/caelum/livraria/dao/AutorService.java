package br.com.caelum.livraria.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.caelum.livraria.bean.LivrariaException;
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
	public void adiciona(Autor autor) throws Exception {
		// **** Regras de negócio
		autorDao.salva(autor);
		
		// SOBRE EXCEÇÕES E ROLLBACK:
		// ********************
		// O padrão do EJB prevê que o lançamento de uma exceção checked é considerada um application exception.
		// Em outras palavras: um application exception é algo previsto em código e, portanto, não é motivo para invalidar o session bean e a transação.
		// A linha abaixo lançaria uma exceção sem causar rollback.
		// throw new Exception();
		// ********************
		// No entanto uma exceção unchecked (ou seja, RuntimeException ou alguma que a estende) é considerada uma System Exception
		// Pela gravidade que é atribuída a um System Exception, por padrão o EJB invalida a sessão e a transação causando rollback.
		// O container empacota a exceção dentro de um EJBTransactionRolledbackException (
		// A linha abaixo não salvaria o autor porque causaria rollback:
		// throw new RuntimeException();
		// ********************
		// Por fim você pode configurar uma exceção criada por você para que, caso ela seja lançada, que cause o rollback da transação.
		// É o que você fez com LivrariaException (entre nela para ver o uso da anotação @ApplicationException.
		// Nesse caso, apesar do rollback, ela não é empacotada em um EJBTransactionRolledbackException
		// No vídeo a exceção extendeu RuntimeException, tornando-a unchecked e livrando o desenvolvedor da obrigação de tratá-la. Ele a chama de Application Exception unchecked com rollback.
		throw new LivrariaException();
		
		
	}

	public List<Autor> todosAutores() {
		return autorDao.todosAutores();
	}
	
}
