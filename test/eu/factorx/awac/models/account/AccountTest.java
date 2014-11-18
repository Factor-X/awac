package eu.factorx.awac.models.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

//import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import play.Logger;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.impl.AccountServiceImpl;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountTest extends AbstractBaseModelTest {


    @Test
    public void _001_createAccount() {

        Organization org = new Organization("testing",InterfaceTypeCode.ENTERPRISE);

		Person person = new Person ("gaston","hollands","gaston.hollands@factorx.eu");
        Account ac = new Account(org,person,"gho","passwd");
		ac.setActive(false);

		em.persist(org);
		em.persist(person);
        em.persist(ac);

		//Logger.info("Identifier:" + ac.getIdentifier());
		//Logger.info("Id:" + ac.getId());

    } // end of test method

	@Test
	public void _002_retrieveAccountSuccessByService() {
		AccountServiceImpl accountService = new AccountServiceImpl ();

		Account account = null;
		account = accountService.findByIdentifier("gho");
		assertEquals(account.getIdentifier(), "gho");
	} // end of test method

    @Test
    public void _003_retrieveAccountSuccess() {

		Account account = null;

        String query = "select a from Account a where a.identifier = 'gho'";
		try {
			account = em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("account is null");
		}
		assertEquals(account.getIdentifier(), "gho");
    } // end of test

	@Test
	public void _004_updateAccount() {

		// Create
		//em.getTransaction().begin();
		//em.persist(new Organization("carrefour"));
		//em.getTransaction().commit();

		// Check persistence
		Account account = null;
		String query1 = "select a from Account a where a.identifier = 'gho'";

		try {
			account = em.createQuery(query1, Account.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(account.getIdentifier(), "gho");

		// update org name
		account.setIdentifier("ghoModified");
		em.persist(account);

		// Check update persistence
		Account modifiedAcc = null;
		String query2 = "select a from Account a where a.identifier = 'ghoModified'";

		try {
			modifiedAcc = em.createQuery(query2, Account.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(modifiedAcc.getIdentifier(), "ghoModified");

	} // end of test method

	@Test
	public void _005_deleteAccount() {

		Account account = null;
		String query = "select a from Account a where a.identifier = 'ghoModified'";

		try {
			account = em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(account.getIdentifier(), "ghoModified");

		em.remove(account);

		Account reload=null;

		try {
			reload = em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	} // end of test


	@Test
	public void _006_retrieveAccountFailureByService() {
		AccountServiceImpl accountService = new AccountServiceImpl ();

		Account account = null;
		account = accountService.findByIdentifier("ghoModified");
		assertNull(account);
	} // end of test method

    @Test
    public void _007_retrieveAccountFailure() {

        Account account = null;
        String query = "select a from Account a where a.identifier = 'ghoModified'";

        try {
            account = em.createQuery(query, Account.class).getResultList().get(0);
        } catch (Exception empty) {}

        assertNull(account);
    } // end of test

	// for DB cleanup
	@Test
	public void _008_deleteAssociatedOrganization() {

		Organization org = null;
		String query = "select o from Organization o where o.name = 'testing'";

		try {
			org = em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(org.getName(), "testing");

		em.remove(org);

		Organization reload=null;

		try {
			reload = em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	} // end of test

	// for DB cleanup
	@Test
	public void _009_deleteAssociatedPerson() {

		Person person = null;
		String query = "select p from Person p where p.lastname = 'gaston'";

		try {
			person = em.createQuery(query, Person.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertEquals(person.getLastname(), "gaston");

		em.remove(person);

		Person reload=null;

		try {
			reload = em.createQuery(query, Person.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	} // end of test

} // end of class
