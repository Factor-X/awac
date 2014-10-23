package eu.factorx.awac.models.data.question.type;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.knowledge.UnitCategory;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NumericQuestion extends Question {

    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = true)
    protected UnitCategory unitCategory;

    @ManyToOne(optional = true)
    protected Driver driver;

    @ManyToOne(optional = true)
    protected Unit defaultUnit;

    protected NumericQuestion() {
        super();
    }

    protected NumericQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory) {
        super(questionSet, orderIndex, code);
        this.unitCategory = unitCategory;
    }

    public NumericQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory, Unit defaultUnit) {
        super(questionSet, orderIndex, code);
        this.unitCategory = unitCategory;
        this.defaultUnit=defaultUnit;
    }


    public UnitCategory getUnitCategory() {
        return unitCategory;
    }

    public void setUnitCategory(UnitCategory unitCategory) {
        this.unitCategory = unitCategory;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Unit getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(Unit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    @Override
    public String toString() {
        return "NumericQuestion{" +
                "unitCategory=" + unitCategory +
                ", driver=" + driver +
                ", defaultUnit=" + defaultUnit +
                '}';
    }
}
