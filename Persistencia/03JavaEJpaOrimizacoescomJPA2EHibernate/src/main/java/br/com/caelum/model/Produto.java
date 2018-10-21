
package br.com.caelum.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

@NamedEntityGraphs({
    @NamedEntityGraph(name = "produtoComCategoria", 
                      attributeNodes = { 
                            @NamedAttributeNode("categorias") 
                      }) 
})
@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty
	private String nome;
	@NotEmpty
	private String linkDaFoto;
	
	@NotEmpty
	@Column(columnDefinition="TEXT")
	private String descricao;
	
	@Min(20)
	private double preco;
	
	@ManyToMany
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // guarda o cache APENAS DOS IDS DOS RELACIONAMENTOS, n�o as entidades.
//	@JoinTable(name="R_CATEGORIA_PRODUTO",
//			joinColumns = @JoinColumn(name = "id_produto"),
//	        inverseJoinColumns = @JoinColumn(name = "id_categoria")
//	)
	List<Categoria> categorias = new ArrayList<>();
	
	@Valid
	@ManyToOne
	private Loja loja;
	
	// O atributo anotado com @Version � usado para guardar uma vers�o do objeto e gerar lock otimista
	// Quando dois usu�rios tentam editar um mesmo registro ao mesmo tempo mas 
	// um submete a altera��o antes do segundo, o primeiro est� trabalhando com uma vers�o anterior do
	// registro e ao atualiz�-lo acaba sobrescrevendo a altera��o que j� tinha sido submetida.
	// O optmistic lock trava a segunda altera��o por saber que aquela vers�o que o segundo usu�rio 
	// tem na tela n�o � mais v�lida, lan�ando uma exce��o quando a segunda altera��o for feita. 
	@Version
	private int versao;
	
	
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	//método auxiliar para associar categorias com o produto
	//se funcionar apos ter definido o relacionamento entre produto e categoria
	public void adicionarCategorias(Categoria... categorias) {
		for (Categoria categoria : categorias) {
			this.categorias.add(categoria);
		}
	}

	public String getLinkDaFoto() {
		return linkDaFoto;
	}
	
	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public void setLinkDaFoto(String linkDaFoto) {
		this.linkDaFoto = linkDaFoto;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Loja getLoja() {
		return loja;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public int getVersao() {
		return versao;
	}

	public void setVersao(int versao) {
		this.versao = versao;
	}
	
	
}
