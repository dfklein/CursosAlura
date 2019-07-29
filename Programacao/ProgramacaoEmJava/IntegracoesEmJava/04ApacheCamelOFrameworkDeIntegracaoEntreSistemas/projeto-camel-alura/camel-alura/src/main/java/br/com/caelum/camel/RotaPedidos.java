package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidos {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		
		// As rotas tratam de como receber um objeto, manipulá-lo e definir um destino para eles.
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				// A rota tem sempre uma origem e um destino. Você utiliza o método from() para adicionar uma nova rota às configurações.
				// Você define a origem dizendo qual o tipo dela (file). 
				// Neste caso você está dizendo qual é a pasta onde está o arquivo
				// Você passa um parâmetro adicional como, por exemplo, um delay de 5s (checar a cada 5s)
				// Na prática você está pegando todos os arquivos da pasta pedido e mandando para a pasta saída.
				// ****** VEJA DOCUMENTAÇÃO EM http://camel.apache.org/components.html. ****** 
				from("file:pedidos?delay=5s&noop=true") // o moop=true não apaga os arquivos da origem.
				// Aqui no log estamos utilizando expressões que referenciam informações dos objetos da fila.
				// .log("${id} - ${body}") // o ${body} vai exibir o conteúdo do arquivo aqui.
				// .log("${exchange.pattern}")
				
					.setProperty("pedidoId", xpath("/pedido/id/text()"))	// Converte uma propriedade em uma variável da sua rota.
				    .setProperty("clienteId", xpath("/pedido/pagamento/email-titular/text()"))
				
					.split()
						.xpath("/pedido/itens/item") 	// este split vai separar no nível indicado todos os ítens, de forma que você possa filtrá-los
														// Se você não fizer isso ele trará todos os pedidos que possuam alguma ocorrência  do formato EBOOK (mesmo possuindo outros também).
														// Ao fazer este filtro vc trará apenas os ítens e do tipo EBOOK ao filtrar.
					.filter()
						// .xpath("/pedido/itens/item/formato[text()='EBOOK']") // você usava o path completo antes de filtrar. Não existe mais depois do filtro 
						.xpath("/item/formato[text()='EBOOK']")
						.setProperty("ebookId", xpath("/item/livro/codigo/text()"))
					.marshal()							// O processo de transformação de dados em memória em um outro formato é chamado de marshal. O contrário é chamado de unmarshal.
						.xmljson() 						// conversão para json
					
				//******** PARA NOVOS ARQUIVOS
				//	.setHeader("CamelFileName", simple("${file:name.noext}.json"))
				// .to("file:saida"); // envia a entrada para o diretório saída em formato de arquivo.
						
				//******** PARA HTTP POST
				//	.setHeader(Exchange.HTTP_METHOD, HttpMethods.POST)
						
				//******** PARA HTTP GET
					.setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
					.setHeader(Exchange.HTTP_QUERY, 
							// Este argumento é do tipo Expression. Pode ser criado por dois métodos: simple() e constant().
							// A diferença é que o simple lê expression language, permitindo o acesso às variáveis da rota.
				            simple("clienteId=${property.clienteId}&pedidoId=${property.pedidoId}&ebookId=${property.ebookId}"))
				.to("http4://localhost:8080/webservices/ebook/item"); // http4 é o nome da biblioteca (por isso o 4)
			}
		});
		
		context.start();
		Thread.sleep(2000L);
		context.stop();

	}	
}
