package uml.data;

import uml.knowledge.Unit;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class NumericAnswer extends QuestionAnswer {

    private static final long serialVersionUID = 1L;
    protected Unit unit;

    public NumericAnswer() {
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit param) {
        this.unit = param;
    }

}