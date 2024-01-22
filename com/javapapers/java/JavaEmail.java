package com.javapapers.java;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class JavaEmail {

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    public static void main(String args[]) {
        JavaEmail javaEmail = new JavaEmail();

        try {
            javaEmail.setMailServerProperties();
            javaEmail.createEmailMessage();
            javaEmail.sendEmail();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void setMailServerProperties() {
        String emailHost = "smtp.example.com"; // Ganti dengan host SMTP yang benar
        String emailPort = "587"; // Ganti dengan port SMTP yang benar
        String username = "user@name.net";
        String password = "password";

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.host", emailHost);
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        emailProperties.put("mail.debug", "true");

        mailSession = Session.getInstance(emailProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void createEmailMessage() throws AddressException, MessagingException {
        String[] toEmails = {"adegmz@gmail.com"};
        String emailSubject = "Java Email";
        String emailBody = "This is an email sent by JavaMail API.";

        emailMessage = new MimeMessage(mailSession);

        for (int i = 0; i < toEmails.length; i++) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }

        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html"); // for an HTML email
    }

    public void sendEmail() throws AddressException, MessagingException {
        String emailHost = "smtp.example.com"; // Ganti dengan host SMTP yang benar

        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, "user@name.net", "password");
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
}
