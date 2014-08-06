package eu.factorx.awac.functional.account;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import eu.factorx.awac.functional.hooks.GlobalHooks;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import play.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountSteps {

	Account accountCheck=null;

	@Given("^I have created new account$")
	public void I_have_created_new_account() throws Throwable {
		// Express the Regexp above with the code you wish you had

		Organization org = new Organization("testing");
		Account ac = new Account(org,"gho","passwd","gaston","hollands");
		ac.setAge(new Integer(20)); // constraints should it be a constraint ?

		GlobalHooks.em.getTransaction().begin();
		GlobalHooks.em.persist(ac);
		GlobalHooks.em.getTransaction().commit();
	}

	@When("^I have search new account$")
	public void I_have_search_new_account() throws Throwable {
		Account account = null;

		String query = "select a from Account a where a.identifier = 'gho'";
		try {
			accountCheck = GlobalHooks.em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("account is null");
		}
		//assertEquals(account.getIdentifier(), "gho");
	}

	@Then("^The account should be \"([^\"]*)\"$")
	public void The_account_should_be(String accountIdentifier) throws Throwable {
		// Express the Regexp above with the code you wish you had
		//throw new PendingException();
		assertEquals(accountIdentifier,accountCheck.getIdentifier());
	}

	@Then("^Perform delete of the account$")
	public void Perform_delete_of_the_account() throws Throwable {
		// Express the Regexp above with the code you wish you had
		Account account = null;
		String query = "select a from Account a where a.identifier = 'gho'";

		try {
			account = GlobalHooks.em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(account.getIdentifier(), "gho");

		GlobalHooks.em.getTransaction().begin();
		GlobalHooks.em.remove(account);
		GlobalHooks.em.getTransaction().commit();

		Account reload=null;

		try {
			reload = GlobalHooks.em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	}

	@Then("^Perform delete of the organization$")
	public void Perform_delete_of_the_organization() throws Throwable {
		Organization org = null;
		String query = "select o from Organization o where o.name = 'testing'";

		try {
			org = GlobalHooks.em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(org.getName(), "testing");

		GlobalHooks.em.getTransaction().begin();
		GlobalHooks.em.remove(org);
		GlobalHooks.em.getTransaction().commit();

		Organization reload=null;

		try {
			reload = GlobalHooks.em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	}


}