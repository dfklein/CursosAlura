package br.com.caelum.timer;

import java.util.Date;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
//Para fazer um timer voc� deve execut�-lo dentro de um @Singleton,
//uma vez que voc� n�o deseja que um mesmo timer seja configurado
//diversas vezes pela aplica��o (se voc� anotasse com @Stateless, 
//diversos session beans seriam criados no pool)
@Singleton
public class Agendador {

//	@Schedule(hour="9,18") // chama o timer todos os dias �s 9 e �s 18h
//	@Schedule(hour="*", minute="1,30,59") // chama nos minutos 1, 30 e 59 de cada hora
	@Schedule(hour="*", minute="*", second="*/10", persistent=false) // chama o timer a cada 10 segundos
	void agendado() {
		System.out.println("Servi�o de timer ativado �s: " + new Date());
	}
	
}
