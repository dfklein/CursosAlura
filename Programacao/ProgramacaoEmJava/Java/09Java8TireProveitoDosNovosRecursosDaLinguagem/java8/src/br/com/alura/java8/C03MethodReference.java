package br.com.alura.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class C03MethodReference {

	public static void main(String[] args) {
		List<String> palavras = new ArrayList<>();
		palavras.add("alura online");
		palavras.add("editora casa do código");
		palavras.add("caelum");
		
		/**
		 * Este é o código da aula passada. Abaixo você vai ver outra implementação para esta mesma lógica. 
		 *
		 * 		System.out.println("--- Sort com lambda expression");
		 * 		palavras.sort((s1, s2) -> {  return Integer.compare(s1.length(), s2.length());  });
		 * 		System.out.println(palavras);
		 * 
		 * É possível deixar este código mais sucinto com Function<T, U>
		 */
		
		/**
		 * A interface Function<T, U> pede a implementação do método apply(T t). O que é importante deste objeto
		 * é o tipo generics que você declara. Você está dizendo que vai criar uma função em que dado um
		 * primeiro tipo genérico ele deve possuir uma implementação que devolverá o segundo tipo.
		 * 
		 * No exemplo acima, em que você está criando um Comparator, você precisa passar uma função que
		 * "dada uma String esta implementação deverá retornar um Integer" (que é como funcionaria um
		 * Comparator que compara o tamanho de duas Strings.
		 *
		 *		palavras.sort(Comparator.comparing(new Function<String, Integer>() {
		 *			@Override
		 *			public Integer apply(String s) {
		 *				return s.length();
		 *			}
		 *		}));
		 * 
		 * Ou para você saber como ficaria só a função:
		 * 
		 * 		Function<String, Integer> function = s -> s.length();
		 * 		palavras.sort(Comparator.comparing(function));
		 * 
		 * Resolvendo em uma linha temos:
		 *
		 *		palavras.sort(Comparator.comparing(s -> s.length()));
		 *
		 * Note que, em um caso desses, o que você quer é apenas chamar um único método e é
		 * quando você pode usar o METHOD REFERENCE. Você utiliza o nome da classe cujo método você
		 * quer evocar, o operador :: e o nome do método ele deve chamar (sem os parênteses de 
		 * argumento), como nesta versão final:
		 */
		palavras.sort(Comparator.comparing(String::length));
		System.out.println(palavras);
		
		System.out.println("\n");
		
		/**
		 * Um outro exemplo de method reference que é muito esquisito.
		 * 
		 * 		Consumer<String> consumer = s -> System.out.println(s);
		 * 		Consumer<String> consumer = System.out::println;
		 * 
		 * Este exemplo mostra que o objeto cuja referência está à esquerda do lambda pode ser passado
		 * como o argumento do método que você está chamando. Meio confuso, mas enfim!
		 * 
		 * Note que no primeiro exemplo o elemento à esquerda do method reference era uma classe enquanto
		 * que aqui é uma outra variável (a out de System).
		 * 
		 * Isso termina em:
		 */
		palavras.forEach(System.out::println);
		
		// Só por curiosidade, o curso mostrava isso aqui na parte escrita:
		palavras.sort(String.CASE_INSENSITIVE_ORDER);
	}
	
}
