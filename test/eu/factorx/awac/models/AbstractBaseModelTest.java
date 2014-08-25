package eu.factorx.awac.models;

import static java.lang.Thread.sleep;
import static play.test.Helpers.contentAsBytes;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

import com.fasterxml.jackson.databind.JsonNode;

import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import eu.factorx.awac.models.business.Organization;

public abstract class AbstractBaseModelTest implements ApplicationContextAware {

	public static final String[] ENTITY_NAMES = {Organization.class.getSimpleName(), "PERSON", "ACCOUNT"};

	protected static EntityManager em;
	protected static ApplicationContext applicationContext;

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
		
		em.getTransaction().begin();
		
		Metamodel metamodel = em.getEntityManagerFactory().getMetamodel();
		for (EntityType<?> entityType : metamodel.getEntities()) {
			String entityName = entityType.getName();
			int nbDeleted = JPA.em().createQuery("DELETE FROM " + entityName + " e WHERE e.technicalSegment.creationUser = 'TEST'").executeUpdate();
			if (nbDeleted != 0) {
				Logger.info("Deleted {} '{}' entities", nbDeleted, entityName);
			}
		}
//		for (String entityName : ENTITY_NAMES) {
//			int nbDeleted = JPA.em().createQuery("DELETE FROM " + entityName + " e WHERE e.technicalSegment.creationUser = 'TEST'").executeUpdate();
//			if (nbDeleted != 0) {
//				Logger.info("Deleted {} '{}' entities");
//			}
//		}
		em.getTransaction().commit();

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
		ExceptionsDTO exceptionDTO = getDTO(result,ExceptionsDTO.class);
		return exceptionDTO.toString();
	}
}
