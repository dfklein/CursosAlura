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
public class TesteConsumidorTopico {

	public static void main(String[] args) throws JMSException, NamingException {
		System.out.println("TesteConsumidorTopico em execução");
		InitialContext context = new InitialContext(); 
													
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		/**
		 *  Existem duas maneiras de consumir um tópico. Uma é o consumidor já estar conectado quando o tópico é cadastrado no MOM. 
		 *  Nesta situação todos os clientes conectados irão receber a mensagem do topico.
		 *  Caso o cliente se conecte depois ele não receberá esta mensagem pois o MOM não tem como saber para quantos clientes aquele
		 *  tópico deve ser enviado.
		 *  
		 *  No entanto a ideia é que o recebimento seja assíncrono, caso o cliente não esteja disponível no momento que a mensagem é
		 *  enviada para o tópico. Para isto é necessária uma configuração adicional.
		 *  
		 *  Esta configuração é basicamente uma identificação que é necessária ser usada na conexão (connection.setClientID("estoque"))
		 *  e no consumer (session.createDurableSubscriber(topico, "assinatura")).
		 *  
		 *  Estas identificações são armazenadas pelo MOM para saber quais mensagens seu cliente já recebeu ou não (e enviar as não
		 *  recebidas assim que o cliente se conectar a ele)
		 */
		connection.setClientID("estoque"); // estoque é um nome que é dado para a conexão.
		
		connection.start();

		Session session = connection.createSession(true, Session.SESSION_TRANSACTED); // exemplo de consumidor transacional. 
		
		Topic topico = (Topic) context.lookup("loja");
		
		// Não usar o createConsumer pois ele se comportará de maneira síncrona para um tópico
		// MessageConsumer consumer = session.createConsumer(topico);
		
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura"); // assinatura é como um ID dado ao consumidor
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					// message.acknowledge(); // NÃO USE ACKNOWLEDGE NO TRANSACIONAL.
					TextMessage textMessage = (TextMessage) message;
				
					System.out.println("Mensagem: " + textMessage.getText());
					session.commit();
					// Um exemplo idiota em que você poderia usar o rollback:
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
