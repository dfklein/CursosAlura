package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * ***************************************
 * ************* ATENÇÃO *****************
 * ***************************************
 *
 * Esta é uma versão antiga da API do JMS.
 */
public class TesteConsumidorTopicoSelector {

	public static void main(String[] args) throws JMSException, NamingException {
		System.out.println("TesteConsumidorTopicoSelector em execução");
		InitialContext context = new InitialContext(); 
													
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.setClientID("estoque-selector"); 
		
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		
		Topic topico = (Topic) context.lookup("loja");
		 /**
		  * Nesta classe é feito um consumidor de tópico que filtra mensagens de um mesmo tópico para si. 
		  * O nome disso é SELECTOR.
		  * 
		  * Este filtro é feito por buscando valores apenas nos headers (cabeçalho) e properties (propriedades) das mensagens, 
		  * e NÃO pelo corpo delas.
		  * 
		  * Neste exemplo você está verificando que a sua mensagem possui um atributo ebook=false pu nulo no cabeçalho
		  * Caso seja ela será consumida.
		  * 
		  * O último argumento refere-se a você aceitar (true) ou não (false) mensagens que foram enviadas
		  * utilizando esta mesma conexão.
		  */
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura-selector", "ebook is null OR ebook= false", false);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
				
					System.out.println("Mensagem: " + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
			}
			
		});
		
		
		new Scanner(System.in).nextLine(); // parar o programa para testar a conexao

		session.close();
		connection.close();
		context.close();
		System.out.println("fim");
	}
}
