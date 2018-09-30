package email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Java Program to send text mail using default SMTP server and without
 * authentication. You need mail.jar, smtp.jar and activation.jar to run this
 * program.
 * 
 * @author Javin Paul
 */

public class EmailSender {
    public static void main (String args[]) {
        sendMessage();
    }

    private static void sendMessage () {
        String to = "corpsetrup@gmail.om"; // sender email
        String from = "alexanderneaznaev@gmail.com"; // receiver email
        String host = "127.0.0.1"; // mail server host

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        // default session
        Session session = Session.getDefaultInstance(properties);

        try {
            // email message
            MimeMessage message = new MimeMessage(session);
            // setting header fields
            message.setFrom(new InternetAddress(from)); 
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // subject line
            message.setSubject("Test Mail from Java Program"); 
            // actual mail body
            message.setText("You can send mail from Java program by using mail API, but you need"
                    + "couple of more JAR files e.g. smtp.jar and activation.jar");
            // Send message
            Transport.send(message);
            System.out.println("Email Sent successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
