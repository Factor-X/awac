package eu.factorx.awac.service;

import static junit.framework.TestCase.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import play.Logger;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountSiteAssociationServiceTest extends AbstractBaseModelTest {

	@Autowired
	private AccountSiteAssociationService accountSiteAssociationService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PersonService personService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private ScopeService scopeService;



	@Test
    public void _001_createAccountSiteAssociation() {

		Organization org = new Organization ("testing", InterfaceTypeCode.ENTERPRISE);
		organizationService.saveOrUpdate(org);

		Person person = new Person ("gaston","hollands","gaston.hollands@factorx.eu");
		personService.saveOrUpdate(person);

		Account ac = new Account(org,person,"gho","passwd");
		ac.setActive(false);
		accountService.saveOrUpdate(ac);

		Person person2 = new Person ("gaston2","hollands2","gaston2.hollands@factorx.eu");
		personService.saveOrUpdate(person2);

		Account ac2 = new Account(org,person2,"gho2","passwd2");
		ac2.setActive(false);
		accountService.saveOrUpdate(ac2);

		Site site = new Site(org, "aSite");
		site.setOrganizationalStructure("ORGANIZATION_STRUCTURE_1");

		List<Site> siteList = new ArrayList<Site>();
		siteList.add(site);
		org.setSites(siteList);
		organizationService.saveOrUpdate(org);

		AccountSiteAssociation accountSiteAssociation = new AccountSiteAssociation(site,ac);
		accountSiteAssociationService.saveOrUpdate(accountSiteAssociation);

		AccountSiteAssociation accountSiteAssociation2 = new AccountSiteAssociation(site,ac2);
		accountSiteAssociationService.saveOrUpdate(accountSiteAssociation2);


	} // end of test method


	// for DB cleanup
	@Test
	public void _002_deleteAll() {

		Organization org = organizationService.findByName("testing");
		assertNotNull(org);

		Account acc = accountService.findByIdentifier("gho");
		assertNotNull(acc);

		Account acc2 = accountService.findByIdentifier("gho2");
		assertNotNull(acc2);

		Site site = siteService.findById(org.getSites().get(0).getId());
		assertNotNull(site);

		List<AccountSiteAssociation> accountSiteAssociationList = accountSiteAssociationService.findBySite(site);
		assertNotNull(accountSiteAssociationList);
		for (AccountSiteAssociation item : accountSiteAssociationList) {
			Logger.info("account:" + item.getAccount().getIdentifier());
		}

		//personService.remove(acc.getPerson());
		accountService.remove(acc);
		accountService.remove(acc2);

		for (AccountSiteAssociation item : accountSiteAssociationList) {
			Logger.info("removing " + item.getAccount().getIdentifier());
			accountSiteAssociationService.remove(item);
		}

		siteService.remove(site);
		// TODO - check why hibernate consistency problem occurs when removing organization
		//organizationService.remove(org);

	} // end of test


} // end of class
