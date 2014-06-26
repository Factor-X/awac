import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.QuestionCode;
import eu.factorx.awac.models.data.Question;
import eu.factorx.awac.models.forms.Campaign;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.forms.FormQuestion;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

public class AwacDummyDataCreator {

    public static void createAwacDummyData() {

        Organization men1 = new Organization("Ménage 1");
        men1.save();
        Organization men2 = new Organization("Ménage 2");
        men2.save();

        Scope scopeMen1 = new Scope(men1);
        scopeMen1.save();
        Scope scopeMen2 = new Scope(men2);
        scopeMen2.save();

        UnitCategory volumeUnits = new UnitCategory("Volume");
        volumeUnits.save();

        Unit litter = new Unit("Liter", volumeUnits);
        litter.save();
        Unit gallon = new Unit("Gallon", volumeUnits);
        gallon.save();
        Unit cubicMeter = new Unit("Cubic meter", volumeUnits);
        cubicMeter.save();

        Question q1 = new Question(QuestionCode.MHF);
        q1.save();
        Question q2 = new Question(QuestionCode.HFC, volumeUnits);
        q2.save();
        Question q3 = new Question(QuestionCode.HOT);
        q3.save();

        Period period1 = new Period("2013");
        period1.save();

        Campaign awac2013 = new Campaign("AWAC 2013", period1);
        awac2013.save();

        Form form = new Form("Formulaire Ménages", awac2013);
        form.save();

        FormQuestion formQ1 = new FormQuestion(form, q1);
        formQ1.save();
        FormQuestion formQ2 = new FormQuestion(form, q2);
        formQ2.save();
        FormQuestion formQ3 = new FormQuestion(form, q3);
        formQ3.save();

    }

}
