package eu.factorx.awac.models.code;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class QuestionCode implements Serializable {

	private static final long serialVersionUID = 1L;

	// heating fuel questions set
	public static final QuestionCode HF_SET = new QuestionCode("HF_SET");
	// heating fuel type 
	public static final QuestionCode HF_HFT = new QuestionCode("HF_HFT");
	// heating fuel consumption
	public static final QuestionCode HF_HFC = new QuestionCode("HF_HFC");

	private String value;

	public QuestionCode() {
		super();
	}

	public QuestionCode(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
