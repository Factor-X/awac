package eu.factorx.awac.models.account;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.impl.AccountServiceImpl;
import play.Logger;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


//import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountTest extends AbstractBaseModelTest {


    @Test
    public void _001_createAccount() {

        Organization org = new Organization("testing");

		Person person = new Person ("gaston","hollands","gaston.hollands@factorx.eu");
        Account ac = new Account(org,person,"gho","passwd",InterfaceTypeCode.ENTERPRISE);
		ac.setActive(false);

        em.getTransaction().begin();
        em.persist(ac);
        em.getTransaction().commit();

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
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();

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

		em.getTransaction().begin();
		em.remove(account);
		em.getTransaction().commit();

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

		em.getTransaction().begin();
		em.remove(org);
		em.getTransaction().commit();

		Organization reload=null;

		try {
			reload = em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	} // end of test

} // end of class
