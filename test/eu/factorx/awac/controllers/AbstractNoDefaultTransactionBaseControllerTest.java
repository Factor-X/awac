package eu.factorx.awac.controllers;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsBytes;
import static play.test.Helpers.status;

import javax.persistence.EntityManager;

import eu.factorx.awac.dto.awac.get.LoginResultDTO;
import eu.factorx.awac.dto.awac.post.EnterpriseAccountCreationDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;
import play.test.Helpers;
import scala.Option;

import com.fasterxml.jackson.databind.JsonNode;

import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;

import java.util.Random;

public abstract class AbstractNoDefaultTransactionBaseControllerTest implements ApplicationContextAware {

	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String TEST_USER = "TEST_USER";
	public static final Long FACTORX_ID = 14L;

 	protected static EntityManager em;
	protected ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	//before class
	@BeforeClass
	public static void setUp() {

		FakeApplication app = Helpers.fakeApplication();
		Helpers.start(app);
		// TODO - this needs to be tunned
		// wait 2 seconds to be sure app is started on all environments
		try {
			sleep(2000);
		} catch (Exception e) {
			// do nothing
		}
		Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
		em = jpaPlugin.get().em("default");
		JPA.bindForCurrentThread(em);

	}

	// after class
	@AfterClass
	public static void tearDown() {

		JPA.bindForCurrentThread(null);
		if (em.isOpen()) {
			em.close();
		}
	}

	public <T> T getDTO(Result result, Class<T> type){

		String content = new String(contentAsBytes(result));
		JsonNode jsonResponse = Json.parse(content);

		return Json.fromJson(jsonResponse,type);
	}

	public String printError(Result result){
		if (result instanceof ExceptionsDTO) {
			ExceptionsDTO exceptionDTO = getDTO(result, ExceptionsDTO.class);
			return "Exception : " + exceptionDTO.toString();
		} else return "200";
	}

	public EntityManager getEntityManager () {
		return (em);
	}
	public String generateRandomString(int nbLetter){
		String result="";
		final Random rand = new Random();
		for (int i = 0; i < nbLetter; i++) {
			result += LETTERS.charAt(rand.nextInt(LETTERS.length()));
		}

		return result;
	}

	public LoginResultDTO createOrganization() {

		//generate random identifier
		String identifier1 = generateRandomString(8);
		String organizationName1 = generateRandomString(8);

		EnterpriseAccountCreationDTO dto = new EnterpriseAccountCreationDTO();
		PersonDTO personDTO = new PersonDTO();

		personDTO.setEmail("test@test.test");
		personDTO.setFirstName("test");
		personDTO.setLastName("test");
		personDTO.setIdentifier(identifier1);

		dto.setPerson(personDTO);
		dto.setFirstSiteName("site");
		dto.setOrganizationName(organizationName1);
		dto.setPassword("password");

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
		);

		return getDTO(result, LoginResultDTO.class);
	}
}
