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
			
			ExternalContext ectx = ctx.getExternalContext(); // isto lhe permite acessar o seu contexto a n�vel de Servlets
			Map<String, Object> sessionMap = ectx.getSessionMap(); // obtem o mapa de objetos que est�o associados � sess�o.
			
			// Desta forma voc� armazenou o usuario na sess�o.
			// Nesta aplica��o foi feito um PhaseListener que vai verificar se este usu�rio est� mesmo logado ou n�o.
			sessionMap.put("usuarioLogado", u);
			
			// OBS: no _template.xhtml h� um exemplo de como acessar um atributo da sess�o via EL
			
			return "livro?faces-redirect=true";
			
		} else {
			// desta forma voc� est� associando a mensagem de erro ao componente de mensgens que estiver associado ao input em quest�o.
			// No entanto, se voc� tiver um componente de mensagens na p�gina, ele tamb�m vai exibir esta mensagem (neste caso, exibindo-a em dois lugares distintos)
			// Veja o componente messages do login.xhtml onde � declarado o globalOnly para n�o exibir msgs espec�ficas de campos.
			ctx.addMessage("login:email", new FacesMessage("Usu�rio ou senha inv�lidos"));
			ctx.addMessage(null, new FacesMessage("Usu�rio ou senha inv�lidos - exibida globalmente")); // desta forma voc� apenas lan�ou a mensagem para o componente messages
			
			// Desta maneira voc� coloca as mensagens no escopo do flash. O flash tem vida por duas requisi��es, ou seja: mesmo com o redirect o objeto continua atribu�do � requisi��o
			ctx.getExternalContext().getFlash().setKeepMessages(true);
			
			// Como voc� est� fazendo um redirect aqui, voc� precisa colocar os objetos contendo as msgs no atributo flash
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
