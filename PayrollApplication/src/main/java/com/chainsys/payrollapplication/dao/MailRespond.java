package com.chainsys.payrollapplication.dao;


import java.util.Properties;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;


public class MailRespond {

    static Session newSession = null;

    public static void Properties() {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        newSession = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("harishdhanush2196@gmail.com", "ebdy voeb lzon cuxt");
            }
        });
    }

    public static void MailBody(String recipient,String mailBody ) throws AddressException, MessagingException {
        String emailSubject = "Salary credition";

        Message mimeMessage = new MimeMessage(newSession);
        mimeMessage.setFrom(new InternetAddress("your-email@example.com"));


            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        mimeMessage.setSubject(emailSubject);
        mimeMessage.setText(mailBody);

        Transport.send(mimeMessage);
    }
}
