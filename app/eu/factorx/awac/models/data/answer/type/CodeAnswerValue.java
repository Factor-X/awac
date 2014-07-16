package eu.factorx.awac.models.data.answer.type;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CodeAnswerValue extends AnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	private Code value;

	protected CodeAnswerValue() {
		super();
	}

	public CodeAnswerValue(QuestionAnswer questionAnswer, Code value) {
		super();
		this.questionAnswer = questionAnswer;
		this.value = value;
	}

	public Code getValue() {
		return value;
	}

	public void setValue(Code value) {
		this.value = value;
	}

	@Override
	protected AnswerRawData getRawData() {
		return new AnswerRawData(value.getCodeList().name(), value.getKey());
	}

	@Override
	protected void setRawData(AnswerRawData rawData) {
		CodeList codeList = CodeList.valueOf(StringUtils.trim(rawData.getStringData1()));
		String key = rawData.getStringData2();
		this.value = new Code(codeList, key);
	}

}