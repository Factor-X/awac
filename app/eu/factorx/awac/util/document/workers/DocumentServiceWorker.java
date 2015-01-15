package eu.factorx.awac.util.document.workers;

import java.io.IOException;

import akka.actor.UntypedActor;
import eu.factorx.awac.util.document.business.DocumentSender;
import eu.factorx.awac.util.document.messages.PDFDocumentMessage;
import eu.factorx.awac.util.document.messages.XLSDocumentMessage;
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
    	if (message instanceof PDFDocumentMessage)
    	{
    		PDFDocumentMessage Document = (PDFDocumentMessage)message;
    		try {
    			DocumentSender sender = new DocumentSender();
    			sender.generatePDFDocument(Document);
    		} catch (IOException e) {
    			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    		} catch (WriteException e) {
				e.printStackTrace();
			} catch (BiffException e) {
				e.printStackTrace();
			}
		} else if (message instanceof XLSDocumentMessage)
		{
			XLSDocumentMessage Document = (XLSDocumentMessage)message;
			try {
				DocumentSender sender = new DocumentSender();
				sender.generateXLSDocument(Document);
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
