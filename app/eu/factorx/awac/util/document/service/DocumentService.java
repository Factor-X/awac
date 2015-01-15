package eu.factorx.awac.util.document.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.SmallestMailboxRouter;
import eu.factorx.awac.util.document.actors.DocumentServiceActor;
import eu.factorx.awac.util.document.messages.PDFDocumentMessage;
import eu.factorx.awac.util.document.messages.XLSDocumentMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;

// annotate as Spring Service for IOC
@Service
public class DocumentService {

	public ActorSystem system;
	public ActorRef DocumentActorRef;

	/**
	 * Uses the smallest inbox strategy to keep 20 instances alive ready to send out Document
	 *
	 * @see akka.routing.SmallestMailboxRouter
	 */

	public DocumentService() throws IOException {
		system = ActorSystem.create("documentsystem");
		DocumentActorRef = system.actorOf(new Props(DocumentServiceActor.class).withRouter
			(new SmallestMailboxRouter(1)), "DocumentService");
	}

	/**
	 * public interface to send out Documents that dispatch the message to the listening actors
	 *
	 * @param Document the Document message
	 */

	public void send(PDFDocumentMessage Document) {
		DocumentActorRef.tell(Document, DocumentActorRef);
	}
	public void send(XLSDocumentMessage Document) {
		DocumentActorRef.tell(Document, DocumentActorRef);
	}
}
