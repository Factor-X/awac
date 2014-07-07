package eu.factorx.awac.models.data.answer.type;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.data.answer.AnswerRawData;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;

public class CodeAnswerValue<T extends Code> extends AnswerValue {

	private static final long serialVersionUID = 1L;

	@Transient
	private T value;

	protected CodeAnswerValue() {
		super();
	}

	public CodeAnswerValue(QuestionAnswer<CodeAnswerValue<T>> questionAnswer, T value) {
		super();
		this.questionAnswer = questionAnswer;
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	protected AnswerRawData getRawData() {
		return new AnswerRawData(value.getCodeList().name(), value.getKey());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void setRawData(AnswerRawData rawData) {
		CodeList codeList = CodeList.valueOf(StringUtils.trim(rawData.getStringData1()));
		String key = rawData.getStringData2();
		value = (T) new Code(codeList, key);
	}
}