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
		
		// Quando você utiliza uma stream ela trabalha sobre um novo objeto sem impactar a lista original.
		// Se você executar a linha abaixo, verá que o filtro NÃO fez efeito:
		System.out.println("Lendo os nomes da lista depois de filtrar a stream (provando que isto não afeta a lista original):");
		cursos.forEach(c -> System.out.println(c.getNome() + " - " + c.getAlunos()));
		
		System.out.println("\n");
		
		// As streams também possuem um forEach:
		System.out.println("Iterando a partir de um forEach da própria stream e fazendo o filtro funcionar:");
		cursos.stream()
			.filter(c -> c.getAlunos() >= 100)
			.forEach(c -> System.out.println(c.getNome() + " - " + c.getAlunos()));
		
		System.out.println("\n");
		
		System.out.println("Mapeando o número de alunos de cada curso retornando um objeto Integer:");
		cursos.stream()
			.filter(c -> c.getAlunos() >= 100)
			.map(Curso::getAlunos)
			.forEach(c -> System.out.println(c + " - " + c.getClass()));
		
		System.out.println("\n");
		
		System.out.println("Mapeando o número de alunos como tipo primitivo e usando métodos específicos:");
		
		/**
		 * Quando você retorna um tipo primitivo (como o mapToInt) você possui métodos específicos que
		 * permitem você trabalhar com eles (no exemplo acima em que você mapeou como objeto Integer
		 * isto não existe pois é uma stream de Object.
		 */
		int sum = cursos.stream()
			.mapToInt(Curso::getAlunos)
			.sum();
		
		OptionalDouble avg = cursos.stream()
			.mapToInt(Curso::getAlunos)
			.average();
		
		long count = cursos.stream()
			.count();
		
		System.out.println("soma de alunos: " + sum + " | média de alunos por curso: " + avg.getAsDouble() + " | quantidade de cursos contabilizados na Stream: " + count);
		
		System.out.println("\n");
		
		/**
		 * Estes métodos de stream que trabalham com tipos primitivos são interessantes em termos de
		 * performance pois ele sabe trabalhar sem realizar tantas operações de boxing e unboxing.
		 * 
		 * Abaixo um método específico de ordenação em que isso seria válido
		 */
		System.out.println("Ordenando por qtd de alunos com um método específico para tipo primitivos:");
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
