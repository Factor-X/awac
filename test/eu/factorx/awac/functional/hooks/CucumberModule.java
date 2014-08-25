package eu.factorx.awac.functional.hooks;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import play.api.test.FakeApplication;
import play.test.Helpers;
import play.test.TestBrowser;
import play.test.TestServer;
import static play.test.Helpers.*;

public class CucumberModule extends AbstractModule {
	private static int PORT = 9000;
	private static String DOMAIN = "awac/enterprise";

	//private TestServer testServer = testServer(PORT, fakeApplication(inMemoryDatabase()));

	//private TestServer testServer = testServer(PORT, fakeApplication());
	play.test.FakeApplication app = Helpers.fakeApplication();
	private TestServer testServer = testServer(PORT, app);
	private TestBrowser testBrowser = testBrowser(HTMLUNIT, PORT);

	@Override
	protected void configure() {

		bind(TestBrowser.class).toInstance(testBrowser);
		bind(TestServer.class).toInstance(testServer);
		bind(play.test.FakeApplication.class).toInstance(app);

		bind(Integer.class).annotatedWith(Names.named("PORT")).toInstance(PORT);
		bind(String.class).annotatedWith(Names.named("DOMAIN")).toInstance(DOMAIN);
	}
}