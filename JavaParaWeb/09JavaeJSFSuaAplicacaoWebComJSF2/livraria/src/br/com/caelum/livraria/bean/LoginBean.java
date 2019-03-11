package br.com.caelum.livraria.bean;

import java.util.Map;

import javax.faces.application.FacesMessage;
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
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		if(u != null) {
			
			ExternalContext ectx = ctx.getExternalContext(); // isto lhe permite acessar o seu contexto a nível de Servlets
			Map<String, Object> sessionMap = ectx.getSessionMap(); // obtem o mapa de objetos que estão associados à sessão.
			
			// Desta forma você armazenou o usuario na sessão.
			// Nesta aplicação foi feito um PhaseListener que vai verificar se este usuário está mesmo logado ou não.
			sessionMap.put("usuarioLogado", u);
			
			// OBS: no _template.xhtml há um exemplo de como acessar um atributo da sessão via EL
			
			return "livro?faces-redirect=true";
			
		} else {
			// desta forma você está associando a mensagem de erro ao componente de mensgens que estiver associado ao input em questão.
			// No entanto, se você tiver um componente de mensagens na página, ele também vai exibir esta mensagem (neste caso, exibindo-a em dois lugares distintos)
			// Veja o componente messages do login.xhtml onde é declarado o globalOnly para não exibir msgs específicas de campos.
			ctx.addMessage("login:email", new FacesMessage("Usuário ou senha inválidos"));
			ctx.addMessage(null, new FacesMessage("Usuário ou senha inválidos - exibida globalmente")); // desta forma você apenas lançou a mensagem para o componente messages
			
			// Desta maneira você coloca as mensagens no escopo do flash. O flash tem vida por duas requisições, ou seja: mesmo com o redirect o objeto continua atribuído à requisição
			ctx.getExternalContext().getFlash().setKeepMessages(true);
			
			// Como você está fazendo um redirect aqui, você precisa colocar os objetos contendo as msgs no atributo flash
			return "login?faces-redirect=true";
		}
		
		
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "livro?faces-redirect=true";
	}
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
