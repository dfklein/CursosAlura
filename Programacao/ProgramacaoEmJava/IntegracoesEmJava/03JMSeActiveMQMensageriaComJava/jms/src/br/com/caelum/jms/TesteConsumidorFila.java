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
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * ***************************************
 * ************* ATEN��O *****************
 * ***************************************
 *
 * Esta � uma vers�o antiga da API do JMS.
 */
public class TesteConsumidorFila {

	public static void main(String[] args) throws JMSException, NamingException {
		InitialContext context = new InitialContext(); // O construtor deste objeto ir� procurar um arquivo de nome
														// jndi.properties
														// Ele precisa estar no classpath.
														// Este arquivo � quem vai indicar onde est� a instala��o do activemq
														// Tamb�m � poss�vel passar essas propriedades programaticamente atrav�s de
														// outros contrutores de InitialContext.
												

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.start();

		// Cria uma sess�o para a conex�o. A Session no JMS abstrai o trabalho transacional e confirma��o do recebimento da mensagem.
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);	// O primeiro argumento � para dizer se a sess�o � transacional ou n�o. Se for, voc� poder� chamar um m�todo commit()
																						// O segundo argumento refere-se a maneiras de configurar a confirma��o de recebimento da mensagem.
																						// Aqui voc� configurou para que a confirma��o de recebimento da msg fosse feito programaticamente (message.acknowledge()). N�o � muito comum esta utiliza��o por ela n�o permitir rollback, sendo melhor utilizar o transacional.
																						// Em TesteConsumidorTopico h� um exemplo de como � feito transacional. Em outras classes consumidoras voc� fez isso automaticamente.
		
		// Localiza a fila que se deseja consumir.
		Destination fila = (Destination) context.lookup("financeiro"); // veja no arquivo jndi.properties que "financeiro" refere-se a uma fila registrada no ActiveMQ
		MessageConsumer consumer = session.createConsumer(fila);
		
		// Message message = consumer.receive(2000); 	// obtem a mensagem da fila, mas � melhor criar um listener para que ele n�o
														// consuma apenas a primeira mensagem da fila.
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = (TextMessage) message;
				
					message.acknowledge(); // confirma programaticamente o recebimento da mensagem
					System.out.println("Mensagem: " + textMessage.getText());
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
