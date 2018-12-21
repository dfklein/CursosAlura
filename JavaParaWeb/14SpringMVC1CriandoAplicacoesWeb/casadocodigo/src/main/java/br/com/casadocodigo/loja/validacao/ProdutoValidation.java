package br.com.casadocodigo.loja.validacao;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.model.Produto;

public class ProdutoValidation implements Validator {

	public void valida(Produto produto, Errors errors) {
		
	}

	// Neste método você define quais classes podem ser utilizadas neste validador.
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Produto.class.isAssignableFrom(clazz);
	}

	//	Este é método onde você escreve a lógica da sua validação. Veja que ele não tem retorno (void)
	@Override
	public void validate(Object target, Errors errors) {
		Produto produto = (Produto) target;
		
		// O Spring tem uma classe de validação que você pode utilizar para facilitar.
		// o último argumento ("field.required") é a parte inicial da chave da mensagem no Bundle
		// de mensagens que ele vai utilizar para enviar para a tela. O restante dela está declarado
		// na própria JSP (ver form.jsp a tag <form:errors path="...">). Se esta chave não existir no
		// bundle de mensagens, você vai recever um erro de NoSuchMessageException.
		// Lembre-se de que o message bundle precisa ser configurado na classe de configurações web
		// da sua aplicação (AppWebConfiguration.class).
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
				
		if(produto.getPaginas()==null || produto.getPaginas() <= 0) {
			errors.rejectValue("paginas", "field.required");
		}
				
				// O próprio Srping saberá utilizar este objeto Errors que ele recebe como argumento.
		
	}
}
