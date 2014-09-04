package eu.factorx.awac.models;

import static java.lang.Thread.sleep;
import static play.test.Helpers.contentAsBytes;

import javax.persistence.EntityManager;

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
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.data.FormProgress;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.*;
import eu.factorx.awac.models.reporting.ReportBusinessException;

public abstract class AbstractBaseModelTest implements ApplicationContextAware {

	public static final Class<?>[] ENTITY_CLASSES = { AnswerValue.class, QuestionAnswer.class, QuestionSetAnswer.class, StoredFile.class, FormProgress.class, Question.class, QuestionSet.class,
			Form.class, CodesEquivalence.class, CodeLabel.class, Factor.class, FactorValue.class, Indicator.class, Period.class, UnitConversionFormula.class, Unit.class, UnitCategory.class, 
			ReportBusinessException.class, Analytics.class, Notification.class, Account.class, Person.class, Scope.class, Product.class, Site.class, Organization.class };
	public static final String TEST_USER = "TEST_USER";
	
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
		JPA.em().createQuery("UPDATE " + UnitCategory.class.getSimpleName() + " uc SET uc.mainUnit = null WHERE uc.technicalSegment.creationUser = '" + TEST_USER + "'").executeUpdate();
		JPA.em().createQuery("UPDATE " + QuestionSetAnswer.class.getSimpleName() + " qsa SET qsa.parent = null WHERE qsa.technicalSegment.creationUser = '" + TEST_USER + "'").executeUpdate();
		JPA.em().createQuery("UPDATE " + QuestionSet.class.getSimpleName() + " qs SET qs.parent = null WHERE qs.technicalSegment.creationUser = '" + TEST_USER + "'").executeUpdate();
		for (Class<?> entityClass : ENTITY_CLASSES) {
			String entityName = entityClass.getSimpleName();
			int nbDeleted = JPA.em().createQuery("DELETE FROM " + entityName + " e WHERE e.technicalSegment.creationUser = '" + TEST_USER + "'").executeUpdate();
			if (nbDeleted != 0) {
				Logger.info("Deleted {} '{}' entities", nbDeleted, entityName);
			}
		}
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
		return "Exception : "+exceptionDTO.toString();
	}
}
