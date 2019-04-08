
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
	@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // guarda o cache APENAS DOS IDS DOS RELACIONAMENTOS, não as entidades.
//	@JoinTable(name="R_CATEGORIA_PRODUTO",
//			joinColumns = @JoinColumn(name = "id_produto"),
//	        inverseJoinColumns = @JoinColumn(name = "id_categoria")
//	)
	List<Categoria> categorias = new ArrayList<>();
	
	@Valid
	@ManyToOne
	private Loja loja;
	
	// O atributo anotado com @Version é usado para guardar uma versão do objeto e gerar lock otimista
	// Quando dois usuários tentam editar um mesmo registro ao mesmo tempo mas 
	// um submete a alteração antes do segundo, o primeiro está trabalhando com uma versão anterior do
	// registro e ao atualizá-lo acaba sobrescrevendo a alteração que já tinha sido submetida.
	// O optmistic lock trava a segunda alteração por saber que aquela versão que o segundo usuário 
	// tem na tela não é mais válida, lançando uma exceção quando a segunda alteração for feita. 
	@Version
	private int versao;
	
	
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	//mÃ©todo auxiliar para associar categorias com o produto
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
