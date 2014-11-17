package eu.factorx.awac.util.batch.workers;

import akka.actor.UntypedActor;

import eu.factorx.awac.business.ComputeAverage;
import eu.factorx.awac.util.batch.CountingService;
import eu.factorx.awac.util.batch.messages.ComputeAverageMessage;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

/**
 * Email worker that delivers the message
 */

@Named("ComputeAverageServiceWorker")
@Scope("prototype")
public class ComputeAverageServiceWorker extends UntypedActor {


    @Autowired
    private ComputeAverage computeAverageService;

	/**
     * Delivers a message
     */
    @Override
    public void onReceive(Object message) {
        Logger.info("entering onReceive() from ComputeAverageServiceWorker");
        if (message instanceof ComputeAverageMessage)
    	{
    		ComputeAverageMessage msg = (ComputeAverageMessage)message;
    		try {
    			computeAverageService.computeAverage(
                        msg.getAccount(),
                        msg.getAwacCalculator(),
                        msg.getScopeAndPeriodList(),
                        msg.getPeriod(),
                        msg.getOrganizationComputed(),
                        msg.getScopeComputed());
    		} catch (IOException e) {
    			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    		} catch (WriteException e) {
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
    	play.Logger.warn("Stopped child batch worker after attempts...");
    }
}
