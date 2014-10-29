package eu.factorx.awac.util.email.workers;

import java.io.IOException;

import javax.mail.MessagingException;

import akka.actor.UntypedActor;
import eu.factorx.awac.util.email.business.EmailSender;
import eu.factorx.awac.util.email.messages.EmailMessage;

/**
 * Email worker that delivers the message
 */

public class EmailServiceWorker extends UntypedActor {
	
	/**
     * Delivers a message
     */
    @Override
    public void onReceive(Object message) {
    	if (message instanceof EmailMessage)
    	{
    		EmailMessage email = (EmailMessage)message;
    		try {
    			EmailSender sender = new EmailSender();
    			sender.sendEmail(email);
    		} catch (IOException e) {
    			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    		} catch (MessagingException e) {
    			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    		}
    	} else {
    		unhandled(message);
    	}
    }

    @Override
    public void preStart() {
      play.Logger.info("AKKA - entering pre start");
     //getContext().system().scheduler().scheduleOnce(Duration.create(5, TimeUnit.SECONDS), self(), "emailWorker", getContext().system().dispatcher(), null);
    }

    @Override
    public void postStop() {
    	play.Logger.warn("Stopped child email worker after attempts...");
    }
}
