package eu.factorx.awac.functional.hooks;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import play.api.Play;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.TestBrowser;
import play.test.TestServer;
import scala.Option;
import play.Logger;

import javax.persistence.EntityManager;

import static play.test.Helpers.*;

public class GlobalHooks {
	public static int PORT = 9000;
	public static TestBrowser TEST_BROWSER;
	private static TestServer TEST_SERVER;
	private static boolean initialised = false;

	public static EntityManager em;

	@Before
	public void before() {
		play.Logger.info("Entering Cucumber GlobalHooks @Before");
		if (!initialised) {
			FakeApplication app = Helpers.fakeApplication();
			Helpers.start(app);
			Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
			em = jpaPlugin.get().em("default");
			Logger.info("GlobalHooks em.isOpen() : " + em.isOpen());
			JPA.bindForCurrentThread(em);
			initialised = true;
		}
	}

	@After
	public void after() {
		play.Logger.info("Entering Cucumber GlobalHooks @After");
		if (initialised) {
			JPA.bindForCurrentThread(null);
			if (em.isOpen()) em.close();
			initialised=false;
		}
	}

	// used for browser tests - to be continued...
	public void startupForBrowser () {
		if (!initialised) {
			TEST_SERVER = testServer(PORT, fakeApplication(inMemoryDatabase()));
			TEST_SERVER = testServer(PORT, fakeApplication());
			TEST_BROWSER = testBrowser(HTMLUNIT, PORT);
			start(TEST_SERVER);
			initialised = true;
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					TEST_BROWSER.quit();
					TEST_SERVER.stop();
				}
			});
		}
	}
}
