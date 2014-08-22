package eu.factorx.awac.util.email.business;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import eu.factorx.awac.models.email.MailConfig;
import eu.factorx.awac.util.email.messages.EmailMessage;
import play.Configuration;
import play.Logger;

public class EmailSender {

    private static final String MAIL_SMTP_AUTH_KEY = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_KEY = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_HOST_KEY = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT_KEY = "mail.smtp.port";

    public EmailSender() throws IOException  {
        MailConfig.loadConfigurations();
    }
    
    
    /**
     * Private helper invoked by the actors that sends the email
     * @param email the email message
     */
    
    public void sendEmail(EmailMessage email) throws MessagingException {
    	play.Logger.info("Sending email ...");
        final String username = MailConfig.username;
		// mail.smpt.password must be define in conf/application.conf
		final String password = Configuration.root().getString("mail.smtp.password");


        Properties props = new Properties();
        props.put(MAIL_SMTP_AUTH_KEY, MailConfig.smtpAuth);
        props.put(MAIL_SMTP_STARTTLS_KEY, MailConfig.starttlsEnable);
        props.put(MAIL_SMTP_HOST_KEY, MailConfig.smtpHost);
        props.put(MAIL_SMTP_PORT_KEY, MailConfig.smtpPort);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(MailConfig.fromAddress));
            //mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(MailConfig.toAddress));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getToAddress()));
            //mimeMessage.setSubject("Testing Subject");
            mimeMessage.setSubject(email.getSubject());
            //mimeMessage.setText("Mail Body");
            mimeMessage.setText(email.getContent());
            Transport.send(mimeMessage);

            play.Logger.info("Email Successfully sent to " + email.getToAddress());

        } catch (MessagingException e) {
            throw e;
        }

    }
}
