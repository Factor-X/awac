package eu.factorx.awac.models.data.question.type;

import eu.factorx.awac.models.code.type.QuestionCode;
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


    protected Double defaultValue = null;

    @ManyToOne(optional = true)
    protected Unit defaultUnit;

    protected NumericQuestion() {
        super();
    }

    protected NumericQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory) {
        super(questionSet, orderIndex, code);
        this.unitCategory = unitCategory;
    }

    protected NumericQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory, Double defaultValue) {
        super(questionSet, orderIndex, code);
        this.unitCategory = unitCategory;
        this.defaultValue = defaultValue;
    }

    protected NumericQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory, Double defaultValue, Unit defaultUnit) {
        super(questionSet, orderIndex, code);
        this.unitCategory = unitCategory;
        this.defaultUnit = defaultUnit;
        this.defaultValue = defaultValue;
    }

    protected NumericQuestion(QuestionSet questionSet, int orderIndex, QuestionCode code, UnitCategory unitCategory, Unit defaultUnit) {
        super(questionSet, orderIndex, code);
        this.unitCategory = unitCategory;
        this.defaultUnit = defaultUnit;
    }

    public UnitCategory getUnitCategory() {
        return unitCategory;
    }

    public void setUnitCategory(UnitCategory unitCategory) {
        this.unitCategory = unitCategory;
    }

    public Double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Unit getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(Unit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }
}
