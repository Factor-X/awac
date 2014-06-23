package eu.factorx.awac.models.data;

import javax.persistence.MappedSuperclass;

import eu.factorx.awac.models.knowledge.Unit;

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