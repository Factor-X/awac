package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class BooleanAnswer extends QuestionAnswer implements Serializable {

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