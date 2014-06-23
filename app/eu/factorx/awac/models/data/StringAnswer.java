package eu.factorx.awac.models.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "StringAnswer")
public class StringAnswer extends QuestionAnswer implements Serializable {

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