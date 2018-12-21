package br.com.casadocodigo.loja.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String titulo;
	
	@Lob
	private String descricao;
	
	private Integer paginas;
	
	// Esta anotação não é do Spring, mas da JPA. Ela adiciona o preço como parte do produto,
	// em uma nova tabela já criando o relacionamento entre eles. Ele se diferencia do @OneToMany
	// por manter uma relação de exclusividade do outros lado: os preços relacionados a este produto
	// são exclusivos, não podendo ser atribuídos a outros produtos. Veja como ele foi mapeado na
	// classe Preco para saber como utilizá-lo.
	@ElementCollection
	private List<Preco> precos;
	

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao.trim();
	}

	public Integer getPaginas() {
		return paginas;
	}

	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<Preco> getPrecos() {
        return precos;
    }

    public void setPrecos(List<Preco> precos) {
        this.precos = precos;
    }

	@Override
	public String toString() {
		return "Produto [titulo=" + titulo + ", descricao=" + descricao + ", paginas=" + paginas + "]";
	}
	
	
}
