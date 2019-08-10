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
 * ************* ATENÇÃO *****************
 * ***************************************
 *
 * Esta é uma versão antiga da API do JMS.
 */
public class TesteConsumidorTopicoComercial {

	public static void main(String[] args) throws JMSException, NamingException {
		System.out.println("TesteConsumidorTopicoComercial em execução");
		
		/**
		 * MUITO IMPORTANTE:
		 * 
		 * O ActiveMQ é bastante exigente com relação ao que pode ou não desserializar, sendo mandatório
		 * a partir da versão 5.12.2 que você declare explicitamente quais pacotes são permitidos neste
		 * processo.
		 */
//		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","java.lang,java.util,sun.util,java.math,br.com.caelum.modelo");
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
		
		InitialContext context = new InitialContext(); 
													
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.setClientID("estoque"); // estoque é um nome que é dado para a conexão.
		
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		
		Topic topico = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura"); // assinatura é como um ID dado ao consumidor
		
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
