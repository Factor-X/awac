package eu.factorx.awac.util.data.importer;

import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.label.CodeLabel;

public class CodeExtract<T extends Code> implements Comparable<CodeExtract<T>> {

	private String name;
	private T code;
	private CodeLabel codeLabel;

	public CodeExtract(String name, T code, CodeLabel codeLabel) {
		super();
		this.name = name;
		this.code = code;
		this.codeLabel = codeLabel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getCode() {
		return code;
	}

	public void setCode(T code) {
		this.code = code;
	}

	public CodeLabel getCodeLabel() {
		return codeLabel;
	}

	public void setCodeLabel(CodeLabel codeLabel) {
		this.codeLabel = codeLabel;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		CodeExtract<T> rhs = (CodeExtract<T>) obj;
		return this.getCode().getKey().equals(rhs.getCode().getKey());
	}

	@Override
	public int compareTo(CodeExtract<T> o) {
		return Integer.valueOf(this.getCode().getKey()).compareTo(Integer.valueOf(o.getCode().getKey()));
	}

}
