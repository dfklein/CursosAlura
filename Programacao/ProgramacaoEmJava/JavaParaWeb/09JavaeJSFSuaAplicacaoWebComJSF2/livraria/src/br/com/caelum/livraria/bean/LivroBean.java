package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped // Este escopo faz a inst�ncia do MB durar o tempo de dura��o da View, e n�o apenas a requisi��o
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;

	private Integer livroId;

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}
	   
	public void carregaPelaId() {
		this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livroId);
	}
	
	public Livro getLivro() {
		return livro;
	}
	
	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("autor",  new FacesMessage("Livro deve ter pelo menos um Autor"));
            return;
        } else {
        	if (livro.getId() == null) {
        		new DAO<Livro>(Livro.class).adiciona(this.livro);
        	} else {
        		new DAO<Livro>(Livro.class).atualiza(this.livro);
        	}
        	
            this.livro = new Livro();
        }
	}
	
	public void carregar(Livro livro) {
		System.out.println("Alterando livro");
		this.livro = livro;
	}
	
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		new DAO<Livro>(Livro.class).remove(livro);
	}
	
	public void removerAutor(Autor autor) {
		System.out.println("Removendo autor");
		this.livro.removeAutor(autor);
	}
	
	public String formAutor() {
		System.out.println("formAutor()");
		return "autor?faces-redirect=true";
	}
	public void gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public List<Autor> getAutoresDoLivro() {
        return this.livro.getAutores();
    }
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

	    String valor = value.toString();
	    if (!valor.startsWith("1")) {
	        throw new ValidatorException(new FacesMessage("Deveria come�ar com 1"));
	    }
	}
	
	public List<Livro> getLivros() {
		  return new DAO<Livro>(Livro.class).listaTodos();
		}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
}
