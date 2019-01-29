package br.com.alura.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class C02Lambdas {

	public static void main(String[] args) {
		
		List<String> palavras = new ArrayList<>();
		palavras.add("alura online");
		palavras.add("editora casa do c�digo");
		palavras.add("caelum");
		
		/**
		 * Para entender o lambda, � preciso lembrar que normalmente voc� faria 
		 * uma implementa��o an�nima em um Consumer que n�o ser� reaproveitado em outra parte
		 * do c�digo:
		 */
		System.out.println("--- La�o utilizando uma implementa��o an�nima de Consumer");
		palavras.forEach(new Consumer<String>() {

			@Override
			public void accept(String s) {
				System.out.println(s);
				
			}
		});
		
		System.out.println("\n");
		
		/**
		 * O lambda � uma maneira de simplificar a sintaxe de um caso como o acima. A condi��o
		 * � que voc� esteja passando como a implementa��o de uma interface que possui UM �NICO
		 * M�TODO ABSTRATO PARA IMPLEMENTAR (a interface Consumer s� exige a implementa��o de accept(T t)
		 * 
		 * Este tipo de interface que possui apenas um m�todo para implementar � chamada de INTERFACE FUNCIONAL.
		 */
		System.out.println("--- La�o utilizando lambda expression");
		palavras.forEach((s) -> System.out.println(s));
		// pode ser sem os par�nteses se o m�todo implementado possuir um �nico argumento
		// ** palavras.forEach(s -> System.out.println(s));
		// pode declarar o tipo do argumento
		// ** palavras.forEach((String s) -> System.out.println(s));
		// se o m�todo forEach recebece mais de um argumento:
		// ** palavras.forEach((s, t) -> System.out.println(s + t));
		// se o m�todo forEach n�o recebece argumentos:
		// ** palavras.forEach(() -> System.out.println("Faz de conta que funciona"));
		
		// Lembre-se de que o que voc� est� fazendo j� � uma evolu��o do seguinte:
		// ** Consumer<String> consumer = s -> System.out.println(s);
		// ** palavras.forEach(consumer);
		
		System.out.println("\n");
		
		/**
		 * Exemplo de comparator com lambda
		 * 
		 * Lembre que as chaves, o "return" e o ";" s�o opcionais neste caso em que a linha do retorno � uma declara��o �nica 
		 */
		System.out.println("--- Sort com lambda expression");
		palavras.sort((s1, s2) -> {  return Integer.compare(s1.length(), s2.length());  });
		System.out.println(palavras);
		
	}
}
