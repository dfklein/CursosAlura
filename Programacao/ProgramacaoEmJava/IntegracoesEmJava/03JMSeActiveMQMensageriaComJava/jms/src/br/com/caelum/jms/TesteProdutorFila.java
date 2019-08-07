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
public class TesteProdutorFila {

	public static void main(String[] args) throws JMSException, NamingException {
		InitialContext context = new InitialContext();
														
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		
		// Localiza a fila que se deseja consumir.
		Destination fila = (Destination) context.lookup("financeiro"); // veja no arquivo jndi.properties que "financeiro" refere-se a uma fila registrada no ActiveMQ
		MessageProducer producer = session.createProducer(fila);
		 /**
		  * O teste abaixo foi feito com duas instâncias da classe consumidora (TesteConsumer) rodando. O que você comprovou é que 
		  * Quando são produzidas muitas msgs com dois consumidores o seu MOM irá balancear as mensagens entre todos os consumidores
		  * conectados.
		  * 
		  * Caso você queira garantir que uma mesma msg seja enviada a vários consumidores, você precisa criar um topic ao invés de
		  * um queue no seu MOM.
		  */
		for(int i = 0; i < 1000; i ++) { 
		    Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
		    producer.send(message);
		}
		
//		new Scanner(System.in).nextLine(); // parar o programa para testar a conexao

		session.close();
		connection.close();
		context.close();
		System.out.println("fim");
	}
}
