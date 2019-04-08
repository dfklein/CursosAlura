package br.com.alura.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class C01MetodosDefaultDeInterfaces {

	public static void main(String[] args) {
		
		List<String> palavras = new ArrayList<>();
		palavras.add("alura online");
		palavras.add("editora casa do c�digo");
		palavras.add("caelum");
		
		Collections.sort(palavras);
		System.out.println("sort por ordem alfab�tica: ");
		System.out.println(palavras);
		
		System.out.println("\n");
		
		Collections.sort(palavras, new ComparadorPorTamanhoMenorMaior());
		System.out.println("sort por ordem de tamanho (Collections.sort): ");
		System.out.println(palavras);
		
		System.out.println("\n");
		
		/**
		 * A partir do Java 8 � poss�vel implementar m�todos concretos em interfaces. 
		 * 
		 * Para declarar m�todos em interfaces eles devem possuir o operador default, que indica
		 * que o m�todo n�o precisa ser implementado por classes que declaram esta interface.
		 * 
		 * As diferen�as entre um m�todo default e um static em uma interface s�o:
		 * 		1 - Um m�todo est�tico pertence apenas � interface, e n�o � classe que a implementa
		 * 		2 - M�todos est�ticos podem ser sobrescritos nas classes que implementam a interface.
		 * 
		 * O que permanece diferente entre uma interface e uma classe abstrata � que a interface
		 * nunca tem estado: ou seja, ela n�o possui atributos (pode possuir constantes, mas n�o
		 * atributos)
		 * 
		 * A interface List, por exemplo, passou a ter m�todos como o 
		 * replaceAll(UnaryOperator<E> operator), copyOf(Collection<? extends E> coll),  
		 * List.of() e o sort(Comparator c), dentre outros 
		 */
		palavras.sort(new ComparadorPorTamanhoMaiorMenor());
		System.out.println("sort por ordem de tamanho (usando o m�todo sort(Comparator c) da interface List: ");
		System.out.println(palavras);
		
		System.out.println("\n");
		
		System.out.println("Itera��o de lista com o enhanced for (anterior ao Java 8)");
		for (String string : palavras) {
			System.out.println(string);
		}
		
		System.out.println("\n");
		
		System.out.println("Itera��o de lista com o novo forEach(Consumer c)");
		palavras.forEach(new ImprimeNaLinha());
	}
}

class ComparadorPorTamanhoMenorMaior implements Comparator<String> {

	@Override
	public int compare(String s1, String s2) {
		// o compare deve retornar n�mero negativo caso o primeiro elemento venha antes do segundo, positivo se for o contr�rio, zero se der empate.
		return s1.length() - s2.length();
	}
	
}

class ComparadorPorTamanhoMaiorMenor implements Comparator<String> {
	
	@Override
	public int compare(String s1, String s2) {
		// o compare deve retornar n�mero negativo caso o primeiro elemento venha antes do segundo, positivo se for o contr�rio, zero se der empate.
		return s2.length() - s1.length();
	}
	
}

/**
 * Um consumer precisa implementar o m�todo accept, que diz o que a classe deve fazer com o objeto que
 * estiver consumindo  
 */
class ImprimeNaLinha implements Consumer<String> {

	@Override
	public void accept(String t) {
		System.out.println("Imprime linha: " + t);
		
	}
	
}
