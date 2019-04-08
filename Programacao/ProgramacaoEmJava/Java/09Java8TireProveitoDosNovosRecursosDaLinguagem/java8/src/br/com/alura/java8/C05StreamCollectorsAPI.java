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
		 * O Optional � uma classe nova que te ajuda a trabalhar com um resultado que seja null.
		 * Assim evita que voc� precise utilizar v�rios if ou try catch s� para verificar se o
		 * trabalho em uma stream possui resultado ou n�o.
		 * 
		 * Por exemplo: voc� vai filtrar todos os cursos com mais de X alunos, mas n�o tem certeza
		 * de se isto retornar� algum resultado (pode ser que nenhum curso tenha isso)
		 */
		Optional<Curso> cursoOptional = cursos.stream().filter(c -> c.getAlunos() > 1000).findAny();
		
		/**
		 * get(): retorna o curso, mas se ele n�o for encontrado voc� vai tomar exception.
		 */
		try {
			Curso cursoUm = cursoOptional.get();
			System.out.println("Com m�todo get(): " + cursoUm);
		} catch (Exception e) {
			System.out.println("m�todo get() lan�ou a exce��o: " + e.getClass().getName());
		}
		
		/**
		 * Devolve o curso. Caso ele n�o exista devolve o que for passado como par�metro.
		 */
		Curso cursoDois = cursoOptional.orElse(null);
		Curso cursoTres = cursoOptional.orElse(new Curso("Novo curso", 0));
		
		System.out.println("orElse null: " + cursoDois);
		System.out.println("orElse new Curso(): " + cursoTres);
		
		/**
		 * O ifPresent() � bem �bvio pelo nome:
		 */
		cursoOptional.ifPresent(c -> System.out.println("Havia curso no resultado da Stream: " + c));
		
		
		/**
		 * OBTER COLLECTIONS
		 * 
		 * O m�todo collect recebe um objeto do tipo Collector.
		 * 
		 * A classe Collectors possui uma s�rie de m�todos est�ticos que fazem esta transforma��o
		 */
		List<Curso> alunosToList = cursos.stream()
			.filter(c -> c.getAlunos() > 100)
			.collect(Collectors.toList());
		
		/**
		 * Por exemplo: voc� pode criar um mapa:
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
		 * Lembrando que para mapas voc� tem o groupingBy que ele n�o falou no curso 
		 * mas � bem legal:
		 */
		Map<String, List<Curso>> alunosGroupBy = cursos.stream()
			.filter(c -> c.getAlunos() > 100)
			.collect(Collectors.groupingBy(
				c -> c.getNome()));
		
		/**
		 * Tem um neg�cio MUITO legal que � o parallelStream(). Voc� usa do mesmo jeito que 
		 * qualquer stream. A diferen�a � que ela vai quebrar as suas opera��es em diversas
		 * threads.
		 * 
		 * Isto melhora a performance para o processamento de um volume muito grande de 
		 * dados. Com poucos dados n�o � interessante (pode deixar o processo at� mais lento) 
		 */
		Map<String, List<Curso>> alunosGroupByParallelStream = cursos.parallelStream()
		.filter(c -> c.getAlunos() > 100)
		.collect(Collectors.groupingBy(
			c -> c.getNome()));
		
	}
	
}

