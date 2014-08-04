package eu.factorx.awac.service;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.service.impl.AccountServiceImpl;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import play.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

//import java.util.List;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountServiceTest extends AbstractBaseModelTest {


    @Test
    public void _001_createAccount() {

        Organization org = new Organization("testing");
        Account ac = new Account(org,"gho","passwd","gaston","hollands");
        ac.setAge(new Integer(20)); // constraints should it be a constraint ?

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
	public void _003_deleteAccount() {

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
	} // end of test


	@Test
	public void _004_retrieveAccountFailureByService() {
		AccountServiceImpl accountService = new AccountServiceImpl ();

		Account account = null;
		account = accountService.findByIdentifier("gho");
		assertNull(account);
	} // end of test method



	// for DB cleanup
	@Test
	public void _005_deleteAssociatedOrganization() {

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
