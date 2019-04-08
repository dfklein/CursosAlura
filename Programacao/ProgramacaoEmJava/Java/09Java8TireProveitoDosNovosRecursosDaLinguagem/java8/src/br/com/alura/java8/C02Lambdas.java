package br.com.alura.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class C02Lambdas {

	public static void main(String[] args) {
		
		List<String> palavras = new ArrayList<>();
		palavras.add("alura online");
		palavras.add("editora casa do código");
		palavras.add("caelum");
		
		/**
		 * Para entender o lambda, é preciso lembrar que normalmente você faria 
		 * uma implementação anônima em um Consumer que não será reaproveitado em outra parte
		 * do código:
		 */
		System.out.println("--- Laço utilizando uma implementação anônima de Consumer");
		palavras.forEach(new Consumer<String>() {

			@Override
			public void accept(String s) {
				System.out.println(s);
				
			}
		});
		
		System.out.println("\n");
		
		/**
		 * O lambda é uma maneira de simplificar a sintaxe de um caso como o acima. A condição
		 * é que você esteja passando como a implementação de uma interface que possui UM ÚNICO
		 * MÉTODO ABSTRATO PARA IMPLEMENTAR (a interface Consumer só exige a implementação de accept(T t)
		 * 
		 * Este tipo de interface que possui apenas um método para implementar é chamada de INTERFACE FUNCIONAL.
		 */
		System.out.println("--- Laço utilizando lambda expression");
		palavras.forEach((s) -> System.out.println(s));
		// pode ser sem os parênteses se o método implementado possuir um único argumento
		// ** palavras.forEach(s -> System.out.println(s));
		// pode declarar o tipo do argumento
		// ** palavras.forEach((String s) -> System.out.println(s));
		// se o método forEach recebece mais de um argumento:
		// ** palavras.forEach((s, t) -> System.out.println(s + t));
		// se o método forEach não recebece argumentos:
		// ** palavras.forEach(() -> System.out.println("Faz de conta que funciona"));
		
		// Lembre-se de que o que você está fazendo já é uma evolução do seguinte:
		// ** Consumer<String> consumer = s -> System.out.println(s);
		// ** palavras.forEach(consumer);
		
		System.out.println("\n");
		
		/**
		 * Exemplo de comparator com lambda
		 * 
		 * Lembre que as chaves, o "return" e o ";" são opcionais neste caso em que a linha do retorno é uma declaração única 
		 */
		System.out.println("--- Sort com lambda expression");
		palavras.sort((s1, s2) -> {  return Integer.compare(s1.length(), s2.length());  });
		System.out.println(palavras);
		
	}
}
