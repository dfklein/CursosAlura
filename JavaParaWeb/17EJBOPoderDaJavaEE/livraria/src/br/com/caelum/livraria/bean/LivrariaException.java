package br.com.caelum.livraria.bean;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
// Se você estende Exception, está criando uma Application Exception.
// É a anotação @ApplicationException que configura se ela causa ou não rollback quando lançada dentro de uma transação.
public class LivrariaException extends Exception {
	// No vídeo a exceção extendeu RuntimeException, tornando-a unchecked e livrando o desenvolvedor da obrigação de tratá-la. 
	// Ele a chama de System Exception unchecked com rollback.
	
	
	// ****** DIFERENÇA ENTRE SYSTEM EXCEPTION E APPLICATION EXCEPTION: ******
	//	Uma System Exception é algo grave, imprevisto, não deveria acontecer e sempre causa um rollback da transação. 
	// Normalmente são exceções de infra-estrutura. Além disso, aquele Session Bean que lançou a exceção é invalidado e 
	// retirado do pool de objetos. Por padrão, qualquer exceção unchecked é System Exception.
	//
	//	Uma Application Exception pode acontecer durante a vida da aplicação e relacionada
	// ao domínio. Por isso não causa rollback e nem invalida o Session Bean. Por padrão, 
	// qualquer exceção checked é Application Exception.

}
