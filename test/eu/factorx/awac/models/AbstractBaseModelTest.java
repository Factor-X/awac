package eu.factorx.awac.models;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.databind.JsonNode;
import eu.factorx.awac.dto.myrmex.get.ExceptionsDTO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
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

import static java.lang.Thread.sleep;
import static play.test.Helpers.contentAsBytes;

public abstract class AbstractBaseModelTest implements ApplicationContextAware {

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
        JPA.bindForCurrentThread(null);
		if (em.isOpen()) em.close();
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
