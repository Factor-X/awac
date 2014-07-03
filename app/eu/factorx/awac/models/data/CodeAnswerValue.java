package eu.factorx.awac.models.data;

import eu.factorx.awac.models.code.Code;

public class CodeAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	public CodeAnswerValue() {
		super();
	}

	Code value;

	public Code getValue() {
		return value;
	}

	public void setValue(Code value) {
		this.value = value;
	}

}