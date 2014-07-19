package eu.factorx.awac.models.data.answer.type;

import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BooleanAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	private Boolean value;

	protected BooleanAnswerValue() {
		super();
	}

	public BooleanAnswerValue(QuestionAnswer questionAnswer, Boolean value) {
		super();
		this.questionAnswer = questionAnswer;
		this.value = value;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean param) {
		this.value = param;
	}

	@Override
	protected AnswerRawData getRawData() {
		return new AnswerRawData(value);
	}

	@Override
	protected void setRawData(AnswerRawData rawData) {
		this.value = rawData.getBooleanData();
	}

}