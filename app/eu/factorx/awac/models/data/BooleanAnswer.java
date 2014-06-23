package eu.factorx.awac.models.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "boolean_answer")
public class BooleanAnswer extends QuestionAnswer {

	private static final long serialVersionUID = 1L;
	private Boolean value;

	public BooleanAnswer() {
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean param) {
		this.value = param;
	}

}