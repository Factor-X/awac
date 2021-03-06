package eu.factorx.awac.models.association;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import play.Logger;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

//import java.util.List;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssociationTest extends AbstractBaseModelTest {

	private final String SITE_NAME = "NAMEP3";
	private final String ORGANIZATION_NAME = "ORGTEST";
	private final String IDENTIFIER1 = "user1";
	private final String IDENTIFIER2 = "user2";
	private final String LASTNAME = "gaston";

    @Test
    public void _001_createAssociation() {

		Organization org = new Organization(ORGANIZATION_NAME, InterfaceTypeCode.ENTERPRISE);

		Account ac = new Account(org,LASTNAME,"hollands","gaston.hollands@factorx.eu",IDENTIFIER1,"passwd");
		ac.setActive(false);

		em.persist(org);

		// verify
		String query;
		org = null;
		query = "select o from Organization o where o.name = '" + ORGANIZATION_NAME + "'";
		try {
			org = em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("organization is null");
		}
		assertNotNull(org);

		em.persist(ac);

		Account acc=null;
		query = "select a from Account a where a.identifier = '" + IDENTIFIER1 + "'";
		try {
			acc = em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("account is null");
		}
		assertNotNull(acc);


		Site site = new Site(org,SITE_NAME);
		site.setOrganizationalStructure("organization_structure");
		em.persist(site);

		site = null;
		query = "select s from Site s where s.name = '" + SITE_NAME + "'";
		try {
			site = em.createQuery(query, Site.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("site is null");
		}
		assertNotNull(site);

		AccountSiteAssociation asa = new AccountSiteAssociation(site,ac);
		em.persist(asa);

		AccountSiteAssociation accountSiteAssociation = null;
		query = "select a from AccountSiteAssociation a where a.site.id = " + site.getId() ;
		try {
			accountSiteAssociation = em.createQuery(query, AccountSiteAssociation.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("association is null");
		}

		assertNotNull(accountSiteAssociation);


    } // end of test method




	// for DB cleanup
	@Test
	public void _004_deleteAll() {

		Account acc = null;
		String query;

		query = "select a from Account a where a.identifier = '" + IDENTIFIER1 + "'";
		try {
			acc = em.createQuery(query, Account.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("account is null");
		}
		assertNotNull(acc);

		Organization org = null;
		query = "select o from Organization o where o.name = '" + ORGANIZATION_NAME + "'";
		try {
			org = em.createQuery(query, Organization.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("organization is null");
		}
		assertNotNull(org);

		Site site = null;
		query = "select s from Site s where s.name = '" + SITE_NAME + "'";
		try {
			site = em.createQuery(query, Site.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("site is null");
		}
		assertNotNull(site);

		//em.remove(site);
		//em.remove(org);
		em.remove(acc);


		AccountSiteAssociation accountSiteAssociation = null;

		query = "select a from AccountSiteAssociation a where a.site.id = " + site.getId() ;
		try {
			accountSiteAssociation = em.createQuery(query, AccountSiteAssociation.class).getResultList().get(0);
		} catch (Exception empty) {
			Logger.info("association is null");
		}

		assertNotNull(accountSiteAssociation);
		em.remove(accountSiteAssociation);


		AccountSiteAssociation reload=null;
		try {
			reload = em.createQuery(query, AccountSiteAssociation.class).getResultList().get(0);
		} catch (Exception empty) {}

		assertNull(reload);
	} // end of test

} // end of class
