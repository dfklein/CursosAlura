package br.com.alura.alurator.reflexao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class ManipuladorMetodo {

	private Method metodo;
	private Object instancia;
	private Map<String, Object> params;
	
	private BiFunction<Method, InvocationTargetException, Object> tratamentoExcecao;

	public ManipuladorMetodo(Method metodo, Object instancia, Map<String, Object> params) {
		this.metodo = metodo;
		this.instancia = instancia;
		this.params = params;
	}
	
	/**
	 * H� uma interface que atende todas as nossas necessidades! E ela se chama BiFunction<T, U, R> (link do Javadoc)! Essa interface funcional possui um m�todo apply(T t, U u) que recebe dois objetos de tipos gen�ricos T e U e retorna um terceiro tipo R. Em outras palavras, � esse m�todo que deve ser invocado para que nossa express�o lambda funcione!
	 * 
	 * Veja o if que foi feito no catch de InvocationTargetException no m�todo invoca()
	 */
	public ManipuladorMetodo comTratamentoDeExcecao(BiFunction<Method, InvocationTargetException, Object> tratamentoExcecao) {
        this.tratamentoExcecao = tratamentoExcecao;
        return this;
    }
	
	public Object invoca() {
		try {
			ArrayList<Object> parametros = new ArrayList<Object>();
			
			Stream.of(metodo.getParameters())
				.forEach(p -> parametros.add(params.get(p.getName())));
			
			return metodo.invoke(instancia, parametros.toArray());
			
		} catch (InvocationTargetException e) {
			if (tratamentoExcecao != null) {
                return tratamentoExcecao.apply(metodo, e);
            }
			throw new RuntimeException("Exe��o lan�ada pelo construtor do m�todo", e);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

}
