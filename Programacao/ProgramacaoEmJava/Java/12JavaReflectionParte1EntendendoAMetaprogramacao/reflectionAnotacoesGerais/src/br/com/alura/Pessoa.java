package br.com.alura;

public class Pessoa {

	private String nome;
	private int idade;
	
	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + ", idade=" + idade + "]";
	}
	
	public Pessoa() {
		super();
	}
	
	public Pessoa(boolean b) throws Exception {
		throw new Exception();
	}
	
	public Pessoa(String nome, int idade) {
		super();
		this.nome = nome;
		this.idade = idade;
	}
	
	private Pessoa(String nome) {
		
	}
	
	protected Pessoa(int idade) {
		
	}
	
	private void metodoPrivado() {
		System.out.println("m�todo privado");
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
}
