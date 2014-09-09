package eu.factorx.awac.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.InvitationResultDTO;
import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.get.OrganizationDTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.post.EmailInvitationDTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.MunicipalityAccountCreationDTO;
import eu.factorx.awac.dto.awac.post.RegisterInvitationDTO;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.dto.myrmex.post.ConnectionFormDTO;
import eu.factorx.awac.models.AbstractBaseModelTest;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.invitation.Invitation;
import eu.factorx.awac.service.AccountService;
import eu.factorx.awac.service.InvitationService;
import eu.factorx.awac.service.OrganizationService;
import eu.factorx.awac.service.PersonService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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
import static play.test.Helpers.*;

@ContextConfiguration(locations = {"classpath:/components-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InvitationTest extends AbstractBaseControllerTest {

	private static final String invitationEmail = "gaston.hollands@factorx.eu";
	private static final String login = "gaston.hollands";
	private static final String password = "factorx.eu";
	private static final String firstName = "hollands";
	private static final String lastName = "gaston";
	private static final String interfaceName = "enterprise";
	private static final String key = "01234567890123456789";

	@Autowired
	OrganizationService organizationService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private InvitationService invitationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PersonService personService;


	@Test
	public void _001_launchInvitation() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");
		Organization org = organizationService.findByName("Factor-X");

		Logger.info("organization id : " + org.getId());

		// InvitationDTO
		EmailInvitationDTO dto = createDTO (invitationEmail,org);

		Logger.info("Guest email:" + dto.getInvitationEmail());
		Logger.info("Host organization name: " + dto.getOrganization().getName());
		// set scope to null to avoid json recusion
		//dto.getOrganization().setSites(null);
		//dto.getOrganization().setAccounts(null);


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

		// wait some time to be sure AKKA actor has time to send the message
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//analyse result
		// expecting an HTTP 200 return code
		assertEquals(200, status(result));

		//analyse result
		InvitationResultDTO resultDTO = getDTO(result, InvitationResultDTO.class);

	} // end of authenticateSuccess test

	@Test
	public void _002_registerInvitation() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		// get back in invitation from test 001 in order to get generated key
		List<Invitation> invitationList = invitationService.findByEmail(invitationEmail);
		// InvitationDTO
		RegisterInvitationDTO dto = new RegisterInvitationDTO(login,password,lastName,firstName,interfaceName,invitationEmail,invitationList.get(0).getGenkey());

		//Json node
		JsonNode node = Json.toJson(dto);

		// perform call to invitation process
		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.InvitationController.registerInvitation(),
				fr
		); // callAction

		// wait some time to be sure AKKA actor has time to send the message
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//analyse result
		// expecting an HTTP 200 return code
		assertEquals(200, status(result));

		// verify account creation
		List<Account> accountList = accountService.findByEmail(invitationEmail);

		assertNotNull(accountList.get(0));
		assertEquals(accountList.get(0).getIdentifier(),login);
		//analyse result
		InvitationResultDTO resultDTO = getDTO(result, InvitationResultDTO.class);

	} // end of authenticateSuccess test

	@Test
	public void _003_registerWithBadKeyFailure() {

		// ConnectionFormDTO
		ConnectionFormDTO cfDto = new ConnectionFormDTO("user1", "password", InterfaceTypeCode.ENTERPRISE.getKey(), "");

		// InvitationDTO
		// specify unknow key
		RegisterInvitationDTO dto = new RegisterInvitationDTO(login,password,lastName,firstName,interfaceName,invitationEmail,key);

		//Json node
		JsonNode node = Json.toJson(dto);

		// perform call to invitation process
		// Fake request
		FakeRequest fr = new FakeRequest();
		fr.withHeader("Content-type", "application/json");
		fr.withJsonBody(node);

		// Call controller action
		Result result = callAction(
				eu.factorx.awac.controllers.routes.ref.InvitationController.registerInvitation(),
				fr
		); // callAction

		// wait some time to be sure AKKA actor has time to send the message
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//analyse result
		// expecting an HTTP 401 return code (unauthorized)
		assertEquals(401, status(result));
		// get MyrmexException

		//analyse result
		ExceptionsDTO exceptionDTO = getDTO(result, ExceptionsDTO.class);
		Logger.info("unauthorize reason: " + exceptionDTO.getMessage());

	} // end of authenticateSuccess test

	@Test
	public void _004_cleanTestData() {

		// remove account for test DB sanity

		// verify account creation
		List<Account> accountList = accountService.findByEmail(invitationEmail);

		assertNotNull(accountList.get(0));
		assertEquals(accountList.get(0).getIdentifier(),login);

		accountService.remove(accountList.get(0));
		personService.remove(accountList.get(0).getPerson());

	}

	// class to handle EmailInvitationDTO

	private EmailInvitationDTO createDTO(String email, Organization org) {

		EmailInvitationDTO dto = new EmailInvitationDTO();
		dto.setInvitationEmail(email);

		OrganizationDTO orgDTO = conversionService.convert(org,OrganizationDTO.class);
		dto.setOrganization(orgDTO);

		return dto;
	}

}
