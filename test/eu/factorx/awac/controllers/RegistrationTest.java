package eu.factorx.awac.controllers;

import static org.junit.Assert.*;
import static play.test.Helpers.callAction;
import static play.test.Helpers.status;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import play.Logger;
import play.db.jpa.JPA;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import com.fasterxml.jackson.databind.JsonNode;

import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.RegistrationDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.association.AccountSiteAssociation;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.service.*;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistrationTest extends AbstractNoDefaultTransactionBaseControllerTest {

	private static final String email1 = "test1@test.test";
	private static final String email2 = "test2@test.test";

	private static final String identifier1 = "testtest";
	private static final String identifier2 = "testtestt";

	private static final String organizationName1 = "testOrganization";
	private static final String organizationName2 = "testOrganizationn";
	private static final String municipalityName = "testMunicipality";


	private static final String firstName = "firstTest";
	private static final String firstName2 = "firstTestt";

	private static final String lastName = "lastTest";
	private static final String site = "testSite";
	private static final String password = "testPassword";

	@Autowired
	private OrganizationService organizationService;

	@Autowired
    private SiteService siteService;

	@Autowired
    private ScopeService scopeService;

	@Autowired
    private AccountService accountService;

    @Autowired
    private AccountSiteAssociationService accountSiteAssociationService;


    /**
     * create a account + person + organization and scope + site and scope
     */

	// Test does not work
	// TODO - check with Florian accurecy of test
	@Test
	public void _001_registration() {

		EnterpriseAccountCreationDTO dto = createDTO(email1, identifier1, organizationName1);

		//Json node
		JsonNode node = Json.toJson(dto);

		// perform save
		// Fake request
		FakeRequest saveFakeRequest = new FakeRequest();
		saveFakeRequest.withHeader("Content-type", "application/json");
		saveFakeRequest.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.RegistrationController.enterpriseRegistration(),
				saveFakeRequest
		); // callAction

		//analyse result
		// expecting an HTTP 200 return code
		Logger.info("before assert");
		assertEquals(printError(result), 200, status(result));

		//analyse result
		Logger.info("status result: " + status(result));
		// TODO handle scope instance to null
//		LoginResultDTO resultDTO = getDTO(result, LoginResultDTO.class);
//		assertEquals(resultDTO.getPerson().getEmail(), email1);
//		assertEquals(resultDTO.getPerson().getFirstName(), firstName);
//		assertEquals(resultDTO.getPerson().getLastName(), lastName);
//		assertEquals(resultDTO.getPerson().getIdentifier(), identifier1);

		//assertEquals(resultDTO.getOrganization().getName(),organizationName1);
		//assertEquals(resultDTO.getOrganization().getSites().get(0).getName(), site);

        //test if the account is liked with one site
		Logger.info("test account service: " + result.toString());
        Account account = accountService.findByIdentifier(identifier1);

		Logger.info("account created");
        assertNotNull("The account was not created", account);

		Logger.info("findByAccount");
        List<AccountSiteAssociation> siteAssociationList = accountSiteAssociationService.findByAccount(account);

		Logger.info("siteAssociationList:" + siteAssociationList.size());
        assertTrue(siteAssociationList.size()==1);

		Logger.info("period:" + siteAssociationList.get(0).getSite().getListPeriodAvailable().size());
        //control period for the site
		if (siteAssociationList.size()>0)
        	assertTrue(siteAssociationList.get(0).getSite().getListPeriodAvailable().size()==1);

	} // end of authenticateSuccess test


	/**
	 * use the same identifier Name : status 404 excepted
	 */
	@Test
	public void _002_registration() {

		EnterpriseAccountCreationDTO dto = createDTO(email2,identifier1, organizationName2);

		//Json node
		JsonNode node = Json.toJson(dto);

		// perform save
		// Fake request
		FakeRequest saveFakeRequest = new FakeRequest();
		saveFakeRequest.withHeader("Content-type", "application/json");
		saveFakeRequest.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.RegistrationController.enterpriseRegistration(),
				saveFakeRequest
		); // callAction

		//analyse result
		// expecting an HTTP 401 return code
		assertEquals(printError(result),404, status(result));

	} // end of authenticateSuccess test


	/**
	 * use the same organization Name : status 404 excepted
	 */
	@Test
	public void _003_registrationEnterprise() {

		EnterpriseAccountCreationDTO dto = createDTO(email2,identifier2, organizationName1);

		//Json node
		JsonNode node = Json.toJson(dto);

		// perform save
		// Fake request
		FakeRequest saveFakeRequest = new FakeRequest();
		saveFakeRequest.withHeader("Content-type", "application/json");
		saveFakeRequest.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.RegistrationController.enterpriseRegistration(),
				saveFakeRequest
		); // callAction

		//analyse result
		// expecting an HTTP 401 return code
		assertEquals(printError(result),404, status(result));

	} // end of authenticateSuccess test



	/**
	 * register a municipality :
	 * use the same email than test1. Excepted : use the person create for the test 1
	 */
	@Test
	public void _004_registrationMunicipality() {

		RegistrationDTO dto = createMunicipalityDTO(email1,identifier2, municipalityName, firstName2);

		//Json node
		JsonNode node = Json.toJson(dto);

		// perform save
		// Fake request
		FakeRequest saveFakeRequest = new FakeRequest();
		saveFakeRequest.withHeader("Content-type", "application/json");
		saveFakeRequest.withJsonBody(node);

		Result result = callAction(
					eu.factorx.awac.controllers.routes.ref.RegistrationController.municipalityRegistration(),
					saveFakeRequest
		); // callAction

		//analyse result
		// expecting an HTTP 401 return code
		assertEquals(printError(result),200, status(result));

		// TODO handle scope instance to null
//		LoginResultDTO loginResultDTO = getDTO(result, LoginResultDTO.class);
//		assertFalse(firstName2 == loginResultDTO.getPerson().getFirstName());

        //test if the account is liked with one site
        Account account = accountService.findByIdentifier(identifier2);

        assertNotNull("The account was not created", account);

    } // end of authenticateSuccess test

	@Test
	public void _005_cleanTestData() {

		// remove organization for test DB sanity
		cleanData(organizationName1,identifier1);
		cleanData(municipalityName,identifier2);
	}


	private void cleanData (String organisationName, String identifier) {

		Account account = null;
		Organization organization = null;
		List<Site> siteList=null;

		JPA.em().getTransaction().begin();
		account = accountService.findByIdentifier(identifier);
		assertNotNull(account);
        List<AccountSiteAssociation> associationList = accountSiteAssociationService.findByAccount(account);
        accountSiteAssociationService.remove(associationList);
        //accountService.remove(account);

        organization = organizationService.findById(FACTORX_ID);
        assertNotNull(organization);

//        siteList=organization.getSites();
//        assertNotNull(siteList);
//
//        for (Site site : siteList) {
//            assertNotNull(site);
//            siteService.remove(site);
//        }

		organizationService.remove(organization);
		JPA.em().getTransaction().commit();
	}

	private EnterpriseAccountCreationDTO createDTO(String email,String identifier, String organizationName) {

		EnterpriseAccountCreationDTO dto = new EnterpriseAccountCreationDTO();
		PersonDTO personDTO = new PersonDTO();

		personDTO.setEmail(email);
		personDTO.setFirstName(firstName);
		personDTO.setLastName(lastName);
		personDTO.setIdentifier(identifier);

		dto.setPerson(personDTO);
		dto.setFirstSiteName(site);
		dto.setOrganizationName(organizationName);
		dto.setPassword(password);

		return dto;
	}


	private RegistrationDTO createMunicipalityDTO(String email,String identifier, String municipalityName, String firstName) {

		RegistrationDTO dto = new RegistrationDTO();
		PersonDTO personDTO = new PersonDTO();

		personDTO.setEmail(email);
		personDTO.setFirstName(firstName);
		personDTO.setLastName(lastName);
		personDTO.setIdentifier(identifier);

		dto.setPerson(personDTO);
		dto.setOrganizationName(municipalityName);
		dto.setPassword(password);

		return dto;
	}
}
