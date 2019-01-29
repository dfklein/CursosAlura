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
		palavras.add("editora casa do código");
		palavras.add("caelum");
		
		Collections.sort(palavras);
		System.out.println("sort por ordem alfabética: ");
		System.out.println(palavras);
		
		System.out.println("\n");
		
		Collections.sort(palavras, new ComparadorPorTamanhoMenorMaior());
		System.out.println("sort por ordem de tamanho (Collections.sort): ");
		System.out.println(palavras);
		
		System.out.println("\n");
		
		/**
		 * A partir do Java 8 é possível implementar métodos concretos em interfaces. 
		 * 
		 * Para declarar métodos em interfaces eles devem possuir o operador default, que indica
		 * que o método não precisa ser implementado por classes que declaram esta interface.
		 * 
		 * As diferenças entre um método default e um static em uma interface são:
		 * 		1 - Um método estático pertence apenas à interface, e não à classe que a implementa
		 * 		2 - Métodos estáticos podem ser sobrescritos nas classes que implementam a interface.
		 * 
		 * O que permanece diferente entre uma interface e uma classe abstrata é que a interface
		 * nunca tem estado: ou seja, ela não possui atributos (pode possuir constantes, mas não
		 * atributos)
		 * 
		 * A interface List, por exemplo, passou a ter métodos como o 
		 * replaceAll(UnaryOperator<E> operator), copyOf(Collection<? extends E> coll),  
		 * List.of() e o sort(Comparator c), dentre outros 
		 */
		palavras.sort(new ComparadorPorTamanhoMaiorMenor());
		System.out.println("sort por ordem de tamanho (usando o método sort(Comparator c) da interface List: ");
		System.out.println(palavras);
		
		System.out.println("\n");
		
		System.out.println("Iteração de lista com o enhanced for (anterior ao Java 8)");
		for (String string : palavras) {
			System.out.println(string);
		}
		
		System.out.println("\n");
		
		System.out.println("Iteração de lista com o novo forEach(Consumer c)");
		palavras.forEach(new ImprimeNaLinha());
	}
}

class ComparadorPorTamanhoMenorMaior implements Comparator<String> {

	@Override
	public int compare(String s1, String s2) {
		// o compare deve retornar número negativo caso o primeiro elemento venha antes do segundo, positivo se for o contrário, zero se der empate.
		return s1.length() - s2.length();
	}
	
}

class ComparadorPorTamanhoMaiorMenor implements Comparator<String> {
	
	@Override
	public int compare(String s1, String s2) {
		// o compare deve retornar número negativo caso o primeiro elemento venha antes do segundo, positivo se for o contrário, zero se der empate.
		return s2.length() - s1.length();
	}
	
}

/**
 * Um consumer precisa implementar o método accept, que diz o que a classe deve fazer com o objeto que
 * estiver consumindo  
 */
class ImprimeNaLinha implements Consumer<String> {

	@Override
	public void accept(String t) {
		System.out.println("Imprime linha: " + t);
		
	}
	
}
