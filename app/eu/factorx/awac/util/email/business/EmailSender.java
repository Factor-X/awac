package eu.factorx.awac.util.email.business;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import eu.factorx.awac.controllers.EmailController;
import eu.factorx.awac.models.email.MailConfig;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.UnitService;
import eu.factorx.awac.util.email.messages.EmailMessage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import play.Configuration;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.db.jpa.Transactional;
import scala.Option;

@Component
public class EmailSender implements ApplicationContextAware {

	// Application context aware
	private static ApplicationContext ctx = null;
	public static ApplicationContext getApplicationContext() {
		return ctx;
	}
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// Assign the ApplicationContext into a static method
		this.ctx = ctx;
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

//		UnitService unitService = ctx.getBean(UnitService.class);
//		if (unitService==null) {
//			Logger.info("Spring context is null");
//		} else {
//
//			/* getting JPA transaction usinf JPA.with Transaction wrapper - for test purposes*/
//			try {
//				JPA.withTransaction("default", false, new play.libs.F.Function0<Void>() {
//					public Void apply() throws Throwable {
//						UnitService unitService = ctx.getBean(UnitService.class);
//						List<Unit> lu = unitService.findAll();
//						Logger.info("UnitService size = : " + lu.size());
//						return null;
//					}
//				});
//			} catch (Throwable throwable) {
//				throw new RuntimeException(throwable);
//			}

			/* same using Transaction.begin()/commit() - not working */
//			JPA.em().getTransaction().begin();
//			Logger.info("Spring context wired : " + new Date(ctx.getStartupDate()));
//			ctx.getStartupDate();
//			List<Unit> lu = unitService.findAll();
//			Logger.info("UnitService size = : " + lu.size());
//			JPA.em().getTransaction().commit();
//		}

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
            mimeMessage.setSubject(MimeUtility.encodeText(email.getSubject(), "utf-8", "B"));

			// main body part -> email content
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(email.getContent(), "text/html");

			// multipart
			Multipart multipart = new MimeMultipart();

			// add mainbodypart to multipart
			multipart.addBodyPart(messageBodyPart);

			// check if attachments
			if (email.getAttachmentFilenameList()!=null) {
				// for each attachment
				for (String fileName : email.getAttachmentFilenameList()) {
					try {
						Logger.info("Attachment found : " + fileName);
						addAttachment(multipart, fileName);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} // end for
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
	private static void addAttachment(Multipart multipart, String fileName) throws IOException, MessagingException
	{
		MimeBodyPart messageAttachment = new MimeBodyPart();
		messageAttachment.attachFile(fileName);
		messageAttachment.setFileName(fileName);
		multipart.addBodyPart(messageAttachment);
	}
}
