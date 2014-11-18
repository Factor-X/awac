package eu.factorx.awac.util.batch.actors;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

import akka.actor.*;
import akka.actor.SupervisorStrategy.*;
import akka.japi.Function;
import akka.routing.SmallestMailboxRouter;
import eu.factorx.awac.util.batch.workers.ComputeAverageServiceWorker;
import eu.factorx.awac.util.email.workers.EmailServiceWorker;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import play.Logger;
import scala.concurrent.duration.Duration;

import javax.inject.Named;
import jxl.write.WriteException;

import static eu.factorx.awac.util.batch.SpringExtension.SpringExtProvider;

@Named("ComputeAverageServiceActor")
@Scope("prototype")
public class ComputeAverageServiceActor extends UntypedActor implements ApplicationContextAware {

    /**
     * The actor supervisor strategy attempts to send email up to 10 times if there is a EmailException
     */
    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create("1 minute"),
                    new Function<Throwable, Directive>() {
                        public Directive apply(Throwable t) {
                            if (t instanceof WriteException) {
                            	play.Logger.error("Restarting after receiving EmailException : {}" + t.getMessage());
                                return resume();
                            } else if (t instanceof Exception) {
                            	play.Logger.error("Giving up. Can you recover from this? : {}" + t.getMessage());
                                return stop();
                            } else {
                            	play.Logger.error("Giving up on unexpected case : {}" + t.getMessage());
                                return escalate();
                            }
                        }
                    });

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
     * Forwards messages to child workers
     */
    
    @SuppressWarnings("deprecation")
	@Override
    public void onReceive(Object message) {

        Logger.info("entering onReceive() from ComputeAverageServiceActor");
        //getContext().actorOf(new Props(ComputeAverageServiceWorker.class)).tell(message, self());

        // get hold of the actor system
        system = context.getBean(ActorSystem.class);

        batchActorRef = system.actorOf(
                SpringExtProvider.get(system).props("ComputeAverageServiceWorker"));

        batchActorRef.tell(message,self());

    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}

