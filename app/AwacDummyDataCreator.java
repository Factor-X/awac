import org.hibernate.Session;

import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.QuestionCode;
import eu.factorx.awac.models.data.Question;
import eu.factorx.awac.models.forms.Campaign;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.forms.FormQuestion;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

public class AwacDummyDataCreator {

	public static void createAwacDummyData(Session session) {

		Organization men1 = new Organization("Ménage 1");
		session.saveOrUpdate(men1);
		Organization men2 = new Organization("Ménage 2");
		session.saveOrUpdate(men2);

		UnitCategory volumeUnits = new UnitCategory("Volume");
		session.saveOrUpdate(volumeUnits);

		Unit litter = new Unit("Liter", volumeUnits);
		session.saveOrUpdate(litter);
		Unit gallon = new Unit("Gallon", volumeUnits);
		session.saveOrUpdate(gallon);
		Unit cubicMeter = new Unit("Cubic meter", volumeUnits);
		session.saveOrUpdate(cubicMeter);

		Question q1 = new Question(QuestionCode.MHF);
		session.saveOrUpdate(q1);
		Question q2 = new Question(QuestionCode.HFC, volumeUnits);
		session.saveOrUpdate(q2);
		Question q3 = new Question(QuestionCode.HOT);
		session.saveOrUpdate(q3);

		Period period1 = new Period("2013");
		session.saveOrUpdate(period1);

		Campaign awac2013 = new Campaign("AWAC 2013", period1);
		session.saveOrUpdate(awac2013);

		Form form = new Form("Formulaire Ménages", awac2013);
		session.saveOrUpdate(form);

		FormQuestion formQ1 = new FormQuestion(form, q1);
		session.saveOrUpdate(formQ1);
		FormQuestion formQ2 = new FormQuestion(form, q2);
		session.saveOrUpdate(formQ2);
		FormQuestion formQ3 = new FormQuestion(form, q3);
		session.saveOrUpdate(formQ3);

	}

}
