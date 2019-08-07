package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * ***************************************
 * ************* ATENÇÃO *****************
 * ***************************************
 *
 * Esta é uma versão antiga da API do JMS.
 */
public class TesteProdutorTopico {

	public static void main(String[] args) throws JMSException, NamingException {
		InitialContext context = new InitialContext();
														
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		
		Destination fila = (Destination) context.lookup("loja");
		MessageProducer producer = session.createProducer(fila);

	    Message message = session.createTextMessage("<pedido><id>1</id></pedido>");
	    producer.send(message);
		
		session.close();
		connection.close();
		context.close();
		System.out.println("fim");
	}
}
