package curso.springboot.restfull.service;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;


@Service
public class EnviaEmailService {
	
	private String emailOrigem = "alessandrojavaetb@gmail.com";
	private String senha = "";// digite aqui a senha
	
	public void enviarEmail(String assunto, String mensagem, String emailDestino) throws MessagingException{
		
		// PROPERTIES GMAIL
		Properties prop = new Properties();
		
		prop.put("mail.smtp.auth", "true");// autenticação
		prop.put("mail.smtp.starttls", "true");// Tipo de autenticação: TLS,SSL e etc. 
		prop.put("mail.smtp.ssl.trust", "*");// Tipo de autenticação: TLS,SSL e etc. 
		prop.put("mail.smtp.host", "smtp.gmail.com");// host
		prop.put("mail.smtp.port", "465");// porta
		prop.put("mail.smtp.socketFactory.port", "465");// porta Socket
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");// Classe Socket

		
		// EMAIL DA EMPRESA
		Session session = Session.getInstance(prop, new Authenticator(){
		
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailOrigem, senha);// Seu Email e Senha
			}
			
		});
		
		Address[] toUser = InternetAddress.parse(emailDestino);
		
		// MENSAGEM
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(emailOrigem));// from: EMAIL DE ORIGEM
		message.setRecipients(Message.RecipientType.TO, toUser);// to: EMAIL DE DESTINO
		message.setSubject(assunto);// ASSUNTO DO EMAIL
		message.setText(mensagem);// CORPO DO EMAIL
		
		
		
		// ENVIAR EMAIL
		Transport.send(message);
		
	}
}
