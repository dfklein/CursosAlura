package br.com.alura.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class C05StreamCollectorsAPI {

	public static void main(String[] args) {
		
		List<Curso> cursos = new ArrayList<Curso>();
		cursos.add(new Curso("Python", 45));
		cursos.add(new Curso("JavaScript", 150));
		cursos.add(new Curso("Java 8", 113));
		cursos.add(new Curso("C", 55));
		
		/**
		 * O Optional é uma classe nova que te ajuda a trabalhar com um resultado que seja null.
		 * Assim evita que você precise utilizar vários if ou try catch só para verificar se o
		 * trabalho em uma stream possui resultado ou não.
		 * 
		 * Por exemplo: você vai filtrar todos os cursos com mais de X alunos, mas não tem certeza
		 * de se isto retornará algum resultado (pode ser que nenhum curso tenha isso)
		 */
		Optional<Curso> cursoOptional = cursos.stream().filter(c -> c.getAlunos() > 1000).findAny();
		
		/**
		 * get(): retorna o curso, mas se ele não for encontrado você vai tomar exception.
		 */
		try {
			Curso cursoUm = cursoOptional.get();
			System.out.println("Com método get(): " + cursoUm);
		} catch (Exception e) {
			System.out.println("método get() lançou a exceção: " + e.getClass().getName());
		}
		
		/**
		 * Devolve o curso. Caso ele não exista devolve o que for passado como parâmetro.
		 */
		Curso cursoDois = cursoOptional.orElse(null);
		Curso cursoTres = cursoOptional.orElse(new Curso("Novo curso", 0));
		
		System.out.println("orElse null: " + cursoDois);
		System.out.println("orElse new Curso(): " + cursoTres);
		
		/**
		 * O ifPresent() é bem óbvio pelo nome:
		 */
		cursoOptional.ifPresent(c -> System.out.println("Havia curso no resultado da Stream: " + c));
		
		
		/**
		 * OBTER COLLECTIONS
		 * 
		 * O método collect recebe um objeto do tipo Collector.
		 * 
		 * A classe Collectors possui uma série de métodos estáticos que fazem esta transformação
		 */
		List<Curso> alunosToList = cursos.stream()
			.filter(c -> c.getAlunos() > 100)
			.collect(Collectors.toList());
		
		/**
		 * Por exemplo: você pode criar um mapa:
		 */
		Map<String, Integer> alunosToMap = cursos.stream()
			.filter(c -> c.getAlunos() > 100)
			.collect(Collectors.toMap(
				c -> c.getNome(),
				c -> c.getAlunos()));
		
		System.out.println("\n");
		
		/**
		 * O mapa tem um forEach que recebe um biconsumidor:
		 */
		System.out.println("Usando o forEach de map:");
		cursos.stream()
			.filter(c -> c.getAlunos() > 100)
			.collect(Collectors.toMap(
				c -> c.getNome(),
				c -> c.getAlunos()))
			.forEach((nome, alunos) -> { System.out.println("nome: " + nome + " / alunos: " + alunos); });;
		
		/**
		 * Lembrando que para mapas você tem o groupingBy que ele não falou no curso 
		 * mas é bem legal:
		 */
		Map<String, List<Curso>> alunosGroupBy = cursos.stream()
			.filter(c -> c.getAlunos() > 100)
			.collect(Collectors.groupingBy(
				c -> c.getNome()));
		
		/**
		 * Tem um negócio MUITO legal que é o parallelStream(). Você usa do mesmo jeito que 
		 * qualquer stream. A diferença é que ela vai quebrar as suas operações em diversas
		 * threads.
		 * 
		 * Isto melhora a performance para o processamento de um volume muito grande de 
		 * dados. Com poucos dados não é interessante (pode deixar o processo até mais lento) 
		 */
		Map<String, List<Curso>> alunosGroupByParallelStream = cursos.parallelStream()
		.filter(c -> c.getAlunos() > 100)
		.collect(Collectors.groupingBy(
			c -> c.getNome()));
		
	}
	
}

