package eu.factorx.awac.models.data.answer.type;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;

@Entity
public class StringAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	private String value;

	protected StringAnswerValue() {
		super();
	}

	public StringAnswerValue(QuestionAnswer<StringAnswerValue> questionAnswer, String value) {
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

	
}