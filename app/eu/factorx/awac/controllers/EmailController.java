package eu.factorx.awac.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;

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
			EmailMessage email = new EmailMessage(destinationEmail, subject, message);
			//EmailMessage email = new EmailMessage(destinationEmail, subject, message);
			emailService.send(email);
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.error("email", "Confirmation e-mail can not be sent!!!");
			return badRequest();
		}

		return ok();
	}

	/**
	 * Handle the email submission.
	 */
	public Result sendWithAttachments(String destinationEmail, String subject, String message, List<String> filenameList) {

		try {
			// send mail
			EmailMessage email = new EmailMessage(destinationEmail, subject, message, filenameList);
			//EmailMessage email = new EmailMessage(destinationEmail, subject, message);
			emailService.send(email);
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.error("email", "Confirmation e-mail can not be sent!!!");
			return badRequest();
		}

		return ok();
	}
}