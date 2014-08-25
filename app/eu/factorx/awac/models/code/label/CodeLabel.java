package eu.factorx.awac.models.code.label;

import java.io.Serializable;

import javax.persistence.*;

import org.apache.commons.lang3.builder.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.LanguageCode;

@Entity
@Table(name = "code_label", uniqueConstraints = { @UniqueConstraint(columnNames = { CodeLabel.COLUMN_NAME_CODELIST,
		CodeLabel.COLUMN_NAME_KEY }) })
@NamedQueries({
		@NamedQuery(name = CodeLabel.FIND_BY_LIST, query = "select cl from CodeLabel cl where cl.codeList = :codeList"),
		@NamedQuery(name = CodeLabel.FIND_KEYS_BY_LIST, query = "select cl.key from CodeLabel cl where cl.codeList = :codeList"),
		@NamedQuery(name = CodeLabel.FIND_ALL, query = "select cl from CodeLabel cl"),
		@NamedQuery(name = CodeLabel.REMOVE_BY_LIST, query = "delete from CodeLabel cl where cl.codeList = :codeList")
})
public class CodeLabel extends AuditedAbstractEntity implements Serializable, Comparable<CodeLabel> {

	public static final String COLUMN_NAME_CODELIST = "codelist";
	public static final String COLUMN_NAME_KEY = "key";
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_LIST = "CodeLabel.findByList";
	public static final String FIND_KEYS_BY_LIST = "CodeLabel.findKeysByList";
	public static final String FIND_ALL = "CodeLabel.findAll";
	public static final String REMOVE_BY_LIST = "CodeLabel.removeByList";

	@Enumerated(EnumType.STRING)
	@Column(name = COLUMN_NAME_CODELIST, nullable = false)
	private CodeList codeList;

	@Column(name = COLUMN_NAME_KEY, nullable = false)
	private String key;

	@Column(nullable = false, length = 4096)
	private String labelEn;

	@Column(length = 4096)
	private String labelFr;

	@Column(length = 4096)
	private String labelNl;

	protected CodeLabel() {
		super();
	}

	public CodeLabel(CodeList codeList, String key, String labelEn, String labelFr, String labelNl) {
		this.codeList = codeList;
		this.key = key;
		this.labelEn = labelEn;
		this.labelFr = labelFr;
		this.labelNl = labelNl;
	}

	public CodeLabel(Code code, String labelEn, String labelFr, String labelNl) {
		super();
		this.codeList = code.getCodeList();
		this.key = code.getKey();
		this.labelEn = labelEn;
		this.labelFr = labelFr;
		this.labelNl = labelNl;
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
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof CodeLabel)) {
			return false;
		}
		CodeLabel rhs = (CodeLabel) obj;
		return new EqualsBuilder().append(this.codeList, rhs.codeList).append(this.key, rhs.key).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(3, 53).append(this.codeList).append(this.key).toHashCode();
	}

	@Override
	public int compareTo(CodeLabel obj) {
		return new CompareToBuilder().append(this.getCodeList(), obj.getCodeList()).append(this.getKey(), obj.getKey()).toComparison();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(codeList.name()).append(key).toString();
	}
}
