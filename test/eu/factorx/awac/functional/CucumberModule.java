package eu.factorx.awac.functional;

import static play.test.Helpers.*;
import play.test.Helpers;
import play.test.TestBrowser;
import play.test.TestServer;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class CucumberModule extends AbstractModule {
	private static int PORT = 9000;
	private static String DOMAIN = "awac/calculator";

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