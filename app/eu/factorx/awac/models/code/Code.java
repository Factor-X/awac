package eu.factorx.awac.models.code;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import org.apache.commons.lang3.builder.*;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * A <b>Code</b> is an element of a {@link CodeList}, defined as a tuple { {@link CodeList} codeList, {@link String} key}.<br>
 * <br>
 * The programmer will typically define 'constant' instances of a Code subclass when he needs to deal with a particular data without assuming the status of the database.<br>
 * <br>
 * <b>Example</b>: The {@link QuestionCode questionCode} of a {@link Question question} must be known (at development time) to write the algorithms of consolidation and calculation
 * involving the answer(s) to this question (base activity data, then reports).
 */
@MappedSuperclass
public class Code implements Serializable, Comparable<Code> {

	private static final long serialVersionUID = 1L;

	@Transient
	protected CodeList codeList;

	protected String key;

	protected Code() {
		super();
	}

	protected Code(CodeList codeList) {
		super();
		this.codeList = codeList;
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Code)) {
			return false;
		}
		Code rhs = (Code) obj;
		return new EqualsBuilder().append(this.codeList, rhs.codeList).append(this.key, rhs.key).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(29, 7).append(this.codeList).append(this.key).toHashCode();
	}

	@Override
	public int compareTo(Code o) {
		return new CompareToBuilder().append(this.codeList, o.codeList).append(this.key, o.key).toComparison();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("type", codeList).append("key", key)
				.toString();
	}
}
