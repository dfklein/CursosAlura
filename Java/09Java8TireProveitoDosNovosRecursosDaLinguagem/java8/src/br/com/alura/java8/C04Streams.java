package br.com.alura.java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

public class C04Streams {

	public static void main(String[] args) {
		List<Curso> cursos = new ArrayList<Curso>();
		cursos.add(new Curso("Python", 45));
		cursos.add(new Curso("JavaScript", 150));
		cursos.add(new Curso("Java 8", 113));
		cursos.add(new Curso("C", 55));
		
		cursos.sort(Comparator.comparing(Curso::getNome));
		System.out.println("Foi criada e ordenada por nome esta lista: ");
		cursos.forEach(c -> System.out.println(c.getNome() + " - " + c.getAlunos()));
		
		System.out.println("\n");
		
		// Filtro: somente cursos com 100 alunos ou mais.
		cursos.stream()
			.filter(c -> c.getAlunos() >= 100);
		
		// Quando voc� utiliza uma stream ela trabalha sobre um novo objeto sem impactar a lista original.
		// Se voc� executar a linha abaixo, ver� que o filtro N�O fez efeito:
		System.out.println("Lendo os nomes da lista depois de filtrar a stream (provando que isto n�o afeta a lista original):");
		cursos.forEach(c -> System.out.println(c.getNome() + " - " + c.getAlunos()));
		
		System.out.println("\n");
		
		// As streams tamb�m possuem um forEach:
		System.out.println("Iterando a partir de um forEach da pr�pria stream e fazendo o filtro funcionar:");
		cursos.stream()
			.filter(c -> c.getAlunos() >= 100)
			.forEach(c -> System.out.println(c.getNome() + " - " + c.getAlunos()));
		
		System.out.println("\n");
		
		System.out.println("Mapeando o n�mero de alunos de cada curso retornando um objeto Integer:");
		cursos.stream()
			.filter(c -> c.getAlunos() >= 100)
			.map(Curso::getAlunos)
			.forEach(c -> System.out.println(c + " - " + c.getClass()));
		
		System.out.println("\n");
		
		System.out.println("Mapeando o n�mero de alunos como tipo primitivo e usando m�todos espec�ficos:");
		
		/**
		 * Quando voc� retorna um tipo primitivo (como o mapToInt) voc� possui m�todos espec�ficos que
		 * permitem voc� trabalhar com eles (no exemplo acima em que voc� mapeou como objeto Integer
		 * isto n�o existe pois � uma stream de Object.
		 */
		int sum = cursos.stream()
			.mapToInt(Curso::getAlunos)
			.sum();
		
		OptionalDouble avg = cursos.stream()
			.mapToInt(Curso::getAlunos)
			.average();
		
		long count = cursos.stream()
			.count();
		
		System.out.println("soma de alunos: " + sum + " | m�dia de alunos por curso: " + avg.getAsDouble() + " | quantidade de cursos contabilizados na Stream: " + count);
		
		System.out.println("\n");
		
		/**
		 * Estes m�todos de stream que trabalham com tipos primitivos s�o interessantes em termos de
		 * performance pois ele sabe trabalhar sem realizar tantas opera��es de boxing e unboxing.
		 * 
		 * Abaixo um m�todo espec�fico de ordena��o em que isso seria v�lido
		 */
		System.out.println("Ordenando por qtd de alunos com um m�todo espec�fico para tipo primitivos:");
		cursos.sort(Comparator.comparingInt(Curso::getAlunos));
		cursos.forEach(c -> System.out.println(c.getNome() + " - " + c.getAlunos()));
	}
}

class Curso {
    private String nome;
    private int alunos;

    public Curso(String nome, int alunos) {
        this.nome = nome;
        this.alunos = alunos;
    }

    public String getNome() {
        return nome;
    }

    public int getAlunos() {
        return alunos;
    }
}
