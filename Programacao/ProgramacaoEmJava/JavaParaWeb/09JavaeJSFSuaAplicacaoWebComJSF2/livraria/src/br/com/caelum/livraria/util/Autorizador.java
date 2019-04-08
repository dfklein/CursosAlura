package br.com.caelum.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.modelo.Usuario;

// Lembre-se de que sempre que você fizer um PhaseListener você deve cadastrá-lo no faces-config.xml
public class Autorizador implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		
		// Você precisa verificar em qual página ele está executando a verificação e, por isso, você
		// precisa fazer no afterPhase.
		FacesContext context = event.getFacesContext();
		UIViewRoot viewRoot = context.getViewRoot(); // devolve a árvore de componentes.
		String nomePagina = viewRoot.getViewId();
		System.out.println("Nome da página: " + nomePagina);
		
		if("/login.xhtml".equals(nomePagina)) {
			return;
		}
		
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		
		if(usuarioLogado != null) {
			return;
		}
		
		NavigationHandler h = context.getApplication().getNavigationHandler();
		// Só para saber: o segundo argumento seria o alias de um navigation-rule do faces-context.xml caso você estivesse usando isso.
		// No faces-context você colocou um navigation rule para ter de exemplo, mas na verdade isso é considerado um jeito meio arcaico de se fazer navegação (herança do Struts).
		h.handleNavigation(context, null, "/login.xhtml?faces-redirect=true");
		
		// Lembre-se de que você está atuando na fase do Restore View. Você precisa aqui definir que ele deve pular todas as fases do ciclo de vida, indo direto a ultima fase (render response)
		context.renderResponse();
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		// Aqui você retorna quais fases do ciclo de vida dos componentes você estará escutando neste listener.
		return PhaseId.RESTORE_VIEW;
	}

}
