package eu.factorx.awac.models.code.label;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.CompareToBuilder;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.type.LanguageCode;

@Entity
@Table(name = "code_label")
public class CodeLabel<T extends Code> extends AbstractEntity implements Serializable, Comparable<CodeLabel<T>> {

	private static final long serialVersionUID = 1L;

	private T code;

	private String labelEn;

	private String labelFr;

	private String labelNl;

	protected CodeLabel() {
		super();
	}

	public CodeLabel(T code, String labelEn, String labelFr, String labelNl) {
		super();
		this.code = code;
		this.labelEn = labelEn;
		this.labelFr = labelFr;
		this.labelNl = labelNl;
	}

	public T getCode() {
		return code;
	}

	public void setCode(T code) {
		this.code = code;
	}

	public String getLabelEn() {
		return labelEn;
	}

	public void setLabelEn(String labelEn) {
		this.labelEn = labelEn;
	}

	public String getLabelFr() {
		return labelFr;
	}

	public void setLabelFr(String labelFr) {
		this.labelFr = labelFr;
	}

	public String getLabelNl() {
		return labelNl;
	}

	public void setLabelNl(String labelNl) {
		this.labelNl = labelNl;
	}

	public String getLabel(LanguageCode languageCode) {
		if (LanguageCode.ENGLISH.equals(languageCode)) {
			return labelEn;
		}
		if (LanguageCode.FRENCH.equals(languageCode)) {
			return labelFr;
		}
		if (LanguageCode.DUTCH.equals(languageCode)) {
			return labelNl;
		}

		return labelEn + " [no translation available]";
	}

	@Override
	public int compareTo(CodeLabel<T> obj) {
		return new CompareToBuilder().append(this.getCode().getCodeList(), obj.getCode().getCodeList())
				.append(this.getCode().getKey(), obj.getCode().getKey()).toComparison();
	}

}
