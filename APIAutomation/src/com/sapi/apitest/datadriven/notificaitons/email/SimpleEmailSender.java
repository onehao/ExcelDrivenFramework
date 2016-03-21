/*
 * ################################################################################
 * 
 *    Copyright (c) 2015 Baidu.com, Inc. All Rights Reserved
 *
 *  version: v1
 *  
 *  
 * ################################################################################
 */
package com.sapi.apitest.datadriven.notificaitons.email;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/***
 * 
 * The class encapsulate the email sending logic.
 * 
 * @author wanhao01
 *
 */
public class SimpleEmailSender {

    private final transient Properties props = System.getProperties();

    private transient EmailAuthenticator authenticator;

    /**
     * Session
     */
    private transient Session session;

    /**
     * 
     * @param smtpHostName
     * @param username
     * @param password
     */
    public SimpleEmailSender(final String smtpHostName, final String username,
            final String password) {
        init(username, password, smtpHostName);
    }

    /**
     * 
     * @param username
     * @param password
     */
    public SimpleEmailSender(final String username, final String password) {
        // parsing the SMTP server according to the email address, this function
        // will work for most cases.
        final String smtpHostName = "smtp." + username.split("@")[1];
        init(username, password, smtpHostName);

    }

    /**
     * Initialized the context.
     * @param username
     * @param password
     * @param smtpHostName
     */
    private void init(String username, String password, String smtpHostName) {
        // init props
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtpHostName);
        // authentication
        authenticator = new EmailAuthenticator(username, password);
        // create session
        session = Session.getInstance(props, authenticator);
        // session.setDebug(true);
    }

    /**
     * Sending email(s).
     * @param recipient
     * @param subject
     * @param content
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(String recipient, String subject, Multipart content)
            throws AddressException, MessagingException {
        // create email with type MIME.
        final MimeMessage message = new MimeMessage(session);
        // set email sender.
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // set recipient.
        message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
        // set subject.
        message.setSubject(subject);
        // set the content.
        message.setContent(content);

        // send email.
        Transport.send(message);
    }

    /**
     * Sending email(s) to recipients list.
     * 
     * @param recipients
     *            the recipients.
     * @param subject
     *            email subject.
     * @param content
     *            email content.
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, String subject, Multipart content)
            throws AddressException, MessagingException {
        // create email with type MIME.
        final MimeMessage message = new MimeMessage(session);
        // the sender.
        message.setFrom(new InternetAddress(authenticator.getUsername()));
        // the recipients
        final int num = recipients.size();
        InternetAddress[] addresses = new InternetAddress[num];
        for (int i = 0; i < num; i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(RecipientType.TO, addresses);
        // email subject.
        message.setSubject(subject);
        // the email context.
        message.setContent(content);
        // send
        Transport.send(message);
    }

    /**
     * Sending email
     * 
     * @param recipient
     *            the recipient email address.
     * @param mail
     * 
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(String recipient, EmailEntity mail)
            throws AddressException, MessagingException {
        send(recipient, mail.getSubject(), mail.getContent());
    }

    /**
     * Sending email(s) to the recipients list.
     * 
     * @param recipients
     *            the recipients list.
     * @param mail
     * 
     * @throws AddressException
     * @throws MessagingException
     */
    public void send(List<String> recipients, EmailEntity mail)
            throws AddressException, MessagingException {
        send(recipients, mail.getSubject(), mail.getContent());
    }
}