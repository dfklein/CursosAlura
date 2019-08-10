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
public class TesteConsumidorTopico {

	public static void main(String[] args) throws JMSException, NamingException {
		System.out.println("TesteConsumidorTopico em execu��o");
		InitialContext context = new InitialContext(); 
													
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		/**
		 *  Existem duas maneiras de consumir um t�pico. Uma � o consumidor j� estar conectado quando o t�pico � cadastrado no MOM. 
		 *  Nesta situa��o todos os clientes conectados ir�o receber a mensagem do topico.
		 *  Caso o cliente se conecte depois ele n�o receber� esta mensagem pois o MOM n�o tem como saber para quantos clientes aquele
		 *  t�pico deve ser enviado.
		 *  
		 *  No entanto a ideia � que o recebimento seja ass�ncrono, caso o cliente n�o esteja dispon�vel no momento que a mensagem �
		 *  enviada para o t�pico. Para isto � necess�ria uma configura��o adicional.
		 *  
		 *  Esta configura��o � basicamente uma identifica��o que � necess�ria ser usada na conex�o (connection.setClientID("estoque"))
		 *  e no consumer (session.createDurableSubscriber(topico, "assinatura")).
		 *  
		 *  Estas identifica��es s�o armazenadas pelo MOM para saber quais mensagens seu cliente j� recebeu ou n�o (e enviar as n�o
		 *  recebidas assim que o cliente se conectar a ele)
		 */
		connection.setClientID("estoque"); // estoque � um nome que � dado para a conex�o.
		
		connection.start();

		Session session = connection.createSession(true, Session.SESSION_TRANSACTED); // exemplo de consumidor transacional. 
		
		Topic topico = (Topic) context.lookup("loja");
		
		// N�o usar o createConsumer pois ele se comportar� de maneira s�ncrona para um t�pico
		// MessageConsumer consumer = session.createConsumer(topico);
		
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura"); // assinatura � como um ID dado ao consumidor
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					// message.acknowledge(); // N�O USE ACKNOWLEDGE NO TRANSACIONAL.
					TextMessage textMessage = (TextMessage) message;
				
					System.out.println("Mensagem: " + textMessage.getText());
					session.commit();
					// Um exemplo idiota em que voc� poderia usar o rollback:
					if(textMessage.getText() == "XXX") {
						session.rollback();
					}
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
