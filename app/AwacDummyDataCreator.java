import org.hibernate.Session;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.GenderCode;
import eu.factorx.awac.models.code.type.HeatingFuelTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.NumericAnswerValue;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.NumericQuestion;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.models.forms.Campaign;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.forms.FormQuestion;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

public class AwacDummyDataCreator {

	public static void createAwacDummyData(Session session) {

		// REFERENCES DATA

		session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.GAS, "Gas", "Gaz", "Gas"));
		session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.OIL, "Oil", "Mazout", "Brandstof"));
		session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.COAL, "Coal", "Charbon",
				"Steenkool"));

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

		// ORGANIZATION AND ACCOUNT

		Organization factorx = new Organization("Factor-X");
		session.saveOrUpdate(factorx);

		Account user1 = new Account(factorx, "user1", "password", "user1_lastname", "user1_firstname");
		user1.setAge(25);
		session.saveOrUpdate(user1);

		// CAMPAIGN AND FORM

		Period period1 = new Period("2013");
		session.saveOrUpdate(period1);

		Campaign awac2013 = new Campaign("AWAC 2013", period1);
		session.saveOrUpdate(awac2013);

		Form form1 = new Form("Formulaire Ménages", awac2013);
		session.saveOrUpdate(form1);

		// QUESTIONS

		// (1) Heating Fuel Question Set (repetition allowed)
		QuestionSet hfqs = new QuestionSet(QuestionCode.HFQS, Boolean.TRUE);
		session.saveOrUpdate(hfqs);

		// (1.1) Heating fuel type
		ValueSelectionQuestion<HeatingFuelTypeCode> hfqType = new ValueSelectionQuestion<>(hfqs, 0,
				QuestionCode.HFQ_HFTYPE);
		hfqs.addQuestion(hfqType);

		// (1.2a) Heating fuel consumption (in volume units)
		NumericQuestion<Integer> hfqVol = new NumericQuestion<>(hfqs, 1, QuestionCode.HFQ_HFCONSO_VOL, volumeUnits);
		hfqs.addQuestion(hfqVol);

		// (1.2b) Heating fuel consumption (in mass units)
		NumericQuestion<Double> hfqMass = new NumericQuestion<>(hfqs, 1, QuestionCode.HFQ_HFCONSO_MASS, massUnits);
		hfqs.addQuestion(hfqMass);

		// link question set to form1
		FormQuestion formQ1 = new FormQuestion(form1, hfqs);
		session.saveOrUpdate(formQ1);

		// (2) Air Travel Questions Set (repetition allowed)
		// TODO !

		// ANSWERS

		Scope scope1 = new Scope(factorx);
		session.saveOrUpdate(scope1);

		// first set of responses
		short repetitionIndex = 1;

		QuestionAnswer<CodeAnswerValue<HeatingFuelTypeCode>> hfqTypeAnswer1 = new QuestionAnswer<>(period1, scope1,
				user1, hfqType, repetitionIndex);
		hfqTypeAnswer1.getAnswerValues().add(new CodeAnswerValue<HeatingFuelTypeCode>(hfqTypeAnswer1, HeatingFuelTypeCode.GAS));
		session.saveOrUpdate(hfqTypeAnswer1);

		QuestionAnswer<NumericAnswerValue<Integer>> hfqVolAnswer1 = new QuestionAnswer<>(period1, scope1, user1,
				hfqVol, repetitionIndex);
		hfqVolAnswer1.getAnswerValues().add(new NumericAnswerValue<Integer>(hfqVolAnswer1, 120, cubicMeter));
		session.saveOrUpdate(hfqVolAnswer1);

		// second set of responses
		repetitionIndex = 2;

		QuestionAnswer<CodeAnswerValue<HeatingFuelTypeCode>> question1_answer2 = new QuestionAnswer<>(period1, scope1,
				user1, hfqType, repetitionIndex);
		question1_answer2.getAnswerValues().add(new CodeAnswerValue<HeatingFuelTypeCode>(question1_answer2,
				HeatingFuelTypeCode.COAL));
		session.saveOrUpdate(question1_answer2);

		QuestionAnswer<NumericAnswerValue<Double>> question2b_answer2 = new QuestionAnswer<>(period1, scope1, user1,
				hfqMass, repetitionIndex);
		question2b_answer2.getAnswerValues().add(new NumericAnswerValue<Double>(question2b_answer2, 3.2, ton));
		session.saveOrUpdate(question2b_answer2);
	}
}
