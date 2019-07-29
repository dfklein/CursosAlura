package br.com.caelum.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 			---------->   Fila Request   ---------> 
 * Producer                                              Consumer
 *          <----------   Fila Respone  <----------
 *          
 *  Crie duas novas filas pedidos.req e pedidos.res pela interface do ActiveMQ.
 *  
 *  O padrão JMS prevê esse cenário e ajuda pelo menos no envio da mensagem em si. Já que temos duas filas, a
 *   ideia é, ao enviar a mensagem, que ela leve a informação de qual fila é a Fila Response. Assim o consumer 
 *   sabe para onde devolver a mensagem (qual é a fila resposta).
 * 
 *  Quando você envia uma mensagem com ActiveMQ, já existem alguns cabeçalhos padrões e um desses é chamado de
 *   Reply to. Envie uma nova mensagem pela interface para a fila pedidos.req. Coloque o nome da fila de resposta
 *   no cabeçalho Reply to.
 *   
 *  Tudo isso foi possível por que o Camel detecta o cabeçalho Reply To na mensagem JMS e automaticamente devolve
 *   o retorno da rota para a fila pedidos.res. Para ser correto, o Camel muda o Exchange Pattern de InOnly para InOut
 */
public class RotaPedidoAula07InOut {

	public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));

        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
            	
            	from("activemq:queue:pedidos.req").
	                log("Pattern: ${exchange.pattern}"). // O Camel deve imprimir no console: Pattern: InOut
	                setBody(constant("Camel Rocks!")).
	                setHeader(Exchange.FILE_NAME, constant("mensagem.txt")).
                to("file:saida");
            }
        });

        context.start();
        Thread.sleep(20000);
        context.stop();
    }
}
