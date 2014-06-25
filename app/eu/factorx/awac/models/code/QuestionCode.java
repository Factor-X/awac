package eu.factorx.awac.models.code;

import javax.persistence.Embeddable;

import play.db.ebean.Model;

@Embeddable
public class QuestionCode extends Model {

	private static final long serialVersionUID = 1L;

	// main heating fuel 
	public static final QuestionCode MHF = new QuestionCode("MHF");
	// heating fuel consumption
	public static final QuestionCode HFC = new QuestionCode("HFC");
	// housing type
	public static final QuestionCode HOT = new QuestionCode("HOT");	
	
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
