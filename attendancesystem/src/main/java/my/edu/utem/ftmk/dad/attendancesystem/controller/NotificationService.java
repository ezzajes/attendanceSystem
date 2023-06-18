package my.edu.utem.ftmk.dad.attendancesystem.controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * NotificationService class is for making notification examination reminder via SMS and email 
 * @author Sofia
 *
 */
public class NotificationService {
	
	/**
	 * This method is for sending email notification
	 * @param recipient
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(String recipient, String subject, String body) {
        // Set the email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 25);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Set the sender's credentials
        final String username = "ulya.sofia.su@gmail.com";
        final String password = "uvpqjbxbkhupvapa";

        // Create a session with the sender's credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set the recipient, subject, and body of the email
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * This is main function to trigger method
	 */
    public static void main(String[] args) {
        String recipient = "ulya.sofia.su@gmail.com";
        String subject = "Notification";
        String body = "This is a notification email.";
        sendEmail(recipient, subject, body);      
    }
}
