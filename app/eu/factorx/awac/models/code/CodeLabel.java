package eu.factorx.awac.models.code;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.CompareToBuilder;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "code_label")
public class CodeLabel extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "code_type")), })
	private CodeType codeType;

	private Integer codeValue;

	private String labelEn;

	private String labelFr;

	private String labelNl;

	protected CodeLabel() {
		super();
	}

	public CodeLabel(Code code, String labelEn, String labelFr, String labelNl) {
		this(code.getCodeType(), code.getValue(), labelEn, labelFr, labelNl);
	}

	public CodeLabel(CodeType codeType, Integer codeValue, String labelEn, String labelFr, String labelNl) {
		super();
		this.codeType = codeType;
		this.codeValue = codeValue;
		this.labelEn = labelEn;
		this.labelFr = labelFr;
		this.labelNl = labelNl;
	}

	public CodeType getCodeType() {
		return codeType;
	}

	public void setCodeType(CodeType codeType) {
		this.codeType = codeType;
	}

	public Integer getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(Integer codeValue) {
		this.codeValue = codeValue;
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
	public int compareTo(AbstractEntity o) {
		CompareToBuilder ctb = new CompareToBuilder();
		//***
		return ctb.toComparison();
	}
}
