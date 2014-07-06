import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.CodeLabel;
import eu.factorx.awac.models.code.CodeType;
import eu.factorx.awac.models.code.GenderCode;
import eu.factorx.awac.models.code.HeatingFuelCode;
import eu.factorx.awac.models.code.LanguageCode;
import eu.factorx.awac.models.code.QuestionCode;
import eu.factorx.awac.models.data.AnswerData;
import eu.factorx.awac.models.data.AnswerType;
import eu.factorx.awac.models.data.Question;
import eu.factorx.awac.models.data.QuestionAnswer;
import eu.factorx.awac.models.data.QuestionSet;
import eu.factorx.awac.models.data.QuestionSetAnswer;
import eu.factorx.awac.models.forms.Campaign;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.forms.FormQuestion;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

public class AwacDummyDataCreator {

	public static void createAwacDummyData(Session session) {

		// ORGANIZATION AND ACCOUNT

		Organization factorx = new Organization("Factor-X");
		session.saveOrUpdate(factorx);
		Account user1 = new Account(factorx, "user1", "password", "user1_lastname", "user1_firstname");
		user1.setAge(25);
		session.saveOrUpdate(user1);

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

		UnitCategory massUnits = new UnitCategory("Mass");
		session.saveOrUpdate(massUnits);
		Unit kg = new Unit("Kg", massUnits);
		session.saveOrUpdate(kg);
		Unit ton = new Unit("Ton", massUnits);
		session.saveOrUpdate(ton);

		Period period1 = new Period("2013");
		session.saveOrUpdate(period1);

		// CAMPAIGN AND FORM

		Campaign awac2013 = new Campaign("AWAC 2013", period1);
		session.saveOrUpdate(awac2013);

		Form form1 = new Form("Formulaire Ménages", awac2013);
		session.saveOrUpdate(form1);

		// QUESTIONS

		// (1) Heating Fuel Questions Set (repetition allowed)
		QuestionSet qs1 = new QuestionSet(QuestionCode.HF_SET, Boolean.TRUE);
		session.saveOrUpdate(qs1);
		// Heating fuel type
		Question q1 = new Question(qs1, QuestionCode.HF_HFT, AnswerType.valueSelection(CodeType.HEATING_FUEL_TYPE),
				null, new Short("0"));
		session.saveOrUpdate(q1);
		// Heating fuel consumption (in volume units)
		Question q2a = new Question(qs1, QuestionCode.HF_HFC1, AnswerType.INTEGER, volumeUnits, new Short("1"));
		session.saveOrUpdate(q2a);

		// Heating fuel consumption (in mass units)
		Question q2b = new Question(qs1, QuestionCode.HF_HFC2, AnswerType.INTEGER, massUnits, new Short("1"));
		session.saveOrUpdate(q2b);

		// adding to form1
		FormQuestion formQ1 = new FormQuestion(form1, qs1);
		session.saveOrUpdate(formQ1);

		// (2) Air Travel Questions Set (repetition allowed)
		// TODO !

		// ANSWERS

		Scope scope1 = new Scope(factorx);
		session.saveOrUpdate(scope1);

		QuestionSetAnswer qsa1 = new QuestionSetAnswer(qs1, period1, scope1);
		session.saveOrUpdate(qsa1);

		// first set of responses

		Short repetitionIndex = new Short("1");

		QuestionAnswer qa1_1 = new QuestionAnswer(q1, qsa1, user1, null, repetitionIndex);
		session.saveOrUpdate(qa1_1);

		AnswerData answerData1 = new AnswerData(qa1_1, HeatingFuelCode.GAS);
		List<AnswerData> answerDataLst1 = new ArrayList<>();
		answerDataLst1.add(answerData1);
		qa1_1.setAnswerData(answerDataLst1);
		session.saveOrUpdate(qa1_1);

		QuestionAnswer qa2_1 = new QuestionAnswer(q2a, qsa1, user1, cubicMeter, repetitionIndex);
		session.saveOrUpdate(qa2_1);

		AnswerData answerData2 = new AnswerData(qa2_1, 120);
		List<AnswerData> answerDataLst2 = new ArrayList<>();
		answerDataLst2.add(answerData2);
		qa2_1.setAnswerData(answerDataLst2);
		session.saveOrUpdate(qa2_1);

		repetitionIndex = new Short("2");

		QuestionAnswer qa1_2 = new QuestionAnswer(q1, qsa1, user1, null, repetitionIndex);
		session.saveOrUpdate(qa1_2);

		AnswerData answerData3 = new AnswerData(qa1_2, HeatingFuelCode.COAL);
		List<AnswerData> answerDataLst3 = new ArrayList<>();
		answerDataLst3.add(answerData3);
		qa1_2.setAnswerData(answerDataLst3);
		session.saveOrUpdate(qa1_2);

		QuestionAnswer qa2_2 = new QuestionAnswer(q2b, qsa1, user1, ton, repetitionIndex);
		session.saveOrUpdate(qa2_2);

		AnswerData answerData4 = new AnswerData(qa2_2, 5);
		List<AnswerData> answerDataLst4 = new ArrayList<>();
		answerDataLst4.add(answerData4);
		qa2_2.setAnswerData(answerDataLst4);
		session.saveOrUpdate(qa2_2);
	}
}
