package br.com.casadocodigo.loja.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice faz com que seu controller seja lido por todos os demais controllers. Meio como um interceptor.
// É possível fazer um tratamento indivudual de exceções em cada controller. Veja ProdutosController.class
@ControllerAdvice
public class ExceptionHandlerController {
	
    @ExceptionHandler(Exception.class)
    // O método pode possuir dois construtores: sem argumentos ou recebendo uma Exception
    // public String trataExceptionGenerica() {
    public String trataExceptionGenerica(Exception e) {
    	System.out.println("Exceção encontrada: " + e.getClass().getName());
        return "error";
    }
    
}
