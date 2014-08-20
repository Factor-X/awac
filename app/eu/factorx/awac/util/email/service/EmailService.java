package eu.factorx.awac.util.email.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.SmallestMailboxRouter;
import eu.factorx.awac.util.email.actors.EmailServiceActor;
import eu.factorx.awac.util.email.messages.EmailMessage;

// annotate as Spring Service for IOC
@Service
public class EmailService {


    public ActorSystem system;
    public ActorRef emailActorRef;

    /**
     * Uses the smallest inbox strategy to keep 20 instances alive ready to send out email
     * @see SmallestMailboxRouter
     */
    
    public EmailService() throws IOException {
        system = ActorSystem.create("easychatsystem");
        emailActorRef = system.actorOf(new Props(EmailServiceActor.class).withRouter
                (new SmallestMailboxRouter(1)), "emailService");
    }

    /**
     * public interface to send out emails that dispatch the message to the listening actors
     * @param email the email message
     */
    
    public void send(EmailMessage email) {
        emailActorRef.tell(email, emailActorRef);
    }
}
