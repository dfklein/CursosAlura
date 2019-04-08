package br.com.caelum.livraria.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LogInterceptador {

	// O @AroundInvoke é uma anotação que faz com que um método funcione como um callback de uma aplicação
	// Apesar de não ser uma sobrescrição de método, você precisa implementá-lo com retorno e o argumento específicos
	// TEM QUE retornar um Object.
	// TEM QUE receber um InvocationContext como argumento
	// Ao chamar o método proceed() do InvocationContext você permite que este filtro libere a execução do método EJB
	// O que for declarado antes é executado antes e o que é declarado depois é executado depois.
	// A classe interceptada também precisa ser configurada (ver AutorDao)
	@AroundInvoke
	public Object interceptar(InvocationContext ctx) throws Exception {
		long millis = System.currentTimeMillis();
		System.out.println("Medindo o tempo da transação");
		
		Object o = ctx.proceed();
		
		System.out.println("Tempo da transação em milisegundos: " + (System.currentTimeMillis() - millis));
		System.out.println("O método que utilizou este EJB interceptor é o " + ctx.getMethod().getName() + " da classe " + ctx.getTarget().getClass().getName());
		
		// Se por algum motivo você não retornar o objeto obtido pelo método proceed(), o método EJB nunca será executado.
		// Ele inclusive pode retornar para outro interceptor mas vazio, caso o primeiro retorne null, por exemplo.
		return o;
	}
}
