package eu.factorx.awac.controllers;

/**
 * Created by gaston on 8/20/14.
 */

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.util.email.service.EmailService;
import eu.factorx.awac.util.email.messages.EmailMessage;
import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

import eu.factorx.awac.models.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

//annotate as Spring Component
@Component
public class EmailController {

	@Autowired
	private EmailService emailService;

	/**
	 * Handle the form submission.
	 */
	public Result submit() {

			try {
				// send mail
				EmailMessage email = new EmailMessage("gaston.hollands@factorx.eu","AWAC Registration Confirmation","http://localhost:9000/easychat/confirm/"+"1");
				emailService.send(email);
			} catch (Exception ex) {
				ex.printStackTrace();
				Logger.error("email", "Confirmation e-mail can not be sent!!!");
				return badRequest();
			}

			return ok();
		}

	/**
	 * Handle the confirm.
	 */

	public Result confirm (Long id) {

		//send confirmation mail of activated account
		EmailMessage email = new EmailMessage("gaston.hollands@factorx.eu","AWAC Registration Activation","Your account is activated. Please log in the system using http://localhost:9000/awac/login");
		emailService.send(email);
		return ok();
	}

}