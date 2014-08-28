package eu.factorx.awac.cucumber.trashbin.withplugin;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.Helpers;
import play.test.TestBrowser;
import play.test.TestServer;
import scala.Option;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.*;
import static play.test.Helpers.start;

public class AccountBisSteps {

	private static String DOMAIN = "awac/enterprise";
	private static int PORT = 9000;

	//private TestServer testServer = testServer(PORT, fakeApplication(inMemoryDatabase()));

	//private TestServer testServer = testServer(PORT, fakeApplication());
	play.test.FakeApplication app = Helpers.fakeApplication();
	private TestServer testServer = testServer(PORT, app);
	private TestBrowser testBrowser = testBrowser(HTMLUNIT, PORT);

	private static boolean initialised = false;
	public static EntityManager em;

	@Before
	public void before() {
		play.Logger.info("Entering Cucumber @Before");
		if (!initialised) {
			//FakeApplication app = Helpers.fakeApplication();
			//FakeApplication app = testServer.application();
			startupForBrowser();
			//Helpers.start(app);
			Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
			em = jpaPlugin.get().em("default");
			Logger.info("em.isOpen() : " + em.isOpen());
			JPA.bindForCurrentThread(em);
			initialised = true;
		}
	}

	//@After
	public void after() {
		play.Logger.info("Entering Cucumber @After");
		if (initialised) {
			JPA.bindForCurrentThread(null);
			if (em.isOpen()) em.close();
			initialised=false;
		}
	}

	// used for browser tests - to be continued...
	public void startupForBrowser () {
		if (!initialised) {

			start(testServer);

			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					testBrowser.quit();
					testServer.stop();
				}
			});
		}
	}

	Account accountCheck=null;

	@Given("^bis I have created new account$")
	public void I_have_created_new_account() throws Throwable {
		// Express the Regexp above with the code you wish you had

		Organization org = new Organization("testing");
		Person person = new Person ("gaston","hollands","gaston.hollands@factorx.eu");
		Account ac = new Account(org,person,"gho","passwd", InterfaceTypeCode.ENTERPRISE);
		ac.setActive(false);

		em.getTransaction().begin();
		em.persist(org);
		em.persist(person);
		em.persist(ac);
		em.getTransaction().commit();
	}

	@When("^bis I have search new account$")
	public void I_have_search_new_account() throws Throwable {
		Account account = null;

		String query = "select a from Account a where a.identifier = 'gho'";
		try {
			accountCheck = em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("account is null");
		}
		//assertEquals(account.getIdentifier(), "gho");
	}

	@Then("^bis The account should be \"([^\"]*)\"$")
	public void The_account_should_be(String accountIdentifier) throws Throwable {
		// Express the Regexp above with the code you wish you had
		//throw new PendingException();
		assertEquals(accountIdentifier,accountCheck.getIdentifier());
	}

	@Then("^bis Perform delete of the account$")
	public void Perform_delete_of_the_account() throws Throwable {
		// Express the Regexp above with the code you wish you had
		Account account = null;
		String query = "select a from Account a where a.identifier = 'gho'";

		try {
			account = em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(account.getIdentifier(), "gho");

		em.getTransaction().begin();
		em.remove(account);
		em.getTransaction().commit();

		Account reload=null;

		try {
			reload = em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	}

	@Then("^bis Perform delete of the organization$")
	public void Perform_delete_of_the_organization() throws Throwable {
		Organization org = null;
		String query = "select o from Organization o where o.name = 'testing'";

		try {
			org = em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(org.getName(), "testing");

		em.getTransaction().begin();
		em.remove(org);
		em.getTransaction().commit();

		Organization reload=null;

		try {
			reload = em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	}
	@Then("^bis Perform delete of the person$")
	public void Perform_delete_of_the_person() throws Throwable {
		Person person = null;
		String query = "select p from Person p where p.lastname = 'gaston'";

		try {
			person = em.createQuery(query, Person.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(person.getLastname(), "gaston");

		em.getTransaction().begin();
		em.remove(person);
		em.getTransaction().commit();

		Person reload=null;

		try {
			reload = em.createQuery(query, Person.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	}


}