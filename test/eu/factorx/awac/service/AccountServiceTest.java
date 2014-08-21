package eu.factorx.awac.service;

import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountServiceTest extends AbstractBaseModelTest {

	@Autowired
	private AccountService accountService;

    @Test
    public void _001_createAccount() {

		Organization org = new Organization("testing");
		Person person = new Person ("gaston","hollands","gaston.hollands@factorx.eu");
		Account ac = new Account(org,person,"gho","passwd", InterfaceTypeCode.ENTERPRISE);
		ac.setActive(false);

        em.getTransaction().begin();
		em.persist(org);
		em.persist(person);
        em.persist(ac);
        em.getTransaction().commit();

		//Logger.info("Identifier:" + ac.getIdentifier());
		//Logger.info("Id:" + ac.getId());

    } // end of test method

	@Test
	public void _002_retrieveAccountSuccessByService() {

		play.Logger.info("Spring @" + new Date(applicationContext.getStartupDate()));

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
		//AccountServiceImpl accountService = new AccountServiceImpl ();

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

	// for DB cleanup
	@Test
	public void _006_deleteAssociatedPerson() {

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
	} // end of test


} // end of class
