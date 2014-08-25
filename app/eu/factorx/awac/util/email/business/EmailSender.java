package eu.factorx.awac.util.email.business;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.email.MailConfig;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.util.email.messages.EmailMessage;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import play.Configuration;
import play.Logger;

public class EmailSender {

	private static HashMap<String, CodeLabel> emailCodeList = null;

    private static final String MAIL_SMTP_AUTH_KEY = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_KEY = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_HOST_KEY = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT_KEY = "mail.smtp.port";

	private static final String emailTemplatePath = "/public/template/email.vm";

	//TODO to paramater
	private static final String mainUrl = "http://awac-accept.herokuapp.com/";
	private static CodeLabel mainTitle = null;
	private static CodeLabel footerContent = null;

	@Autowired
	private CodeLabelService codeLabelService;

    public EmailSender() throws IOException  {
        MailConfig.loadConfigurations();

		Spring.

		if(emailCodeList == null){
			codeLabelService =
			emailCodeList = codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_EMAIL_MESSAGE);
			footerContent = emailCodeList.get("EMAIL_FOOTER");
			mainTitle = emailCodeList.get("EMAIL_TITLE");
		}
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

			if(email.getFromInterface()!=null){
				String content = getEmail(email.getContent(),email.getFromInterface(),email.getLanguageCode());
				mimeMessage.setContent(content, "text/html; charset=utf-8");
			}
			else{
				mimeMessage.setText(email.getContent());
			}


            Transport.send(mimeMessage);

            play.Logger.info("Email Successfully sent to " + email.getToAddress());

        } catch (MessagingException e) {
            throw e;
        }

    }

	public String getEmail(String content,InterfaceTypeCode fromInterface, LanguageCode languageCode){

		VelocityEngine ve = new VelocityEngine();

		Template t = ve.getTemplate(emailTemplatePath);

		VelocityContext context = new VelocityContext();
		context.put("footerContent", footerContent.getLabel(languageCode));
		context.put("mainTitle", mainTitle);
		context.put("mainUrl", mainUrl+"/"+fromInterface+"/");
		context.put("bodyContent", content);

		StringWriter writer = new StringWriter();
		t.merge( context, writer );

		return writer.toString();
	}
}
