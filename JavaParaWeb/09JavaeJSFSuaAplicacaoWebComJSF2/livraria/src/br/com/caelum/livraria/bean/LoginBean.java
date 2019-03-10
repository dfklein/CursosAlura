package br.com.caelum.livraria.bean;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
			
			FacesContext ctx = FacesContext.getCurrentInstance();
			ExternalContext ectx = ctx.getExternalContext(); // isto lhe permite acessar o seu contexto a nível de Servlets
			Map<String, Object> sessionMap = ectx.getSessionMap(); // obtem o mapa de objetos que estão associados à sessão.
			
			// Desta forma você armazenou o usuario na sessão.
			// Nesta aplicação foi feito um PhaseListener que vai verificar se este usuário está mesmo logado ou não.
			sessionMap.put("usuarioLogado", u);
			
			// OBS: no _template.xhtml há um exemplo de como acessar um atributo da sessão via EL
			
			return "livro?faces-redirect=true";
			
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
