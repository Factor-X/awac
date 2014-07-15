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

        UnitCategory surfaceUnits = getUnitCategoryByName("Area");
        UnitCategory energyUnits = getUnitCategoryByName("Energy");

        Unit gj = getUnitBySymbol("GJ");
        Unit kcal = getUnitBySymbol("kcal");


        // ORGANIZATION AND ACCOUNT

        Organization factorx = new Organization("Factor-X");
        session.saveOrUpdate(factorx);

        Site p3 = new Site(factorx, "P3");
        session.saveOrUpdate(p3);

        Scope scope = new Scope(p3);
        session.saveOrUpdate(scope);

        Account user1 = new Account(factorx, "user1", "password", "user1_lastname", "user1_firstname");
        user1.setAge(25);
        session.saveOrUpdate(user1);

        // CAMPAIGN AND FORM

        Period period1 = new Period("2013");
        session.saveOrUpdate(period1);

        Form form1 = new Form("Formulaire Entreprise");
        session.saveOrUpdate(form1);


        // ANSWERS

        // -- FORM
        Form tab1Form = new Form("TAB1");
        session.saveOrUpdate(tab1Form);

        Form tab2Form = new Form("TAB2");
        session.saveOrUpdate(tab2Form);


        // -- QUESTION_SETS
        QuestionSet a1 = new QuestionSet(QuestionCode.A1, false);
        session.saveOrUpdate(a1);
        tab1Form.getQuestionSet().add(a1);
        session.saveOrUpdate(tab1Form);


        QuestionSet a13 = new QuestionSet(QuestionCode.A13, false);
        session.saveOrUpdate(a13);
        tab2Form.getQuestionSet().add(a13);
        session.saveOrUpdate(tab2Form);

        QuestionSet a15 = new QuestionSet(QuestionCode.A15, true);
        session.saveOrUpdate(a15);
        tab2Form.getQuestionSet().add(a15);
        session.saveOrUpdate(tab2Form);

        QuestionSet a20 = new QuestionSet(QuestionCode.A20, false);
        session.saveOrUpdate(a20);
        tab2Form.getQuestionSet().add(a20);
        session.saveOrUpdate(tab2Form);

        QuestionSet a22 = new QuestionSet(QuestionCode.A22, false);
        session.saveOrUpdate(a22);
        tab2Form.getQuestionSet().add(a22);
        session.saveOrUpdate(tab2Form);

        QuestionSet a25 = new QuestionSet(QuestionCode.A25, false);
        session.saveOrUpdate(a25);
        tab2Form.getQuestionSet().add(a25);
        session.saveOrUpdate(tab2Form);

        QuestionSet a31 = new QuestionSet(QuestionCode.A31, false);
        session.saveOrUpdate(a31);
        tab2Form.getQuestionSet().add(a31);
        session.saveOrUpdate(tab2Form);

        QuestionSet a41 = new QuestionSet(QuestionCode.A41, false);
        session.saveOrUpdate(a41);
        tab2Form.getQuestionSet().add(a41);
        session.saveOrUpdate(tab2Form);

        QuestionSet a42 = new QuestionSet(QuestionCode.A42, false);
        session.saveOrUpdate(a42);
        tab2Form.getQuestionSet().add(a42);
        session.saveOrUpdate(tab2Form);

        QuestionSet a45 = new QuestionSet(QuestionCode.A45, false);
        session.saveOrUpdate(a45);
        tab2Form.getQuestionSet().add(a45);
        session.saveOrUpdate(tab2Form);

        QuestionSet a47 = new QuestionSet(QuestionCode.A47, false);
        session.saveOrUpdate(a47);
        tab2Form.getQuestionSet().add(a47);
        session.saveOrUpdate(tab2Form);

        // -- QUESTIONS

        session.saveOrUpdate(new IntegerQuestion(a1, 0, QuestionCode.A2, null));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 100, QuestionCode.A3, CodeList.SITE_SECTORS));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 200, QuestionCode.A4, CodeList.NACE_CODES_1));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 300, QuestionCode.A5, CodeList.NACE_CODES_2));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 400, QuestionCode.A6, CodeList.NACE_CODES_3));
        session.saveOrUpdate(new BooleanQuestion(a1, 500, QuestionCode.A7));
        session.saveOrUpdate(new ValueSelectionQuestion(a1, 600, QuestionCode.A8, CodeList.PUBLIC_PRIVATE));
        session.saveOrUpdate(new DoubleQuestion(a1, 700, QuestionCode.A9, surfaceUnits));
        session.saveOrUpdate(new DoubleQuestion(a1, 800, QuestionCode.A10, surfaceUnits));
        session.saveOrUpdate(new BooleanQuestion(a1, 900, QuestionCode.A11));
        session.saveOrUpdate(new IntegerQuestion(a1, 1000, QuestionCode.A12, null));

        // TAB 2

        session.saveOrUpdate(new IntegerQuestion(a13, 0, QuestionCode.A14, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 100, QuestionCode.A15, null));
        Question a16 = new ValueSelectionQuestion(a15, 200, QuestionCode.A16, CodeList.ACTIVITY_SOURCE);
        session.saveOrUpdate(a16);
        Question a17 = new DoubleQuestion(a15, 300, QuestionCode.A17, energyUnits);
        session.saveOrUpdate(a17);
        // 18
        // 19
        session.saveOrUpdate(new IntegerQuestion(a13, 400, QuestionCode.A21, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 500, QuestionCode.A23, null));
        session.saveOrUpdate(new IntegerQuestion(a13, 600, QuestionCode.A24, null));


        // First set of answers
        int repetitionIndex = 1;
        QuestionAnswer a16Answer1 = new QuestionAnswer(period1, scope, user1, a16, repetitionIndex);
        session.saveOrUpdate(a16Answer1);
        CodeAnswerValue a16AnswerValue1 = new CodeAnswerValue(a16Answer1, ActivitySourceCode.DIESEL_GASOIL_OU_FUEL_LEGER);
        session.saveOrUpdate(a16AnswerValue1);

        QuestionAnswer a17Answer1 = new QuestionAnswer(period1, scope, user1, a17, repetitionIndex);
        session.saveOrUpdate(a17Answer1);
        DoubleAnswerValue a17Answer1Value = new DoubleAnswerValue(a17Answer1, 20.6, gj);
        session.saveOrUpdate(a17Answer1Value);

        // Second set of answers
        repetitionIndex = 2;
        QuestionAnswer a16Answer2 = new QuestionAnswer(period1, scope, user1, a16, 2);
        session.saveOrUpdate(a16Answer2);
        CodeAnswerValue a16Answer2Value = new CodeAnswerValue(a16Answer2, ActivitySourceCode.BOIS_BUCHE);
        session.saveOrUpdate(a16Answer2Value);

        QuestionAnswer a17Answer2 = new QuestionAnswer(period1, scope, user1, a17, 2);
        session.saveOrUpdate(a17Answer2);
        DoubleAnswerValue a17Answer2Value = new DoubleAnswerValue(a17Answer2, 60523.0, kcal);
        session.saveOrUpdate(a17Answer2Value);

    }

    private static Unit getUnitBySymbol(String symbol) {
        return JPA.em().createNamedQuery(Unit.FIND_BY_SYMBOL, Unit.class).setParameter("symbol", symbol).getSingleResult();
    }

    private static UnitCategory getUnitCategoryByName(String name) {
        return JPA.em().createNamedQuery(UnitCategory.FIND_BY_NAME, UnitCategory.class).setParameter("name", name).getSingleResult();
    }

}
