import org.hibernate.Session;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.User;
import eu.factorx.awac.models.code.CodeLabel;
import eu.factorx.awac.models.code.CodeType;
import eu.factorx.awac.models.code.GenderCode;
import eu.factorx.awac.models.code.HeatingFuelCode;
import eu.factorx.awac.models.code.LanguageCode;
import eu.factorx.awac.models.code.QuestionCode;
import eu.factorx.awac.models.data.AnswerType;
import eu.factorx.awac.models.data.Question;
import eu.factorx.awac.models.data.QuestionSet;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

public class AwacDummyDataCreator {

	public static void createAwacDummyData(Session session) {

		// REFERENCES DATA
		
		session.saveOrUpdate(new CodeLabel(HeatingFuelCode.GAS, "Gas", "Gaz", "Gas"));
		session.saveOrUpdate(new CodeLabel(HeatingFuelCode.OIL, "Oil", "Mazout", "Brandstof"));

		session.saveOrUpdate(new CodeLabel(LanguageCode.ENGLISH, "English", "Anglais", "Engels"));
		session.saveOrUpdate(new CodeLabel(LanguageCode.FRENCH, "French", "Français", "Frans"));
		session.saveOrUpdate(new CodeLabel(LanguageCode.DUTCH, "Dutch", "Néerlandais", "Nederlands"));

		session.saveOrUpdate(new CodeLabel(GenderCode.MAN, "Man", "Homme", "Man"));
		session.saveOrUpdate(new CodeLabel(GenderCode.WOMAN, "Woman", "Femme", "Vrouw"));

		UnitCategory volumeUnits = new UnitCategory("Volume");
		session.saveOrUpdate(volumeUnits);

		Unit litter = new Unit("Liter", volumeUnits);
		session.saveOrUpdate(litter);
		Unit gallon = new Unit("Gallon", volumeUnits);
		session.saveOrUpdate(gallon);
		Unit cubicMeter = new Unit("Cubic meter", volumeUnits);
		session.saveOrUpdate(cubicMeter);

		// ORGANIZATIONS AND USER
		
		Organization men1 = new Organization("Ménage 1");
		session.saveOrUpdate(men1);
		Organization men2 = new Organization("Ménage 2");
		session.saveOrUpdate(men2);

		User user = new User("user1", men1);
		session.saveOrUpdate(user);

		// QUESTIONS
		
		// Heating Fuel Questions Set (repetition allowed)
		QuestionSet qs1 = new QuestionSet(QuestionCode.HF_SET, Boolean.TRUE);
		session.saveOrUpdate(qs1);
		Question q1 = new Question(qs1, QuestionCode.HF_HFT, AnswerType.valueSelection(CodeType.HEATING_FUEL_TYPE),
				null, new Short("0"));
		session.saveOrUpdate(q1);
		Question q2 = new Question(qs1, QuestionCode.HF_HFC, AnswerType.INTEGER, volumeUnits, new Short("1"));
		session.saveOrUpdate(q2);

		// Air Travel Questions Set (repetition allowed)
		// TODO !
		
		// Period period1 = new Period("2013");
		// session.saveOrUpdate(period1);
		//
		// Campaign awac2013 = new Campaign("AWAC 2013", period1);
		// session.saveOrUpdate(awac2013);
		//
		// Form form = new Form("Formulaire Ménages", awac2013);
		// session.saveOrUpdate(form);
		//
		// FormQuestion formQ1 = new FormQuestion(form, q1);
		// session.saveOrUpdate(formQ1);
		// FormQuestion formQ2 = new FormQuestion(form, q2);
		// session.saveOrUpdate(formQ2);
		// FormQuestion formQ3 = new FormQuestion(form, q3);
		// session.saveOrUpdate(formQ3);
		//
		// Scope scope = new Scope(men1);
		// session.saveOrUpdate(scope);
		// SimpleAnswerValue answerValue = new SimpleAnswerValue(132);
		// session.saveOrUpdate(answerValue);
		// QuestionAnswer qa1 = new QuestionAnswer(user, q1, period1, scope,
		// answerValue);
		// session.saveOrUpdate(qa1);
		//
		// session.createCriteria(Organization.class).list();
		// session.createCriteria(Scope.class).list();
		//
		// session.createCriteria(CodeLabel.class).list();
		// StringAnswerValue answerValue = new StringAnswerValue(qa1, 0, "120");
		// session.saveOrUpdate(answerValue);
	}
}
