package br.com.caelum.financas.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String profissao;
	private String endereco;
	
	// O OneToOne apenas mapeia a rela��o de um para um entre as duas entidades.
	// Neste exemplo o instrutor definiu que um cliente s� pode ter uma conta e que uma conta s� pode pertencer a um cliente
	// Por este motivo ele anotou o JoinColumn como unique (ou seja: dos dois lados do relacionamento a coluna s� aceitar� uma ocorr�ncia do valor ID que referencia o outro lado do relacionamento)
	// IMPORTANTE: o unique s� funciona na cria��o do Schema, ou seja: inseri-lo j� em funcionamento n�o funciona (se a tabela j� existir voc� precisa dropar ela)
	@JoinColumn(unique=true)
	@OneToOne 
	private Conta conta;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	
}
