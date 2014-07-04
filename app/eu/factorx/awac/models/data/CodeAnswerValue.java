package eu.factorx.awac.models.data;

import eu.factorx.awac.models.code.Code;

public class CodeAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	private Code value;

	public CodeAnswerValue() {
		super();
	}

	public CodeAnswerValue(Code value) {
		super();
		this.value = value;
	}

	public Code getValue() {
		return value;
	}

	public void setValue(Code value) {
		this.value = value;
	}

}