package eu.factorx.awac.util.batch.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.SmallestMailboxRouter;
import eu.factorx.awac.util.batch.actors.ComputeAverageServiceActor;
import eu.factorx.awac.util.batch.messages.ComputeAverageMessage;
import eu.factorx.awac.util.email.actors.EmailServiceActor;
import eu.factorx.awac.util.email.messages.EmailMessage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import play.Logger;
import play.Play;

import javax.inject.Named;
import java.io.IOException;
import java.util.Date;

import static eu.factorx.awac.util.batch.SpringExtension.SpringExtProvider;

// annotate as Spring Service for IOC

@Service
public class ComputeAverageService implements ApplicationContextAware {

    // Application context aware
    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // Assign the ApplicationContext into a static method
       this.context = ctx;
    }

    public ActorSystem system;
    public ActorRef batchActorRef;

    /**
     * Uses the smallest inbox strategy to keep 20 instances alive ready to send out email
     * @see akka.routing.SmallestMailboxRouter
     */

    public ComputeAverageService() throws IOException {

    }

    /**
     * public interface to send out emails that dispatch the message to the listening actors
     * @param computeAverageMessage the computeAverage message
     */
    
    public void send(ComputeAverageMessage computeAverageMessage) {
        initialize();
        batchActorRef.tell(computeAverageMessage, batchActorRef);
    }

    private void initialize() {

        if (context==null) {
            Logger.info("Spring context is null");
        } else {
            Logger.info("Spring context is not null");
        }


        // get hold of the actor system
        system = context.getBean(ActorSystem.class);
        //system = ActorSystem.create("awacbatchsystem");

        // use the Spring Extension to create props for a named actor bean
        batchActorRef = system.actorOf(
                SpringExtProvider.get(system).props("ComputeAverageServiceActor").
                        withRouter(new SmallestMailboxRouter(1))
                ,"batchService");

//        batchActorRef = system.actorOf(new Props(ComputeAverageServiceActor.class).withRouter
//                (new SmallestMailboxRouter(1)), "batchService");
    }
}
