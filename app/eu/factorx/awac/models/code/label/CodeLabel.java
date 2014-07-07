package eu.factorx.awac.models.code.label;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.CompareToBuilder;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.type.LanguageCode;

@Entity
@Table(name = "code_label")
public class CodeLabel extends AbstractEntity implements Serializable, Comparable<CodeLabel> {

	private static final long serialVersionUID = 1L;

	@Embedded
	private Code code;

	private String labelEn;

	private String labelFr;

	private String labelNl;

	protected CodeLabel() {
		super();
	}

	public CodeLabel(Code code, String labelEn, String labelFr, String labelNl) {
		super();
		this.code = code;
		this.labelEn = labelEn;
		this.labelFr = labelFr;
		this.labelNl = labelNl;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
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
	public int compareTo(CodeLabel obj) {
		return new CompareToBuilder()
				.append(this.getCode(), obj.getCode())
				.append(this.getLabelEn(), obj.getLabelEn())
				.append(this.getLabelFr(), obj.getLabelFr())
				.append(this.getLabelNl(), obj.getLabelNl())
				.toComparison();
	}

}
