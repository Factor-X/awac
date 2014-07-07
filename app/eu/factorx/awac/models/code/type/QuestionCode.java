package eu.factorx.awac.models.code.type;

import javax.persistence.Embeddable;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;

@Embeddable
public class QuestionCode implements Code {

	private static final long serialVersionUID = 1L;

	public static final CodeList CODE_TYPE = CodeList.QUESTION;

	public static final QuestionCode HFQS = new QuestionCode("HFQS");
	public static final QuestionCode HFQ_HFTYPE = new QuestionCode("HFQ_HFTYPE");
	public static final QuestionCode HFQ_HFCONSO_VOL = new QuestionCode("HFQ_HFCONSO_VOL");
	public static final QuestionCode HFQ_HFCONSO_MASS = new QuestionCode("HFQ_HFCONSO_MASS");

	protected CodeList codeList;

	protected String key;

	protected QuestionCode() {
		super();
	}

	public QuestionCode(String key) {
		super();
		this.key = key;
		this.codeList= CODE_TYPE;
	}

	public CodeList getCodeList() {
		return codeList;
	}

	public void setCodeList(CodeList codeList) {
		this.codeList = codeList;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
