package me.alexanderhodes.blocktrace.service;

import me.alexanderhodes.blocktrace.model.Email;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;

/**
 * Created by alexa on 23.09.2017.
 */
public class EmailService {

    private static final String EMAIL_SESSION_JNDI_PATH = "java:/Mail";

    @Resource(mappedName = "java:/Mail")
    private Session mailSession;

    private Session createMailSession () throws Exception {
        InitialContext initialContext = new InitialContext();
        return (Session) initialContext.lookup(EMAIL_SESSION_JNDI_PATH);
    }

    /**
     * Sending email
     *
     * @param email E-Mail with attributes for subject, content, receiver and sender
     */
    public void send (Email email) {
        try {
            MimeMessage m = new MimeMessage(createMailSession());
            // Umwandlung der E-Mail Adressen in Address
            Address from = new InternetAddress("blocktrace@gmail.com");
            Address to = new InternetAddress(email.getReceiver());
            // setzen der E-Mail Adresse des Senders
            m.setFrom(from);
            // setzen der E-Mail Adresse des Empfängers
            m.addRecipient(Message.RecipientType.TO, to);
            // setzen des Betreffs
            m.setSubject(email.getSubject());
            // setzen der Nachricht im Content-type html
            m.setContent(email.getMessage(), "text/html; charset=utf-8");
            // Versan der E-Mail
            Transport.send(m);
            System.out.println("Mail Sent Successfully.");
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }

    }

}
