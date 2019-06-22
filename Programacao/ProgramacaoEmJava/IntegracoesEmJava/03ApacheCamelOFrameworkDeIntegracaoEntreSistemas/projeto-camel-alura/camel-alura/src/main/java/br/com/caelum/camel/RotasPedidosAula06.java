package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class RotasPedidosAula06 {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				
				// O onException captura exceções específicas, permitindo que você as trate conforme o tipo. 
				
//				onException(Exception.class) // Pode colocar qualquer outro tipo de exceção.
//			    .handled(true)
//			        .maximumRedeliveries(3)
//			            .redeliveryDelay(4000)
//			        .onRedelivery(new Processor() {
//
//			            @Override
//			            public void process(Exchange exchange) throws Exception {
//			                    int counter = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER);
//			                    int max = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_MAX_COUNTER);
//			                    System.out.println("Redelivery - " + counter + "/" + max );;
//			            }
//			    });
				
				// Já o errorHandler é mais genérico e lida com qualquer erro.
				
				errorHandler(
					    deadLetterChannel("file:erro") // deadLetterChannel é um errorHandler que pode ser personalizado.
					    	.logExhausted(true) // loga o erro
					        .maximumRedeliveries(3)//tente 3 vezes
					            .redeliveryDelay(3000) //espera 3 segundo entre as tentativas   
					            .onRedelivery(new Processor() { // executa em todas as tentativas de entrega

									@Override
									public void process(Exchange exchange) throws Exception {
										int count = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER);
										int max = (int) exchange.getIn().getHeader(Exchange.REDELIVERY_MAX_COUNTER);
										System.out.println(String.format("Tentativa %d de %d", count, max));
										
									} 
					            	
					            }) 
					            
				);
				
				
				from("file:pedidos?delay=5s&noop=true")
					.routeId("Rota principal")
					.to("validator:pedido.xsd") ;	// define que a entrada deve ser validada de acordo com o schema pedido.xsd
//					 .multicast()
//						 .to("direct:soap")
//						 .to("direct:http");
				
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
