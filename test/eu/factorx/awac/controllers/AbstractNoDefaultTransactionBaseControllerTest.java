package eu.factorx.awac.controllers;

import static java.lang.Thread.sleep;
import static play.test.Helpers.contentAsBytes;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

import com.fasterxml.jackson.databind.JsonNode;

import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;

public abstract class AbstractNoDefaultTransactionBaseControllerTest implements ApplicationContextAware {

	public static final String TEST_USER = "TEST_USER";
	
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
}
