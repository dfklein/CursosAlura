package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidosAula04 {
	
	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				
				// Aqui você está pegando os arquivos da sua pasta pedido e enviando para duas subrotas (direcionando para duas
				// outras rotas que vão fazer ações distintas). Você a envia então para um direct.
				from("file:pedidos?delay=5s&noop=true")
					.routeId("Rota principal")
					// Se você apenas declarar alguns destinos ele vai apenas executá-los na ordem em que
					// foram declarados (inclusive cadenciando filtros e formatações). Caso a sua ideia seja
					// enviar a mesma entrada para ambos, então você deve utilizar o multicast()
					.multicast()
						.to("direct:soap");
						//.to("direct:http");
				
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
					.to("xslt:pedido-para-soap.xslt") // adequa para o soap de acordo com o modelo que você estipulou no pedido-para-soap.xslt 
					.log("${body}")
					.setHeader(Exchange.CONTENT_TYPE, constant("text/xml"))
				// .to("mock:soap"); Você pode usar o componente mock se não quiser testar o redirecionamento final.
					.to("http4://localhost:8080/webservices/financeiro");
				
			}
			
		});
		
		context.start();
		Thread.sleep(2000L);
		context.stop();
	}
}
