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
 * ************* ATENÇÃO *****************
 * ***************************************
 *
 * Esta é uma versão antiga da API do JMS.
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
		
		// Já nesta outra versão estamos enviando um objeto serializado para ser desserializado no consumidor.
		/**
		 * ATENÇÃO: verifique na classe TesteConsumidorTopicoComercial que no ActiveMQ a desserialização de
		 * objetos precisa de configurações adicionais.
		 */
		Message message = session.createObjectMessage(pedido);
		
		
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();
		System.out.println("fim");
	}
}
