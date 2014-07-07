package eu.factorx.awac.models.code.type;

import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
public class QuestionCode extends Code {

	private static final long serialVersionUID = 1L;

	public static final CodeList CODE_TYPE = CodeList.QUESTION;

	public static final QuestionCode HFQS = new QuestionCode("HFQS");
	public static final QuestionCode HFQ_HFTYPE = new QuestionCode("HFQ_HFTYPE");
	public static final QuestionCode HFQ_HFCONSO_VOL = new QuestionCode("HFQ_HFCONSO_VOL");
	public static final QuestionCode HFQ_HFCONSO_MASS = new QuestionCode("HFQ_HFCONSO_MASS");

	protected QuestionCode() {
		super();
	}

	public QuestionCode(String key) {
		super(CODE_TYPE, key);
	}

}
