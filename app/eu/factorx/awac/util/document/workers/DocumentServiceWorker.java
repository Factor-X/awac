package eu.factorx.awac.util.document.workers;

import java.io.IOException;

import javax.mail.MessagingException;

import akka.actor.UntypedActor;
import eu.factorx.awac.util.document.business.DocumentSender;
import eu.factorx.awac.util.document.messages.DocumentMessage;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

/**
 * Document worker that delivers the message
 */

public class DocumentServiceWorker extends UntypedActor {

	/**
     * Delivers a message
     */
    @Override
    public void onReceive(Object message) {
    	if (message instanceof DocumentMessage)
    	{
    		DocumentMessage Document = (DocumentMessage)message;
    		try {
    			DocumentSender sender = new DocumentSender();
    			sender.generateDocument(Document);
    		} catch (IOException e) {
    			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    		} catch (WriteException e) {
				e.printStackTrace();
			} catch (BiffException e) {
				e.printStackTrace();
			}
		} else {
    		unhandled(message);
    	}
    }

    @Override
    public void preStart() {
      play.Logger.info("AKKA - entering pre start");
    }

    @Override
    public void postStop() {
    	play.Logger.warn("Stopped child Document worker after attempts...");
    }
}
