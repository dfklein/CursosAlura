package br.com.alura.alurator.reflexao;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Stream;

public class ManipuladorObjeto {

	private Object instancia;

	public ManipuladorObjeto(Object instancia) {
		this.instancia = instancia;
		
	}

	public ManipuladorMetodo getMetodo(String nomeMetodo, Map<String, Object> params) {
		// Primeiro voc� separa todos os m�todos e vai iterar um a um.
		Stream<Method> metodos = Stream.of(instancia.getClass().getDeclaredMethods());
		
		Method metodoSelecionado = metodos.filter(m -> 
			m.getName().equals(nomeMetodo)
			&& m.getParameterCount() == params.values().size()
			// A pr�xima itera��o � sobre os par�metros declarados de cada m�todo.
			// ATEN��O: A partir da JDK 8 � poss�vel obter o nome dos par�metros de cada m�todo, sem ter que lidar com nomes padronizados como arg0, arg1, etc.
			// S� que para isso voc� precisa configurar algo no seu projeto:
			// 		1 - Bot�o direito sobre o projeto -> Propriedades -> Java Compiler
			// 		2 - Marcar o checkbox da se��o "Classfile Generation" que se chama "Store information about method parameters (usable via reflection)"
			&& Stream.of(m.getParameters())
				// O allMatch() verifica um conjunto de condi��es, retornando true apenas se todas as condi��es ali forem true.
				.allMatch(arg -> {
//					System.out.println(arg.getName());
					return
					// CONDI��O 1 = aquele par�metro deve existir no mapa de par�metros passados pela url chamada.
					params.keySet().contains(arg.getName())
					// CONDI��O 2 = Verifica se o tipo passado nos argumentos da URL s�o compat�veis com o argumento que o m�todo recebe (mas n�o entendi direito)
					&& params.get(arg.getName()).getClass().equals(arg.getType());
				})
			)
			.findFirst()
			.orElseThrow(() -> new RuntimeException("M�todo n�o encontrado"));
	
	
		return new ManipuladorMetodo(metodoSelecionado, this.instancia, params);
	}

}
