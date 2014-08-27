package eu.factorx.awac.functional;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.TestBrowser;
import play.test.TestServer;
import scala.Option;
import play.Logger;

import javax.persistence.EntityManager;

import static play.test.Helpers.*;

public class GlobalHooks {

	@Inject
	private TestBrowser testBrowser;
	@Inject
	private TestServer testServer;
	@Inject
	private FakeApplication app;
	@Inject
	@Named("PORT")
	private Integer port;


	private static boolean initialised = false;
	public static EntityManager em;

	@Before
	public void before() {
		play.Logger.info("Entering Cucumber GlobalHooks @Before");
		if (!initialised) {
			//FakeApplication app = Helpers.fakeApplication();
			//FakeApplication app = testServer.application();
			startupForBrowser();
			//Helpers.start(app);
			Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
			em = jpaPlugin.get().em("default");
			Logger.info("GlobalHooks em.isOpen() : " + em.isOpen());
			JPA.bindForCurrentThread(em);
			initialised = true;
		}
	}

//	@After
//	public void after() {
//		play.Logger.info("Entering Cucumber GlobalHooks @After");
//		if (initialised) {
//			JPA.bindForCurrentThread(null);
//			//if (em.isOpen()) em.close();
//			play.Logger.info("stopping browser and server");
//			initialised=false;
//		}
//	}

	// used for browser tests - to be continued...
	public void startupForBrowser () {
		if (!initialised) {

			play.Logger.info("Staring server");
			start(testServer);

			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					play.Logger.info("stopping browser and server");
					testBrowser.quit();
					testServer.stop();
				}
			});
		}
	}
}
