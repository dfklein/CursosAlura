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
public class TesteProdutorTopico {

	public static void main(String[] args) throws JMSException, NamingException {
		InitialContext context = new InitialContext();
														
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		
		Destination fila = (Destination) context.lookup("loja");
		MessageProducer producer = session.createProducer(fila);

		// Convertendo um objeto em XML. Veja as anota��es necess�rias na classe Pedido.
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		StringWriter writer = new StringWriter();
		JAXB.marshal(pedido, writer);
		System.out.println(pedido);
		
		// Nesta vers�o abaixo voc� est� enviando uma mensagem em formato de texto e assim deve ser recebida do outro lado.
		 Message message = session.createTextMessage(writer.toString());
		
		// isto adiciona uma propriedade ao cabe�alho, n�o � mensagem, o que possiblita que um consumidor filtre esta mensagem e n�o a receba.
		// message.setBooleanProperty("ebook", false); 
	    
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();
		System.out.println("fim");
	}
}
