package eu.factorx.awac.models.data;

import javax.persistence.Entity;
import javax.persistence.Table;

import eu.factorx.awac.models.code.Code;

//@Entity
//@Table(name = "code_answer")
public class CodeAnswer<T extends Code> extends QuestionAnswer {

	private static final long serialVersionUID = 1L;

	public CodeAnswer() {
	}

	T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}