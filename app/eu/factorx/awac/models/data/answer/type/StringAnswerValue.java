package eu.factorx.awac.models.data.answer.type;

import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class StringAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	private String value;

	protected StringAnswerValue() {
		super();
	}

	public StringAnswerValue(QuestionAnswer questionAnswer, String value) {
		super();
		this.questionAnswer = questionAnswer;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String param) {
		this.value = param;
	}

	@Override
	protected AnswerRawData getRawData() {
		return new AnswerRawData(value);
	}

	@Override
	protected void setRawData(AnswerRawData rawData) {
		this.value = StringUtils.trimToNull(rawData.getStringData1());

	}

	@Override
	public String toString() {
		return "StringAnswerValue [value=" + value + "]";
	}

}