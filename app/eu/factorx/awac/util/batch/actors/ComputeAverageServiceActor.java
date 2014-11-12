package eu.factorx.awac.util.batch.actors;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.*;
import akka.actor.UntypedActor;
import akka.japi.Function;
import akka.routing.SmallestMailboxRouter;
import eu.factorx.awac.util.batch.workers.ComputeAverageServiceWorker;
import eu.factorx.awac.util.email.workers.EmailServiceWorker;
import org.springframework.context.annotation.Scope;
import play.Logger;
import scala.concurrent.duration.Duration;

import javax.inject.Named;
import jxl.write.WriteException;

import static eu.factorx.awac.util.batch.SpringExtension.SpringExtProvider;

@Named("ComputeAverageServiceActor")
@Scope("prototype")
public class ComputeAverageServiceActor extends UntypedActor {

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

    /**
     * Forwards messages to child workers
     */
    
    @SuppressWarnings("deprecation")
	@Override
    public void onReceive(Object message) {

        Logger.info("entering onReceive() from ComputeverageServiceActor");
        getContext().actorOf(new Props(ComputeAverageServiceWorker.class)).tell(message, self());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}

