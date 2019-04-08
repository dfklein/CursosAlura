package br.com.caelum.livraria.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

//Lembre-se de que sempre que voc� fizer um PhaseListener voc� deve cadastr�-lo no faces-config.xml
public class LogPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("FASE ACESSADA NO beforePhase: " + event.getPhaseId());
//		RESTORE_VIEW 1			--> recupera toda a �rvore de componentes da sess�o (n�o se esque�a que os componentes fazem parte da sess�o do usu�rio)
//		APPLY_REQUEST_VALUES 2 	--> s�o recuperados os valores que constam na requisi��o e aplicados aos seus respectivos componentes
//								--> OBS: se seu componente tiver o atributo immediate="true" ele ir� executar nesta fase o m�todo chamado e pular� daqui para a fase 6 (ou seja: sem validar e sem atualizar o modelo)
//		PROCESS_VALIDATIONS 3 	--> executa todos os CONVERSORES e VALIDADORES dos componentes.
//								--> OBS: se os conversores e os validadores falharem, ele pula direto para a fase 6
//		UPDATE_MODEL_VALUES 4 	--> � a hora em que ele vai pegar os valores da requisi��o e efetivamente aplic�-los ao seu modelo (o objeto a que o componente aponta no managed bean)
//		INVOKE_APPLICATION 5 	--> � a hora em que a a��o solicitada � efetivamente executada
//		RENDER_RESPONSE 6 		--> o JSF gera a resposta com a atualiza��o da view.
		
	}

	@Override
	public PhaseId getPhaseId() {
		// Aqui voc� retorna quais fases do ciclo de vida dos componentes voc� estar� escutando neste listener.
		return PhaseId.ANY_PHASE;
	}

}
