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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrganisationServiceTest extends AbstractBaseModelTest {

	private static String ORGANIZATION_NAME = "testing";

	@Autowired
	private OrganizationService organizationService;

    @Test
    public void _001_createOrganization() {

		Organization org = new Organization(ORGANIZATION_NAME);
		organizationService.saveOrUpdate(org);

		//em.persist(org);

		//Logger.info("Identifier:" + ac.getIdentifier());
		//Logger.info("Id:" + ac.getId());

    } // end of test method

	@Test
	public void _002_retrieveOrganizationSuccessByService() {

		play.Logger.info("Spring @" + new Date(applicationContext.getStartupDate()));

		Organization org = null;
		org = organizationService.findByName(ORGANIZATION_NAME);
		assertEquals(org.getName(), ORGANIZATION_NAME);
	} // end of test method



	@Test
	public void _003_deleteOrganization() {


		Organization org = null;
		org = organizationService.findByName(ORGANIZATION_NAME);
		assertEquals(org.getName(), ORGANIZATION_NAME);

		organizationService.remove(org);

		Organization reload=null;

		try {
			reload = organizationService.findByName(ORGANIZATION_NAME);
		} catch (Exception empty) {}

		assertNull(reload);
	} // end of test


} // end of class
