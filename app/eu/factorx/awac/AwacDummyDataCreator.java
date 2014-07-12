package eu.factorx.awac;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;

import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.HeatingFuelTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.data.question.type.IntegerQuestion;
import eu.factorx.awac.models.data.question.type.ValueSelectionQuestion;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.util.data.importer.AwacDataImporter;
import eu.factorx.awac.util.data.importer.MyrmexUnitsImporter;

public class AwacDummyDataCreator {

	public static void createAwacDummyData(ApplicationContext ctx, Session session) {

		// IMPORT MYRMEX UNITS
		new MyrmexUnitsImporter().run(false);
		
		// IMPORT AWAC DATA
		new AwacDataImporter().run(false);

		// REFERENCES DATA

		session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.GAS, "Gas", "Gaz", "Gas"));
		session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.OIL, "Oil", "Mazout", "Brandstof"));
		session.saveOrUpdate(new CodeLabel(HeatingFuelTypeCode.COAL, "Coal", "Charbon", "Steenkool"));

		session.saveOrUpdate(new CodeLabel(LanguageCode.ENGLISH, "English", "Anglais", "Engels"));
		session.saveOrUpdate(new CodeLabel(LanguageCode.FRENCH, "French", "Français", "Frans"));
		session.saveOrUpdate(new CodeLabel(LanguageCode.DUTCH, "Dutch", "Néerlandais", "Nederlands"));

		// MINIMAL BUSINESS DATA

		Organization factorx = new Organization("Factor-X");
		session.saveOrUpdate(factorx);

		Account user1 = new Account(factorx, "user1", "password", "user1_lastname", "user1_firstname");
		user1.setAge(25);
		session.saveOrUpdate(user1);

		Period period1 = new Period("2013");
		session.saveOrUpdate(period1);

        // TAB 1

        Form tab1Form = new Form("TAB1");
        session.saveOrUpdate(tab1Form);

        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTOR, "1", "Industrie primaire, hormis le secteur agricole", "Industrie primaire, hormis le secteur agricole", "Industrie primaire, hormis le secteur agricole"));
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTOR, "2", "Production de biens intermédiaires", "Production de biens intermédiaires", "Production de biens intermédiaires"));
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTOR, "3", "Production de biens de consommation", "Production de biens de consommation", "Production de biens de consommation"));
        session.saveOrUpdate(new CodeLabel(CodeList.SITE_SECTOR, "4", "Tertiaire", "Tertiaire", "Tertiaire"));

        QuestionSet tab1qs = new QuestionSet(QuestionCode.A1, false);
        session.saveOrUpdate(tab1qs);

        tab1Form.getQuestionSet().add(tab1qs);
        session.saveOrUpdate(tab1Form);


        // -- QUESTIONS

        Question a2 = new IntegerQuestion(tab1qs, 0, QuestionCode.A2, null);
        session.saveOrUpdate(a2);

        Question a3 = new ValueSelectionQuestion(tab1qs, 0, QuestionCode.A3, CodeList.SITE_SECTOR);
        session.saveOrUpdate(a3);

        
    }
}
