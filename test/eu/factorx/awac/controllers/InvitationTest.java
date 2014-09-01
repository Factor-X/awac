package eu.factorx.awac.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.InvitationResultDTO;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.post.EmailInvitationDTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.MunicipalityAccountCreationDTO;
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

import static org.junit.Assert.*;
import static play.test.Helpers.*;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InvitationTest extends AbstractBaseModelTest {

	private static final String invitationEmail = "test1@test.test";
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

	/**
	 * create a account + person + organization and scope + site and scope
	 */

	// Test does not work
	@Test
	public void _001_launchInvitation() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		// InvitationDTO
		EmailInvitationDTO dto = createDTO (invitationEmail);

		//Json node
		JsonNode node = Json.toJson(dto);

		// perform call to invitation process
		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withJsonBody(node);
		fr.withSession(SecuredController.SESSION_IDENTIFIER_STORE, cfDto.getLogin());

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.InvitationController.launchInvitation(),
				fr
		); // callAction

		//analyse result
		// expecting an HTTP 200 return code
		assertEquals(200, status(result));

		//analyse result
		InvitationResultDTO resultDTO = getDTO(result, InvitationResultDTO.class);


	} // end of authenticateSuccess test

	// class to handle EmailInvitationDTO

	private EmailInvitationDTO createDTO(String email) {

		EmailInvitationDTO dto = new EmailInvitationDTO();
		dto.setInvitationEmail(email);

		return dto;
	}

}
