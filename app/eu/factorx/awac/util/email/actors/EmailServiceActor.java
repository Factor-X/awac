package eu.factorx.awac.util.email.actors;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

import javax.mail.MessagingException;

import scala.concurrent.duration.Duration;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Function;
import eu.factorx.awac.util.email.workers.EmailServiceWorker;

public class EmailServiceActor extends UntypedActor {

    /**
     * The actor supervisor strategy attempts to send email up to 10 times if there is a EmailException
     */
    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create("1 minute"),
                    new Function<Throwable, Directive>() {
                        public Directive apply(Throwable t) {
                            if (t instanceof MessagingException) {
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
        getContext().actorOf(new Props(EmailServiceWorker.class)).tell(message, self());
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}

