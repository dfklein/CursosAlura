package br.com.caelum.livraria.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

//Lembre-se de que sempre que você fizer um PhaseListener você deve cadastrá-lo no faces-config.xml
public class LogPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("FASE ACESSADA NO beforePhase: " + event.getPhaseId());
//		RESTORE_VIEW 1			--> recupera toda a árvore de componentes da sessão (não se esqueça que os componentes fazem parte da sessão do usuário)
//		APPLY_REQUEST_VALUES 2 	--> são recuperados os valores que constam na requisição e aplicados aos seus respectivos componentes
//								--> OBS: se seu componente tiver o atributo immediate="true" ele irá executar nesta fase o método chamado e pulará daqui para a fase 6 (ou seja: sem validar e sem atualizar o modelo)
//		PROCESS_VALIDATIONS 3 	--> executa todos os CONVERSORES e VALIDADORES dos componentes.
//								--> OBS: se os conversores e os validadores falharem, ele pula direto para a fase 6
//		UPDATE_MODEL_VALUES 4 	--> é a hora em que ele vai pegar os valores da requisição e efetivamente aplicá-los ao seu modelo (o objeto a que o componente aponta no managed bean)
//		INVOKE_APPLICATION 5 	--> é a hora em que a ação solicitada é efetivamente executada
//		RENDER_RESPONSE 6 		--> o JSF gera a resposta com a atualização da view.
		
	}

	@Override
	public PhaseId getPhaseId() {
		// Aqui você retorna quais fases do ciclo de vida dos componentes você estará escutando neste listener.
		return PhaseId.ANY_PHASE;
	}

}
