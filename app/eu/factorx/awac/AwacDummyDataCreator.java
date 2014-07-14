package eu.factorx.awac;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;

import play.db.jpa.JPA;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.BooleanQuestion;
import eu.factorx.awac.models.data.question.type.DoubleQuestion;
import eu.factorx.awac.models.data.question.type.IntegerQuestion;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;
import eu.factorx.awac.util.data.importer.AwacDataImporter;
import eu.factorx.awac.util.data.importer.CodeImporter;
import eu.factorx.awac.util.data.importer.MyrmexUnitsImporter;

public class AwacDummyDataCreator {

	public static void createAwacDummyData(ApplicationContext ctx, Session session) {

		// IMPORT MYRMEX UNITS
		new MyrmexUnitsImporter().run();

		// IMPORT AWAC DATA
		new AwacDataImporter().run();

		// IMPORT CODES
		new CodeImporter().run();

		// REFERENCES DATA

		UnitCategory surfaceUnits = new UnitCategory(null, "Surface", null, null);
		session.saveOrUpdate(surfaceUnits);
		Unit m2 = new Unit(null, "m2", "m2", surfaceUnits);
		session.saveOrUpdate(m2);
		surfaceUnits.setMainUnit(m2);

		// MINIMAL BUSINESS DATA (FOR TEST PURPOSE)

		Organization factorx = new Organization("Factor-X");
		session.saveOrUpdate(factorx);

		Site p3 = new Site(factorx, "P3");
		session.saveOrUpdate(p3);

		Scope scope = new Scope(p3);
		session.saveOrUpdate(scope);

		Account user1 = new Account(factorx, "user1", "password", "user1_lastname", "user1_firstname");
		user1.setAge(25);
		session.saveOrUpdate(user1);

		Period period1 = new Period("2013");
		session.saveOrUpdate(period1);

		// TAB 1

		// -- FORM
		Form tab1Form = new Form("TAB1");
		session.saveOrUpdate(tab1Form);

		// -- CODE LISTS
		// ... are now imported by CodeImporter

		// -- QUESTION_SETS
		QuestionSet a1 = new QuestionSet(QuestionCode.A1, false);
		session.saveOrUpdate(a1);

		tab1Form.getQuestionSet().add(a1);
		session.saveOrUpdate(tab1Form);

		// -- QUESTIONS

		Question a2 = new IntegerQuestion(a1, 0, QuestionCode.A2, null);
		session.saveOrUpdate(a2);

		Question a3 = new ValueSelectionQuestion(a1, 0, QuestionCode.A3, CodeList.SITE_SECTORS);
		session.saveOrUpdate(a3);

		Question a4 = new ValueSelectionQuestion(a1, 0, QuestionCode.A4, CodeList.NACE_CODES_1);
		session.saveOrUpdate(a4);

		Question a5 = new ValueSelectionQuestion(a1, 0, QuestionCode.A5, CodeList.NACE_CODES_2);
		session.saveOrUpdate(a5);

		Question a6 = new ValueSelectionQuestion(a1, 0, QuestionCode.A6, CodeList.NACE_CODES_3);
		session.saveOrUpdate(a6);

		Question a7 = new BooleanQuestion(a1, 0, QuestionCode.A7);
		session.saveOrUpdate(a7);

		Question a8 = new ValueSelectionQuestion(a1, 0, QuestionCode.A8, CodeList.PUBLIC_OR_PRIVATE);
		session.saveOrUpdate(a8);

		Question a9 = new DoubleQuestion(a1, 0, QuestionCode.A9, surfaceUnits);
		session.saveOrUpdate(a9);

		Question a10 = new DoubleQuestion(a1, 0, QuestionCode.A10, surfaceUnits);
		session.saveOrUpdate(a10);

		Question a11 = new BooleanQuestion(a1, 0, QuestionCode.A11);
		session.saveOrUpdate(a11);

		Question a12 = new IntegerQuestion(a1, 0, QuestionCode.A12, null);
		session.saveOrUpdate(a12);

		// TAB 2

		// TODO adapt model to allow finding units and categories by codes (UnitCategoryCode and UnitCode)
		UnitCategory energyUnits = getUnitCategoryByName("Energy");
		Unit GJ = getUnitBySymbol("GJ");

		// -- FORM

		Form tab2Form = new Form("TAB2");
		session.saveOrUpdate(tab2Form);

		// -- QUESTION_SETS

		QuestionSet a15 = new QuestionSet(QuestionCode.A15, true);
		session.saveOrUpdate(a15);

		tab2Form.getQuestionSet().add(a15);
		session.saveOrUpdate(tab2Form);

		// -- QUESTIONS

		// TODO implement actually the specs! (this is simplified for now)
		Question a16 = new ValueSelectionQuestion(a15, 0, QuestionCode.A16, CodeList.ACTIVITY_SOURCE);
		session.saveOrUpdate(a16);

		Question a17 = new DoubleQuestion(a15, 0, QuestionCode.A17, energyUnits);
		session.saveOrUpdate(a17);

		// -- ANSWERS (temporary, for reporting test purposes => TODO Delete This)

		QuestionAnswer a16Answer = new QuestionAnswer(period1, scope, user1, a16, 0);
		session.saveOrUpdate(a16Answer);
		CodeAnswerValue a16AnswerValue = new CodeAnswerValue(a16Answer, ActivitySourceCode.DIESEL_GASOIL_OU_FUEL_LEGER);
		session.saveOrUpdate(a16AnswerValue);

		QuestionAnswer a17Answer = new QuestionAnswer(period1, scope, user1, a17, 0);
		session.saveOrUpdate(a17Answer);
		DoubleAnswerValue a17AnswerValue = new DoubleAnswerValue(a17Answer, 20.6, GJ);
		session.saveOrUpdate(a17AnswerValue);
	}

	private static Unit getUnitBySymbol(String symbol) {
		return JPA.em().createNamedQuery(Unit.FIND_BY_SYMBOL, Unit.class).setParameter("symbol", symbol).getSingleResult();
	}

	private static UnitCategory getUnitCategoryByName(String name) {
		return JPA.em().createNamedQuery(UnitCategory.FIND_BY_NAME, UnitCategory.class).setParameter("name", name).getSingleResult();
	}
}
