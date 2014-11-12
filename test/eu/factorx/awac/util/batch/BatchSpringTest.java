package eu.factorx.awac.util.batch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import static akka.pattern.Patterns.ask;
import akka.util.Timeout;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import eu.factorx.awac.util.batch.CountingActor.Count;
import eu.factorx.awac.util.batch.CountingActor.Get;
import static eu.factorx.awac.util.batch.SpringExtension.SpringExtProvider;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BatchSpringTest extends eu.factorx.awac.controllers.AbstractBaseControllerTest {
    @Test
    public void testSpring() throws Exception {
        // create a spring context and scan the classes
//        AnnotationConfigApplicationContext ctx =
//                new AnnotationConfigApplicationContext();
//        ctx.scan("eu.factorx.awac");
//        ctx.refresh();

        // get hold of the actor system
        ActorSystem system = applicationContext.getBean(ActorSystem.class);
        // use the Spring Extension to create props for a named actor bean
        ActorRef counter = system.actorOf(
                SpringExtProvider.get(system).props("CountingActor"), "counter");

        // tell it to count three times
        counter.tell(new Count(), null);
        counter.tell(new Count(), null);
        counter.tell(new Count(), null);

        // check that it has counted correctly
        FiniteDuration duration = FiniteDuration.create(3, TimeUnit.SECONDS);
        Future<Object> result = ask(counter, new Get(),
                Timeout.durationToTimeout(duration));
        assertEquals(3, Await.result(result, duration));

        // shut down the actor system
        system.shutdown();
        system.awaitTermination();
    }
}