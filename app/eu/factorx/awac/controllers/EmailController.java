package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.util.email.service.EmailService;
import eu.factorx.awac.util.email.messages.EmailMessage;
import play.*;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

import eu.factorx.awac.models.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeUtility;

//annotate as Spring Component
@Component
@Transactional(readOnly = true)
@Security.Authenticated(SecuredController.class)
@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
public class EmailController extends AbstractController {

	@Autowired
	private EmailService emailService;

	/**
	 * Handle the email submission.
	 */
	public Result send(String destinationEmail, String subject, String message) {

		try {
			// send mail
			EmailMessage email = new EmailMessage(destinationEmail, MimeUtility.encodeText(subject, "utf-8", "B"), message);
			//EmailMessage email = new EmailMessage(destinationEmail, subject, message);
			emailService.send(email);
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.error("email", "Confirmation e-mail can not be sent!!!");
			return badRequest();
		}

		return ok();
	}

	public Result sendComplete(String destinationEmail,String subject, String message,String interfaceName,String languageKey) {

		try {
			// send mail
			// TODO waiting refactoring EmailMessage email = new EmailMessage(destinationEmail,subject,message, new InterfaceTypeCode(interfaceName),new LanguageCode(languageKey));
			// emailService.send(email);
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.error("email", "Confirmation e-mail can not be sent!!!");
			return badRequest();
		}

		return ok();
	}
}