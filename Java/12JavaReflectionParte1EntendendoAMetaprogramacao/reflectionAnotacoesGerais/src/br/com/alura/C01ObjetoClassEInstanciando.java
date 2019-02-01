package br.com.alura;

public class C01ObjetoClassEInstanciando {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Pessoa p = new Pessoa();
		
		// Maneiras de obter uma classe para trabalhar com reflection
		// Lembre-se de que isso n�o s�o inst�ncias da classe Pessoa, mas sim de Class.
		Class<Pessoa> classClass = Pessoa.class;
		Class<? extends Pessoa> classGetClass = p.getClass();
		Class<?> classForName = Class.forName("br.com.alura.Pessoa");
		
		// o new instance foi depreciado a partir do JDK9. Na aula 2 ser� explicado o motivo e a alternativa.
		Object newInstance = classForName.newInstance();
		
		// Como a vari�vel classForName n�o � parametrizada, o newInstance n�o pode retornar um tipo Pessoa sem um cast.
		// J� com a classClass ou a cassGetClass isso � poss�vel (pois s�o parametrizadas).
		Pessoa newInstance2 = classClass.newInstance();
		Pessoa newInstance3 = classGetClass.newInstance();
		
		System.out.println("fim");
		
	}
}

