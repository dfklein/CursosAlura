package br.com.alura.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class C03MethodReference {

	public static void main(String[] args) {
		List<String> palavras = new ArrayList<>();
		palavras.add("alura online");
		palavras.add("editora casa do c�digo");
		palavras.add("caelum");
		
		/**
		 * Este � o c�digo da aula passada. Abaixo voc� vai ver outra implementa��o para esta mesma l�gica. 
		 *
		 * 		System.out.println("--- Sort com lambda expression");
		 * 		palavras.sort((s1, s2) -> {  return Integer.compare(s1.length(), s2.length());  });
		 * 		System.out.println(palavras);
		 * 
		 * � poss�vel deixar este c�digo mais sucinto com Function<T, U>
		 */
		
		/**
		 * A interface Function<T, U> pede a implementa��o do m�todo apply(T t). O que � importante deste objeto
		 * � o tipo generics que voc� declara. Voc� est� dizendo que vai criar uma fun��o em que dado um
		 * primeiro tipo gen�rico ele deve possuir uma implementa��o que devolver� o segundo tipo.
		 * 
		 * No exemplo acima, em que voc� est� criando um Comparator, voc� precisa passar uma fun��o que
		 * "dada uma String esta implementa��o dever� retornar um Integer" (que � como funcionaria um
		 * Comparator que compara o tamanho de duas Strings.
		 *
		 *		palavras.sort(Comparator.comparing(new Function<String, Integer>() {
		 *			@Override
		 *			public Integer apply(String s) {
		 *				return s.length();
		 *			}
		 *		}));
		 * 
		 * Ou para voc� saber como ficaria s� a fun��o:
		 * 
		 * 		Function<String, Integer> function = s -> s.length();
		 * 		palavras.sort(Comparator.comparing(function));
		 * 
		 * Resolvendo em uma linha temos:
		 *
		 *		palavras.sort(Comparator.comparing(s -> s.length()));
		 *
		 * Note que, em um caso desses, o que voc� quer � apenas chamar um �nico m�todo e �
		 * quando voc� pode usar o METHOD REFERENCE. Voc� utiliza o nome da classe cujo m�todo voc�
		 * quer evocar, o operador :: e o nome do m�todo ele deve chamar (sem os par�nteses de 
		 * argumento), como nesta vers�o final:
		 */
		palavras.sort(Comparator.comparing(String::length));
		System.out.println(palavras);
		
		System.out.println("\n");
		
		/**
		 * Um outro exemplo de method reference que � muito esquisito.
		 * 
		 * 		Consumer<String> consumer = s -> System.out.println(s);
		 * 		Consumer<String> consumer = System.out::println;
		 * 
		 * Este exemplo mostra que o objeto cuja refer�ncia est� � esquerda do lambda pode ser passado
		 * como o argumento do m�todo que voc� est� chamando. Meio confuso, mas enfim!
		 * 
		 * Note que no primeiro exemplo o elemento � esquerda do method reference era uma classe enquanto
		 * que aqui � uma outra vari�vel (a out de System).
		 * 
		 * Isso termina em:
		 */
		palavras.forEach(System.out::println);
		
		// S� por curiosidade, o curso mostrava isso aqui na parte escrita:
		palavras.sort(String.CASE_INSENSITIVE_ORDER);
	}
	
}
