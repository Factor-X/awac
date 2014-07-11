package eu.factorx.awac;

import eu.factorx.awac.models.data.question.type.*;
import org.hibernate.Session;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.GenderCode;
import eu.factorx.awac.models.code.type.HeatingFuelTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.DoubleAnswerValue;
import eu.factorx.awac.models.data.answer.type.IntegerAnswerValue;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

public class AwacDummyDataCreator {

    public static void createAwacDummyData(Session session) {

        // REFERENCES DATA

        session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.GAS, "Gas", "Gaz", "Gas"));
        session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.OIL, "Oil", "Mazout", "Brandstof"));
        session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.COAL, "Coal", "Charbon", "Steenkool"));

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

        UnitCategory surfaceUnits = new UnitCategory("Surface");
        session.saveOrUpdate(surfaceUnits);
        Unit m2 = new Unit("m2", surfaceUnits);
        session.saveOrUpdate(m2);

        // ORGANIZATION AND ACCOUNT

        Organization factorx = new Organization("Factor-X");
        session.saveOrUpdate(factorx);

        Account user1 = new Account(factorx, "user1", "password", "user1_lastname", "user1_firstname");
        user1.setAge(25);
        session.saveOrUpdate(user1);

        // CAMPAIGN AND FORM

        Period period1 = new Period("2013");
        session.saveOrUpdate(period1);

        Form form1 = new Form("Formulaire Entreprise");
        session.saveOrUpdate(form1);

        // QUESTIONS

        // (1) Heating Fuel Question Set (repetition allowed)
        QuestionSet hfqs = new QuestionSet(QuestionCode.HFQS, Boolean.TRUE);
        session.saveOrUpdate(hfqs);

        // (1.1) Heating fuel type
        Question hfqType = new ValueSelectionQuestion(hfqs, 0, QuestionCode.HFQ_HFTYPE, CodeList.HEATING_FUEL_TYPE);
        hfqs.addQuestion(hfqType);

        // (1.2a) Heating fuel consumption (in volume units)
        Question hfqVol = new IntegerQuestion(hfqs, 1, QuestionCode.HFQ_HFCONSO_VOL, volumeUnits);
        hfqs.addQuestion(hfqVol);

        // (1.2b) Heating fuel consumption (in mass units)
        Question hfqMass = new DoubleQuestion(hfqs, 1, QuestionCode.HFQ_HFCONSO_MASS, massUnits);
        hfqs.addQuestion(hfqMass);

        // link question set to form1
        form1.getQuestionSet().add(hfqs);
        session.saveOrUpdate(form1);

        // (2) Air Travel Questions Set (repetition allowed)
        // TODO !

        // ANSWERS

        Scope scope1 = new Scope(factorx);
        session.saveOrUpdate(scope1);

        // first set of responses
        short repetitionIndex = 1;

        QuestionAnswer hfqTypeAnswer1 = new QuestionAnswer(period1, scope1, user1, hfqType, repetitionIndex);
        session.saveOrUpdate(hfqTypeAnswer1);
        CodeAnswerValue hfqTypeAnswer1Value = new CodeAnswerValue(hfqTypeAnswer1, HeatingFuelTypeCode.GAS);
        session.saveOrUpdate(hfqTypeAnswer1Value);

        QuestionAnswer hfqVolAnswer1 = new QuestionAnswer(period1, scope1, user1, hfqVol, repetitionIndex);
        session.saveOrUpdate(hfqVolAnswer1);
        IntegerAnswerValue hfqVolAnswer1Value = new IntegerAnswerValue(hfqVolAnswer1, 120, cubicMeter);
        session.saveOrUpdate(hfqVolAnswer1Value);

        // second set of responses
        repetitionIndex = 2;

        QuestionAnswer question1_answer2 = new QuestionAnswer(period1, scope1, user1, hfqType, repetitionIndex);
        session.saveOrUpdate(question1_answer2);
        CodeAnswerValue question1_answer2Value = new CodeAnswerValue(question1_answer2, HeatingFuelTypeCode.COAL);
        session.saveOrUpdate(question1_answer2Value);

        QuestionAnswer question2b_answer2 = new QuestionAnswer(period1, scope1, user1, hfqMass, repetitionIndex);
        session.saveOrUpdate(question2b_answer2);
        DoubleAnswerValue question2b_answer2Value = new DoubleAnswerValue(question2b_answer2, 3.2, ton);
        session.saveOrUpdate(question2b_answer2Value);

        // TEST FORM

        Form testForm = new Form("TEST_FORM");
        session.saveOrUpdate(testForm);

        // (Q1)
        QuestionSet questionSet1 = new QuestionSet(new QuestionCode("QS1"), Boolean.TRUE);
        session.saveOrUpdate(questionSet1);
        Question question1 = new StringQuestion(questionSet1, 0, new QuestionCode("Q1"));
        session.saveOrUpdate(question1);

        // (Q2)
        QuestionSet questionSet2 = new QuestionSet(new QuestionCode("QS2"), Boolean.TRUE);
        session.saveOrUpdate(questionSet2);
        Question question2 = new IntegerQuestion(questionSet2, 0, new QuestionCode("Q2"), volumeUnits);
        session.saveOrUpdate(question2);

        // (Q3)
        QuestionSet questionSet3 = new QuestionSet(new QuestionCode("QS3"), Boolean.TRUE);
        session.saveOrUpdate(questionSet3);
        Question question3 = new DoubleQuestion(questionSet3, 0, new QuestionCode("Q3"), null);
        session.saveOrUpdate(question3);

        // (Q4)
        QuestionSet questionSet4 = new QuestionSet(new QuestionCode("QS4"), Boolean.TRUE);
        session.saveOrUpdate(questionSet4);
        Question question4 = new IntegerQuestion(questionSet4, 0, new QuestionCode("Q4"), massUnits);
        session.saveOrUpdate(question4);

        // Add question sets to test form
        testForm.getQuestionSet().add(questionSet1);
        testForm.getQuestionSet().add(questionSet2);
        testForm.getQuestionSet().add(questionSet3);
        testForm.getQuestionSet().add(questionSet4);
        session.saveOrUpdate(testForm);

        // (Answer Q4)
        QuestionAnswer question4Answer = new QuestionAnswer(period1, scope1, user1, question4, 0);
        session.saveOrUpdate(question4Answer);
        IntegerAnswerValue question4AnswerValue = new IntegerAnswerValue(question4Answer, 23, ton);
        session.saveOrUpdate(question4AnswerValue);

        System.out.println(" ==== TEST ANSWERS CONTROLLER - REQUEST PARAMS:");
        System.out.println(" ===== Form ID = " + testForm.getId());
        System.out.println(" ===== Period ID = " + period1.getId());
        System.out.println(" ===== Scope ID = " + scope1.getId());


        // TAB 1

        // -- FORM
        Form tab1Form = new Form("TAB1");
        session.saveOrUpdate(tab1Form);

        // -- CODE LISTS
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTORS, "1", "Industrie primaire, hormis le secteur agricole", "Industrie primaire, hormis le secteur agricole", "Industrie primaire, hormis le secteur agricole"));
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTORS, "2", "Production de biens intermédiaires", "Production de biens intermédiaires", "Production de biens intermédiaires"));
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTORS, "3", "Production de biens de consommation", "Production de biens de consommation", "Production de biens de consommation"));
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTORS, "4", "Tertiaire", "Tertiaire", "Tertiaire"));

        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES, "1", "05 Extraction de houille et de lignite", "05 Extraction de houille et de lignite", "05 Extraction de houille et de lignite"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES, "2", "06 Extraction d'hydrocarbures", "06 Extraction d'hydrocarbures", "06 Extraction d'hydrocarbures"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES, "3", "07 Extraction de minerais métalliques", "07 Extraction de minerais métalliques", "07 Extraction de minerais métalliques"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES, "4", "08 Autres industries extractives", "08 Autres industries extractives", "08 Autres industries extractives"));
        session.saveOrUpdate(new CodeLabel(CodeList.NACE_CODES, "5", "09 Services de soutien aux industries extractives", "09 Services de soutien aux industries extractives", "09 Services de soutien aux industries extractives"));

        session.saveOrUpdate(new CodeLabel(CodeList.PUBLIC_OR_PRIVATE, "1", "Public", "Public", "Public"));
        session.saveOrUpdate(new CodeLabel(CodeList.PUBLIC_OR_PRIVATE, "2", "Privé", "Privé", "Privé"));

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

        Question a4 = new ValueSelectionQuestion(a1, 0, QuestionCode.A4, CodeList.NACE_CODES);
        session.saveOrUpdate(a4);

        Question a5 = new ValueSelectionQuestion(a1, 0, QuestionCode.A5, CodeList.NACE_CODES);
        session.saveOrUpdate(a5);

        Question a6 = new ValueSelectionQuestion(a1, 0, QuestionCode.A6, CodeList.NACE_CODES);
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

    }
}













