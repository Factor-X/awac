package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import eu.factorx.awac.models.code.Code;

@Entity
@Table(name = "CodeAnswer")
public class CodeAnswer<T extends Code> extends QuestionAnswer implements Serializable {

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