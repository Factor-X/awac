package eu.factorx.awac.util.email.business;

import java.io.*;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

import eu.factorx.awac.util.MyrmexFatalException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import play.Configuration;
import play.Logger;
import eu.factorx.awac.models.email.MailConfig;
import eu.factorx.awac.util.email.messages.EmailMessage;
import scala.util.parsing.combinator.testing.Str;

@Component
public class EmailSender implements ApplicationContextAware {

	// Application context aware
	private static ApplicationContext ctx = null;
	public static ApplicationContext getApplicationContext() {
		return ctx;
	}
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// Assign the ApplicationContext into a static method
		EmailSender.ctx = ctx;
	}

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
    
    public void sendEmail(EmailMessage email) throws MessagingException, UnsupportedEncodingException {
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
            for(String address : email.getToAddress()) {
                mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
            }
	        for(String address : email.getCcAddresses()) {
		        mimeMessage.addRecipients(Message.RecipientType.CC, InternetAddress.parse(address));
	        }
            mimeMessage.setSubject(MimeUtility.encodeText(email.getSubject(), "utf-8", "B"));

			// main body part -> email content
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(email.getContent(), "text/html; charset=utf-8");

			// multipart
			Multipart multipart = new MimeMultipart();

			// add mainbodypart to multipart
			multipart.addBodyPart(messageBodyPart);

			// check if attachments
			if (email.getAttachmentFilenameList()!=null) {
				// for each attachment
				for (Map.Entry<String, ByteArrayOutputStream> fileName : email.getAttachmentFilenameList().entrySet())
                    try {
                        addAttachment(multipart,fileName.getKey() , fileName.getValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                // end for
            } // end if

			// add multipart to message
			mimeMessage.setContent(multipart);

			// send message
            Transport.send(mimeMessage);

            play.Logger.info("Email Successfully sent to " + email.getToAddress());

        } catch (MessagingException e) {
            throw e;
        }

    }

	// add attachment
	private static void addAttachment(Multipart multipart, String name, ByteArrayOutputStream out) throws IOException, MessagingException{

        if(out==null || name==null){
            throw new MyrmexFatalException("out or name is null:"+name+"/"+out);
        }
        Logger.info(out.toByteArray()+"");

        MimeBodyPart attachmentPart = new MimeBodyPart();

        Logger.info(new ByteArrayInputStream(out.toByteArray())+"");

        DataSource aAttachment = new ByteArrayDataSource(new ByteArrayInputStream(out.toByteArray()),"application/octet-stream");
        attachmentPart.setDataHandler(new DataHandler(aAttachment));
        attachmentPart.attachFile(name);
		multipart.addBodyPart(attachmentPart);
	}
}
