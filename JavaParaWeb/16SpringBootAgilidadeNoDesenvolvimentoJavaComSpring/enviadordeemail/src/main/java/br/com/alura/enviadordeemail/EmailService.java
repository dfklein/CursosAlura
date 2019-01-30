package br.com.alura.enviadordeemail;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
    public void enviar(String nome, String emailConvidado) {
    	
    	System.out.println("*** E-mail enviado para " + nome + " no endereço " + emailConvidado);

    	// Código com o apache mail
		//    	try {
		//            Email email = new SimpleEmail();
		//            email.setHostName("smtp.googlemail.com");
		//            email.setSmtpPort(465);
		//            email.setAuthenticator(new DefaultAuthenticator("email", "password"));
		//            email.setSSLOnConnect(true);
		//
		//            email.setFrom("email");
		//            email.setSubject("Você foi convidado pelo ListaVIP");
		//            email.setMsg("Olá " + nome + ". Você acaba de ser convidado pelo ListaVIP.");
		//            email.addTo(emailConvidado);
		//            email.send();
		//
		//        } catch (EmailException e) {
		//            e.printStackTrace();
		//        }
    	
    }
}
