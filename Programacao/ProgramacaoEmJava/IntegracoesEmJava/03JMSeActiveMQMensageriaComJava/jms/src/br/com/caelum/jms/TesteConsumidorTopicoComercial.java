package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.caelum.modelo.Pedido;

/**
 * ***************************************
 * ************* ATEN��O *****************
 * ***************************************
 *
 * Esta � uma vers�o antiga da API do JMS.
 */
public class TesteConsumidorTopicoComercial {

	public static void main(String[] args) throws JMSException, NamingException {
		System.out.println("TesteConsumidorTopicoComercial em execu��o");
		
		/**
		 * MUITO IMPORTANTE:
		 * 
		 * O ActiveMQ � bastante exigente com rela��o ao que pode ou n�o desserializar, sendo mandat�rio
		 * a partir da vers�o 5.12.2 que voc� declare explicitamente quais pacotes s�o permitidos neste
		 * processo.
		 */
//		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","java.lang,java.util,sun.util,java.math,br.com.caelum.modelo");
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
		
		InitialContext context = new InitialContext(); 
													
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.setClientID("estoque"); // estoque � um nome que � dado para a conex�o.
		
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		
		Topic topico = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura"); // assinatura � como um ID dado ao consumidor
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					ObjectMessage objMessage = (ObjectMessage) message;
					
					Pedido obj = (Pedido) objMessage.getObject();
					
					System.out.println("Mensagem: " + obj);
				} catch (JMSException e) {
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		new Scanner(System.in).nextLine(); // parar o programa para testar a conexao

		session.close();
		connection.close();
		context.close();
		System.out.println("fim");
	}
}
