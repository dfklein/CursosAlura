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
 * ************* ATEN��O *****************
 * ***************************************
 *
 * Esta � uma vers�o antiga da API do JMS.
 */
public class TesteConsumidorTopicoSelector {

	public static void main(String[] args) throws JMSException, NamingException {
		System.out.println("TesteConsumidorTopicoSelector em execu��o");
		InitialContext context = new InitialContext(); 
													
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.setClientID("estoque-selector"); 
		
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		
		Topic topico = (Topic) context.lookup("loja");
		 /**
		  * Nesta classe � feito um consumidor de t�pico que filtra mensagens de um mesmo t�pico para si. 
		  * O nome disso � SELECTOR.
		  * 
		  * Este filtro � feito por buscando valores apenas nos headers (cabe�alho) e properties (propriedades) das mensagens, 
		  * e N�O pelo corpo delas.
		  * 
		  * Neste exemplo voc� est� verificando que a sua mensagem possui um atributo ebook=false pu nulo no cabe�alho
		  * Caso seja ela ser� consumida.
		  * 
		  * O �ltimo argumento refere-se a voc� aceitar (true) ou n�o (false) mensagens que foram enviadas
		  * utilizando esta mesma conex�o.
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
