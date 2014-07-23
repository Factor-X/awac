package eu.factorx.awac.models.knowledge;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "period")
public class Period extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String label;

	public Period() {
	}

	public Period(String label) {
		super();
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String param) {
		this.label = param;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Period)) {
			return false;
		}
		Period rhs = (Period) obj;
		return new EqualsBuilder().append(this.label, rhs.label).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 67).append(this.label).toHashCode();
	}

}