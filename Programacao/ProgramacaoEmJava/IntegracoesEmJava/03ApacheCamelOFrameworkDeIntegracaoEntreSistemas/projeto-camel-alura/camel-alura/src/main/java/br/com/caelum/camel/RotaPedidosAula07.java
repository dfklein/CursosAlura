package br.com.caelum.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidosAula07 {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		
		// O apelido usado aqui ("activemq") é a String que define o nome do componente que será chamado no método from()
		// O segundo é o caminho do seu ActiveMQ na porta onde ele lê e recebe mensagens.
		context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				
				errorHandler(
					    deadLetterChannel("activemq:queue:pedidos.DLQ") 	// pedidos.DLQ é uma fila que você criou para receber erros.
					    													// O nome DLQ é um padrão e significa "dead letter queue"
					    	.logExhausted(true) 
					        .maximumRedeliveries(3)
					            .redeliveryDelay(3000)   
					            .onRedelivery(new Processor() { 

									@Override
									public void process(Exchange exchange) throws Exception {
										int count = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER);
										int max = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_MAX_COUNTER);
										System.out.println(String.format("Tentativa %d de %d", count, max));
										
									} 
					            	
					            }) 
					            
				);
				
				// Se fosse um tópico, seria topic. Como você registrou uma fila no ActiveMQ então e o queue
				from("activemq:queue:pedidos")
					.routeId("Rota principal")
					.to("validator:pedido.xsd")	
					 .multicast()
						 .to("direct:soap")
						 .to("direct:http");
				
				from("direct:http")
					.routeId("Rota http")
					.setProperty("pedidoId", xpath("/pedido/id/text()"))	
				    .setProperty("clienteId", xpath("/pedido/pagamento/email-titular/text()"))
				    .log("${body}")
					.split()
						.xpath("/pedido/itens/item")
					.filter()
						.xpath("/item/formato[text()='EBOOK']")
						.setProperty("ebookId", xpath("/item/livro/codigo/text()"))
					.marshal()							
						.xmljson() 						
					
					.setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
					.setHeader(Exchange.HTTP_QUERY, 
				            simple("clienteId=${property.clienteId}&pedidoId=${property.pedidoId}&ebookId=${property.ebookId}"))

				.to("http4://localhost:8080/webservices/ebook/item");
				
				from("direct:soap")
					.routeId("Rota soap")
					.to("xslt:pedido-para-soap.xslt")  
					.log("${body}")
					.setHeader(Exchange.CONTENT_TYPE, constant("text/xml"))
					.to("http4://localhost:8080/webservices/financeiro");
				
			}
			
		});
		
		context.start();
		Thread.sleep(2000L);
		context.stop();
	}
}
