package br.com.alura;

public class C01ObjetoClassEInstanciando {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Pessoa p = new Pessoa();
		
		// Maneiras de obter uma classe para trabalhar com reflection
		// Lembre-se de que isso não são instâncias da classe Pessoa, mas sim de Class.
		Class<Pessoa> classClass = Pessoa.class;
		Class<? extends Pessoa> classGetClass = p.getClass();
		Class<?> classForName = Class.forName("br.com.alura.Pessoa");
		
		// o new instance foi depreciado a partir do JDK9. Na aula 2 será explicado o motivo e a alternativa.
		Object newInstance = classForName.newInstance();
		
		// Como a variável classForName não é parametrizada, o newInstance não pode retornar um tipo Pessoa sem um cast.
		// Já com a classClass ou a cassGetClass isso é possível (pois são parametrizadas).
		Pessoa newInstance2 = classClass.newInstance();
		Pessoa newInstance3 = classGetClass.newInstance();
		
		System.out.println("fim");
		
	}
}

