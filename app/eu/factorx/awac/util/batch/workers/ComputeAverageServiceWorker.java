package eu.factorx.awac.util.batch.workers;

import akka.actor.UntypedActor;

import eu.factorx.awac.business.ComputeAverage;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.service.UnitService;
import eu.factorx.awac.util.batch.CountingService;
import eu.factorx.awac.util.batch.messages.ComputeAverageMessage;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import play.Application;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.libs.Akka;
import scala.Option;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Email worker that delivers the message
 */

@Named("ComputeAverageServiceWorker")
@Scope("prototype")
public class ComputeAverageServiceWorker extends UntypedActor {


    @Autowired
    private ComputeAverage computeAverageService;

    @Autowired
    private UnitService unitService;


    /**
     * Delivers a message
     */
    @Override
    public void onReceive(final Object message) {
        Logger.info("entering onReceive() from ComputeAverageServiceWorker");
        if (message instanceof ComputeAverageMessage)
    	{

            Akka.system().scheduler().scheduleOnce (
                    Duration.create(0, TimeUnit.MILLISECONDS),
                    new Runnable() {
                        @Override
                        public void run() {
                            Logger.info("BATCH RUN ONCE STARTED ---    " + System.currentTimeMillis());


                            /**********************/
//                            try {
//                                JPA.withTransaction("default", false, new play.libs.F.Function0<Void>() {
//                                    public Void apply() throws Throwable {

                                        EntityManager em;
                                        Application app = Play.application();
                                        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
                                        em = jpaPlugin.get().em("default");
                                        JPA.bindForCurrentThread(em);

                                        //em.getTransaction()

                                        //em.getTransaction().begin();

                                        List<Unit> lu = unitService.findAll();
                                        Logger.info("UnitService size = : " + lu.size());


                                                                ComputeAverageMessage msg = (ComputeAverageMessage) message;
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
                                        //em.getTransaction().commit();

//                                        return null;
//                                    }
//                                });
//                            } catch (Throwable throwable) {
//                                throw new RuntimeException(throwable);
//                            }

                            /********************************/
                        } // end of run()
                    } // end of Runnable
                    , Akka.system().dispatcher()
                ); // end of scheduleOnce()

        }else
            {
              unhandled(message);
            }
} // end of onReceive()

    @Override
    public void preStart() {
      //play.Logger.info("AKKA - entering pre start");
     //getContext().system().scheduler().scheduleOnce(Duration.create(5, TimeUnit.SECONDS), self(), "emailWorker", getContext().system().dispatcher(), null);
    }

    @Override
    public void postStop() {
    	play.Logger.warn("Stopped child batch worker after attempts...");
    }
}
