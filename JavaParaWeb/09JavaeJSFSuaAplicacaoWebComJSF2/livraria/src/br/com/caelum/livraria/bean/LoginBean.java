package br.com.caelum.livraria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();
	
	public String efetuarLogin() {
		System.out.println("Efetuando login de: " + usuario.getEmail());
		DAO<Usuario> dao = new DAO<Usuario>(Usuario.class);
		Usuario u = dao.buscarUsuario(usuario);
		if(u != null) {
			return "livro?faces-redirect-true";
		} else {
			usuario = new Usuario();
			return null;
		}
		
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
