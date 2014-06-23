package eu.factorx.awac.models.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "string_answer")
public class StringAnswer extends QuestionAnswer {

	private static final long serialVersionUID = 1L;
	private String value;

	public StringAnswer() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String param) {
		this.value = param;
	}

}