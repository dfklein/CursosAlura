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
		// Primeiro você separa todos os métodos e vai iterar um a um.
		Stream<Method> metodos = Stream.of(instancia.getClass().getDeclaredMethods());
		
		Method metodoSelecionado = metodos.filter(m -> 
			m.getName().equals(nomeMetodo)
			&& m.getParameterCount() == params.values().size()
			// A próxima iteração é sobre os parâmetros declarados de cada método.
			// ATENÇÃO: A partir da JDK 8 é possível obter o nome dos parâmetros de cada método, sem ter que lidar com nomes padronizados como arg0, arg1, etc.
			// Só que para isso você precisa configurar algo no seu projeto:
			// 		1 - Botão direito sobre o projeto -> Propriedades -> Java Compiler
			// 		2 - Marcar o checkbox da seção "Classfile Generation" que se chama "Store information about method parameters (usable via reflection)"
			&& Stream.of(m.getParameters())
				// O allMatch() verifica um conjunto de condições, retornando true apenas se todas as condições ali forem true.
				.allMatch(arg -> {
//					System.out.println(arg.getName());
					return
					// CONDIÇÃO 1 = aquele parâmetro deve existir no mapa de parâmetros passados pela url chamada.
					params.keySet().contains(arg.getName())
					// CONDIÇÃO 2 = Verifica se o tipo passado nos argumentos da URL são compatíveis com o argumento que o método recebe (mas não entendi direito)
					&& params.get(arg.getName()).getClass().equals(arg.getType());
				})
			)
			.findFirst()
			.orElseThrow(() -> new RuntimeException("Método não encontrado"));
	
	
		return new ManipuladorMetodo(metodoSelecionado, this.instancia, params);
	}

}
