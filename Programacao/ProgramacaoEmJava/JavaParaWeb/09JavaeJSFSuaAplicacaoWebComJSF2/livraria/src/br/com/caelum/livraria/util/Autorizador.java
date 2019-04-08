package br.com.caelum.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.caelum.livraria.modelo.Usuario;

// Lembre-se de que sempre que voc� fizer um PhaseListener voc� deve cadastr�-lo no faces-config.xml
public class Autorizador implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		
		// Voc� precisa verificar em qual p�gina ele est� executando a verifica��o e, por isso, voc�
		// precisa fazer no afterPhase.
		FacesContext context = event.getFacesContext();
		UIViewRoot viewRoot = context.getViewRoot(); // devolve a �rvore de componentes.
		String nomePagina = viewRoot.getViewId();
		System.out.println("Nome da p�gina: " + nomePagina);
		
		if("/login.xhtml".equals(nomePagina)) {
			return;
		}
		
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		
		if(usuarioLogado != null) {
			return;
		}
		
		NavigationHandler h = context.getApplication().getNavigationHandler();
		// S� para saber: o segundo argumento seria o alias de um navigation-rule do faces-context.xml caso voc� estivesse usando isso.
		// No faces-context voc� colocou um navigation rule para ter de exemplo, mas na verdade isso � considerado um jeito meio arcaico de se fazer navega��o (heran�a do Struts).
		h.handleNavigation(context, null, "/login.xhtml?faces-redirect=true");
		
		// Lembre-se de que voc� est� atuando na fase do Restore View. Voc� precisa aqui definir que ele deve pular todas as fases do ciclo de vida, indo direto a ultima fase (render response)
		context.renderResponse();
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		// Aqui voc� retorna quais fases do ciclo de vida dos componentes voc� estar� escutando neste listener.
		return PhaseId.RESTORE_VIEW;
	}

}
