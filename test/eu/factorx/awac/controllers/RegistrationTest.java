package eu.factorx.awac.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.MunicipalityAccountCreationDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.ScopeService;
import eu.factorx.awac.service.SiteService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistrationTest extends AbstractBaseControllerTest {

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
	OrganizationService organizationService;

	@Autowired
	SiteService siteService;

	@Autowired
	ScopeService scopeService;

	@Autowired
	AccountService accountService;


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
		assertEquals(200, status(result));

		//analyse result
		LoginResultDTO resultDTO = getDTO(result, LoginResultDTO.class);

		assertEquals(resultDTO.getPerson().getEmail(), email1);
		assertEquals(resultDTO.getPerson().getFirstName(), firstName);
		assertEquals(resultDTO.getPerson().getLastName(), lastName);
		assertEquals(resultDTO.getPerson().getIdentifier(), identifier1);
		assertEquals(resultDTO.getOrganization().getName(),organizationName1);
		assertEquals(resultDTO.getOrganization().getSites().get(0).getName(), site);


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
	public void _004_registration() {

		MunicipalityAccountCreationDTO dto = createMunicipalityDTO(email1,identifier2, municipalityName, firstName2);

		//Json node
		JsonNode node = Json.toJson(dto);

		// perform save
		// Fake request
		FakeRequest saveFakeRequest = new FakeRequest();
		saveFakeRequest.withHeader("Content-type", "application/json");
		saveFakeRequest.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.RegistrationController.municipalityRegistration(),
				saveFakeRequest
		); // callAction

		//analyse result
		// expecting an HTTP 401 return code
		assertEquals(200, status(result));

		LoginResultDTO loginResultDTO = getDTO(result, LoginResultDTO.class);

		assertFalse(firstName2 == loginResultDTO.getPerson().getFirstName());

	} // end of authenticateSuccess test

	@Test
	public void _005_cleanTestData() {

		// remove organization for test DB sanity
		//cleanData(organizationName1,identifier1);
		//cleanData(municipalityName,identifier2);
	}


	private void cleanData (String organisationName, String identifier) {

		Account account = null;
		Organization organization = null;
		List<Site> siteList=null;


		account = accountService.findByIdentifier(identifier);
		assertNotNull(account);
		accountService.remove(account);

		organization = organizationService.findByName(organisationName);
		assertNotNull(organization);

		siteList=organization.getSites();
		assertNotNull(siteList);

		for (Site site : siteList) {
			assertNotNull(site);
			play.Logger.info("Site for orgrganisation:" + site.getName());
			//Scope siteScope = scopeService.findBySite(site);
			//scopeService.remove(scopeService.findBySite(site));
			//scopeService.remove(scopeService.findByOrganization(organization))
			siteService.remove(site);
		}

		organizationService.remove(organization);
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


	private MunicipalityAccountCreationDTO  createMunicipalityDTO(String email,String identifier, String municipalityName, String firstName) {

		MunicipalityAccountCreationDTO dto = new MunicipalityAccountCreationDTO();
		PersonDTO personDTO = new PersonDTO();

		personDTO.setEmail(email);
		personDTO.setFirstName(firstName);
		personDTO.setLastName(lastName);
		personDTO.setIdentifier(identifier);

		dto.setPerson(personDTO);
		dto.setMunicipalityName(municipalityName);
		dto.setPassword(password);

		return dto;
	}
}
