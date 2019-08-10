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
 * ************* ATENÇÃO *****************
 * ***************************************
 *
 * Esta é uma versão antiga da API do JMS.
 */
public class TesteConsumidorFila {

	public static void main(String[] args) throws JMSException, NamingException {
		InitialContext context = new InitialContext(); // O construtor deste objeto irá procurar um arquivo de nome
														// jndi.properties
														// Ele precisa estar no classpath.
														// Este arquivo é quem vai indicar onde está a instalação do activemq
														// Também é possível passar essas propriedades programaticamente através de
														// outros contrutores de InitialContext.
												

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.start();

		// Cria uma sessão para a conexão. A Session no JMS abstrai o trabalho transacional e confirmação do recebimento da mensagem.
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);	// O primeiro argumento é para dizer se a sessão é transacional ou não. Se for, você poderá chamar um método commit()
																						// O segundo argumento refere-se a maneiras de configurar a confirmação de recebimento da mensagem.
																						// Aqui você configurou para que a confirmação de recebimento da msg fosse feito programaticamente (message.acknowledge()). Não é muito comum esta utilização por ela não permitir rollback, sendo melhor utilizar o transacional.
																						// Em TesteConsumidorTopico há um exemplo de como é feito transacional. Em outras classes consumidoras você fez isso automaticamente.
		
		// Localiza a fila que se deseja consumir.
		Destination fila = (Destination) context.lookup("financeiro"); // veja no arquivo jndi.properties que "financeiro" refere-se a uma fila registrada no ActiveMQ
		MessageConsumer consumer = session.createConsumer(fila);
		
		// Message message = consumer.receive(2000); 	// obtem a mensagem da fila, mas é melhor criar um listener para que ele não
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
