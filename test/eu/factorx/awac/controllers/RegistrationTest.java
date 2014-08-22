package eu.factorx.awac.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistrationTest extends AbstractBaseModelTest {

	private static final String email1 = "test@test.test";
	private static final String email2 = "test2@test.test";

	private static final String identifier1 = "testtest";
	private static final String identifier2 = "testtest2";

	private static final String organizationName1 = "testOrganization";
	private static final String organizationName2 = "test2Organization";


	private static final String firstName = "firstTest";
	private static final String lastName = "lastTest";
	private static final String site = "testSite";
	private static final String password = "testPassword";

	/**
	 * create a account + person + organization and scope + site and scope
	 */

	// Test does not work
	// TODO - check with Florian accurecy of test
	//@Test
	public void _001_registrationEnterprise() {

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
		assertEquals(printError(result),200, status(result));

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
	 * use the same email : status 404 excepted
	 */
	@Test
	public void _002_registrationEnterprise() {

		EnterpriseAccountCreationDTO dto = createDTO(email1, identifier2, organizationName2);

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
		assertEquals(404, status(result));

	} // end of authenticateSuccess test

	/**
	 * use the same identifier Name : status 404 excepted
	 */
	@Test
	public void _003_registrationEnterprise() {

		EnterpriseAccountCreationDTO dto = createDTO(email2, identifier1, organizationName2);

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
		assertEquals(404, status(result));

	} // end of authenticateSuccess test

	/**
	 * use the same organization Name : status 404 excepted
	 */
	@Test
	public void _004_registrationEnterprise() {

		EnterpriseAccountCreationDTO dto = createDTO(email2, identifier2, organizationName1);

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
		assertEquals(404, status(result));

	} // end of authenticateSuccess test


	private EnterpriseAccountCreationDTO createDTO(String email, String identifier, String organizationName) {

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

}
