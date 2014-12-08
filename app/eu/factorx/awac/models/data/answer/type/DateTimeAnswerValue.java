package eu.factorx.awac.models.data.answer.type;

import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DATE_TIME")
public class DateTimeAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	private DateTime dateTime;

	public DateTimeAnswerValue() {
	}

	public DateTimeAnswerValue(QuestionAnswer questionAnswer, DateTime dateTime) {
		this.questionAnswer = questionAnswer;
		this.dateTime = dateTime;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	protected AnswerRawData getRawData() {
		return new AnswerRawData();
	}

	@Override
	protected void setRawData(AnswerRawData rawData) {
	}

	@Override
	public String toString() {
		return dateTime.toString(DateTimeFormat.fullTime());
	}
}
