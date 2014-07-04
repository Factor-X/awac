package eu.factorx.awac.models.data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.Code;

@Entity
@Table(name = "answer_data")
public class AnswerData extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private QuestionAnswer questionAnswer;

	// string data
	private String strData;

	// boolean data
	private Boolean booleanData;

	// numeric data
	private Double doubleData;

	// foreign key
	private Long longData;

	public AnswerData() {
		super();
	}

	public AnswerData(QuestionAnswer questionAnswer, Code code) {
		super();
		this.questionAnswer = questionAnswer;
		this.doubleData = new Double(code.getValue());
	}

	public AnswerData(QuestionAnswer questionAnswer, Integer intValue) {
		super();
		this.questionAnswer = questionAnswer;
		this.doubleData = new Double(intValue);
	}

	public QuestionAnswer getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(QuestionAnswer questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getStrData() {
		return strData;
	}

	public void setStrData(String strData) {
		this.strData = strData;
	}

	public Boolean getBooleanData() {
		return booleanData;
	}

	public void setBooleanData(Boolean booleanData) {
		this.booleanData = booleanData;
	}

	public Double getDoubleData() {
		return doubleData;
	}

	public void setDoubleData(Double doubleData) {
		this.doubleData = doubleData;
	}

	public Long getLongData() {
		return longData;
	}

	public void setLongData(Long longData) {
		this.longData = longData;
	}

}
