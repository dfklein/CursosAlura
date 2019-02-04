package br.com.alura;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class C03EvocandoMetodosPorReflection {

	public static void main(String[] args) {
		try {
//			Class<Pessoa> pessoaClass = Pessoa.class;
			Class<?> pessoaClass = Class.forName("br.com.alura.Pessoa");
			
			// ********** MANEIRAS DE OBTER OS M�TODOS  ********** //:
			System.out.println("*** - Obter todos os m�todos p�blicos");
			Method[] methodsPublic = pessoaClass.getMethods();
			Arrays.asList(methodsPublic).forEach(m -> System.out.println(m));
			
			System.out.println("\n");
			
			// Todos os m�todos com todos os modificadores mas considera apenas a classe que tipa
			// a vari�vel, sem nenhum m�todo de alguma superclasse dele.
			// Neste exemplo ele retornar� todos os m�todos declarados na classe Pessoa:
			System.out.println("*** - Obtendo todos os m�todos declarados na classe Pessoa:");
			Method[] methodsAll = pessoaClass.getDeclaredMethods();
			Arrays.asList(methodsAll).forEach(m -> System.out.println(m));
		
			System.out.println("\n");
			
			// Retorna apenas o m�todo com essa lista de argumentos que for p�blico. Voc� passa o nome do m�todo como primeiro argumento e depois uma lista ordenada de tipos que ele recebe como argumento.
			System.out.println("*** - Obtendo m�todos pelo seu nome:");
			Method methodGetNome = pessoaClass.getMethod("getNome");
			Method methodSetNome = pessoaClass.getMethod("setNome", String.class);
			System.out.println(methodGetNome);
			System.out.println(methodSetNome);
			
			System.out.println("\n");
			
			// Recuperando m�todos privados para manipular
			System.out.println("*** - Obtendo m�todos privados e habilitando-os para manipula��o:");
			Method metodoPrivado = pessoaClass.getDeclaredMethod("metodoPrivado");
			metodoPrivado.setAccessible(true); // seta ele para ser manipulado e true para a VM aceitar.
			
			System.out.println("\n");
			
			// Executando m�todos (serve para m�todos com qualquer modificador, evidentemente):
			// O primeiro argumento precisa ser um objeto que representa a inst�ncia da qual o m�todo em quest�o ser� invocado:
			System.out.println("*** - Evocando m�todos:");
			Constructor<?> c = pessoaClass.getConstructor(String.class, Integer.TYPE);
			Object newInstance = c.newInstance("Pedro", 22);
			Object retornoMetodoPrivado = metodoPrivado.invoke(newInstance);
			Object retornoMetodoGetNome = methodGetNome.invoke(newInstance);
			System.out.println(retornoMetodoPrivado);
			System.out.println(retornoMetodoGetNome);
			Object retornoMetodoSetNome = methodSetNome.invoke(newInstance, "Jo�o");
			System.out.println(retornoMetodoSetNome);
			System.out.println(methodGetNome.invoke(newInstance));
			
			
			
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		
	}
}
