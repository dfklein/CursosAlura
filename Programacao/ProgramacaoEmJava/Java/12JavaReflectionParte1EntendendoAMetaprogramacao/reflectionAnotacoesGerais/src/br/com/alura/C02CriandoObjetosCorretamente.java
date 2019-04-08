package br.com.alura;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * O motivo porque o m�todo newInstance() foi depreciado � porque ele n�o tem como prever
 * exce��es checadas dos construtores das classes invocadas. Por esse motivo a partir da
 * JDK 9 isso � feito de outra forma, evocando um objeto que representa os construtores
 * da classe.
 * 
 * Este objeto permite:
 * - listar todas as exce��es que podem ser lan�adas do construtor que estivermos representando
 * - listar todos os tipos de par�metros do construtor que estivermos representando
 * - perguntar quantos s�o os par�metros que aquele construtor recebe
 * - criar uma inst�ncia da classe a qual aquele construtor pertence (que � justamente o que precisamos para substituir o m�todo newInstance() da classe Class<T>
 */
public class C02CriandoObjetosCorretamente {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Class<Pessoa> pessoaClass = Pessoa.class;
		
		System.out.println("");
		System.out.println("********* OBTENDO CONSTRUTORES *********");
		System.out.println("");
		
		System.out.println("- Obtendo um array com todos os construtores P�BLICOS de uma classe e iterando sobre eles: ");
		Constructor<?>[] constructors = pessoaClass.getConstructors();
		Arrays.asList(constructors).forEach(c -> System.out.println(c));
		
		System.out.println("");
		
		System.out.println("- Obtendo um array com todos os construtores COM QUALQUER MODIFICADOR de uma classe e iterando sobre eles: ");
		Constructor<?>[] declaredConstructors = pessoaClass.getDeclaredConstructors();
		Arrays.asList(declaredConstructors).forEach(c -> System.out.println(c));

		System.out.println("");
		
		System.out.println("- Obtendo um construtor com argumentos:");
		Constructor<Pessoa> constructor = pessoaClass.getConstructor(String.class, Integer.TYPE);
		System.out.println(constructor);
		
		System.out.println("");
		
		System.out.println("- Obtendo um construtor PRIVADO com argumentos:");
		Constructor<Pessoa> declaredConstructor = pessoaClass.getDeclaredConstructor(String.class);
		System.out.println(declaredConstructor);
		
		System.out.println("");
		System.out.println("********* INSTANCIANDO CLASSES *********");
		System.out.println("");
		
		Constructor<Pessoa> construtorSemArgumentos = pessoaClass.getConstructor();
		Pessoa p1 = construtorSemArgumentos.newInstance();
		System.out.println("Pessoa sem args: " + p1);
		Constructor<Pessoa> construtorComArgumentos = pessoaClass.getConstructor(String.class, Integer.TYPE);
		Pessoa p2 = construtorComArgumentos.newInstance("Nome", 2);
		System.out.println("Pessoa com args: " + p2);
		Constructor<Pessoa> construtorPrivado = pessoaClass.getDeclaredConstructor(String.class);
		// Voc� pode indicar � VM que quer ter acesso a um construtor privado, mas algumas VMs n�o aceitam.
		construtorPrivado.setAccessible(true);
		Pessoa p3 = construtorPrivado.newInstance("Nome privado");
		System.out.println("Pessoa com construtor privado: " + p3);
		
		System.out.println("");
		System.out.println("********* CONSTRUTORES QUE LAN�AM EXCE��ES CHECADAS *********");
		System.out.println("");
		
		Constructor<Pessoa> constructorExcecao = pessoaClass.getConstructor(Boolean.TYPE);
		
		System.out.println("- Usando o newInstance de Class em um construtor que lan�a exce��o checada e dando errado:");
		Class<PessoaConstrutorExcecao> pessoaConstrutorExcecao = PessoaConstrutorExcecao.class;
		
		try {
			pessoaConstrutorExcecao.newInstance();
		} catch (Exception e) {
			System.out.println("Exce��o lan�ada pelo construtor da classe usando newInstance() de Class: " + e.getClass().getName());
		}
		
		// O que acontece � que o new Instance() de Constructor lan�a uma exce��o checada InvocationTargetException
		try {
			pessoaConstrutorExcecao.getConstructor().newInstance();
		} catch (InvocationTargetException e) {
			System.out.println("Exce��o lan�ada por um construtor usando newInstance() de Constructor: " + e.getClass().getName() + " / causa: " + e.getTargetException().getClass().getName());
		}
		System.out.println("");
		
	}
	
}
