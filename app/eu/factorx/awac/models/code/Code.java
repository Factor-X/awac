package eu.factorx.awac.models.code;

import java.io.Serializable;

import org.apache.commons.lang3.builder.CompareToBuilder;

public abstract class Code implements Serializable, Comparable<Code> {

	private static final long serialVersionUID = 1L;

	protected CodeType type;

	protected Integer value;

	public CodeType getType() {
		return type;
	}

	public void setType(CodeType type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public int compareTo(Code o) {
		CompareToBuilder ctb = new CompareToBuilder().append(this.type, o.type).append(this.value, o.value);
		return ctb.toComparison();
	}

}
