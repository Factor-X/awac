package eu.factorx.awac.models.code;

import java.io.Serializable;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;

/**
 * 
 * A <b>Code</b> is an element of a {@link CodeList}, defined as a tuple {
 * {@link CodeList} codeList, {@link String} key}.<br>
 * <br>
 * The programmer will typically define 'constant' instances of a Code subclass
 * when he needs to deal with a particular data without assuming the status of
 * the database.<br>
 * <br>
 * <b>Example</b>: The {@link QuestionCode questionCode} of a {@link Question
 * question} must be known (at compilation time) to write the algorithms of
 * consolidation and calculation involving the answer to this question (reports
 * and indicators).
 * 
 */
public class Code implements Serializable {

	private static final long serialVersionUID = 1L;

	protected CodeList codeList;

	protected String key;

	protected Code() {
		super();
	}

	public Code(CodeList codeList, String key) {
		super();
		this.codeList = codeList;
		this.key = key;
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
