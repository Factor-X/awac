package eu.factorx.awac.models.code.type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "question")) })
public class QuestionCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final QuestionCode HFQS = new QuestionCode("HFQS");
	public static final QuestionCode HFQ_HFTYPE = new QuestionCode("HFQ_HFTYPE");
	public static final QuestionCode HFQ_HFCONSO_VOL = new QuestionCode("HFQ_HFCONSO_VOL");
	public static final QuestionCode HFQ_HFCONSO_MASS = new QuestionCode("HFQ_HFCONSO_MASS");

	public static final QuestionCode A1 = new QuestionCode("A1");
	public static final QuestionCode A2 = new QuestionCode("A2");
	public static final QuestionCode A3 = new QuestionCode("A3");

	protected QuestionCode() {
		super(CodeList.QUESTION);
	}

	public QuestionCode(String key) {
		this();
		this.key = key;
	}

}
