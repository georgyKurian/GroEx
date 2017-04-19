/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.Properties;
import javax.enterprise.context.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Georgi
 */
@SessionScoped
public class MyMail implements Serializable {

    String username;
    String password;

    Properties props;

    /**
     * initialize the value
     */
    public MyMail() {
        username = "groexmail@gmail.com";
        password = "wearegreat";

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    /**
     * Sends welcome mail to the user
     *
     * @param recepientEmail recepientsEmail Id
     * @param recepientName recepientsName
     */
    public void sendWelcomeMail(String recepientEmail, String recepientName) {
        Session session;
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("groexmail@groex.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recepientEmail));
            message.setSubject("Testing Subject");
            message.setText("Dear " + recepientName + ","
                    + "\n Welcome to GroEx application!" + "\n \n - Regards,\n GroEx Team");
            System.out.println("Sending message to : " + recepientName);
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
