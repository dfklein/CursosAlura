package br.com.caelum.jms;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXB;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

/**
 * ***************************************
 * ************* ATEN��O *****************
 * ***************************************
 *
 * Esta � uma vers�o antiga da API do JMS.
 */
public class TesteProdutorTopicoObjeto {

	public static void main(String[] args) throws JMSException, NamingException {
		InitialContext context = new InitialContext();
														
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		
		Destination fila = (Destination) context.lookup("loja");
		MessageProducer producer = session.createProducer(fila);

		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
		// J� nesta outra vers�o estamos enviando um objeto serializado para ser desserializado no consumidor.
		/**
		 * ATEN��O: verifique na classe TesteConsumidorTopicoComercial que no ActiveMQ a desserializa��o de
		 * objetos precisa de configura��es adicionais.
		 */
		Message message = session.createObjectMessage(pedido);
		
		
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();
		System.out.println("fim");
	}
}
