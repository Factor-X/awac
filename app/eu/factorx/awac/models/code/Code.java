package eu.factorx.awac.models.code;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.CompareToBuilder;

@MappedSuperclass
public abstract class Code implements Serializable, Comparable<Code> {

	private static final long serialVersionUID = 1L;

	protected CodeType codeType;

	protected Integer value;

	protected Code() {
		super();
	}

	protected Code(CodeType codeType, Integer value) {
		super();
		this.codeType = codeType;
		this.value = value;
	}

	public CodeType getCodeType() {
		return codeType;
	}

	public void setCodeType(CodeType codeType) {
		this.codeType = codeType;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public int compareTo(Code o) {
		CompareToBuilder ctb = new CompareToBuilder().append(this.codeType, o.codeType).append(this.value, o.value);
		return ctb.toComparison();
	}

}
