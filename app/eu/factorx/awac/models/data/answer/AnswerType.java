package eu.factorx.awac.models.data.answer;

import java.io.Serializable;

import eu.factorx.awac.models.data.answer.type.BooleanAnswerValue;
import eu.factorx.awac.models.data.answer.type.CodeAnswerValue;
import eu.factorx.awac.models.data.answer.type.EntityAnswerValue;
import eu.factorx.awac.models.data.answer.type.IntegerAnswerValue;
import eu.factorx.awac.models.data.answer.type.StringAnswerValue;

public class AnswerType implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final AnswerType BOOLEAN = new AnswerType(1, BooleanAnswerValue.class);
	public static final AnswerType NUMERIC = new AnswerType(2, IntegerAnswerValue.class);
	public static final AnswerType STRING = new AnswerType(4, StringAnswerValue.class);
	public static final AnswerType VALUE_SELECTION = new AnswerType(5, CodeAnswerValue.class);
	public static final AnswerType ENTITY_SELECTION = new AnswerType(6, EntityAnswerValue.class);

	private Integer key;

	private Class<? extends AnswerValue> answerValueType;

	protected AnswerType() {
		super();
	}

	private AnswerType(Integer key, Class<? extends AnswerValue> answerValueType) {
		this.key = key;
		this.answerValueType = answerValueType;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Class<? extends AnswerValue> getAnswerValueType() {
		return answerValueType;
	}

	public void setAnswerValueType(Class<? extends AnswerValue> answerValueType) {
		this.answerValueType = answerValueType;
	}

	
}
