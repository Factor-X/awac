package eu.factorx.awac.models.reporting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.factorx.awac.models.AuditedAbstractEntity;

@Entity
public class ReportBusinessException extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	public enum ErrorType {
		NO_SUITABLE_INDICATOR, NO_SUITABLE_FACTOR,
	}

	@Enumerated(EnumType.STRING)
	private ErrorType errorType;

	private String baseActivityDataKey;

	private String indicatorKey;

	@Column(columnDefinition = "TEXT")
	private String searchParameter;

	public ReportBusinessException() {
		super();
	}

	/**
	 * @param errorType
	 * @param baseActivityDataKey
	 * @param indicatorKey
	 * @param searchParameter
	 */
	public ReportBusinessException(ErrorType errorType, String baseActivityDataKey, String indicatorKey, String searchParameter) {
		super();
		this.errorType = errorType;
		this.baseActivityDataKey = baseActivityDataKey;
		this.indicatorKey = indicatorKey;
		this.searchParameter = searchParameter;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	public String getBaseActivityDataKey() {
		return baseActivityDataKey;
	}

	public void setBaseActivityDataKey(String baseActivityDataKey) {
		this.baseActivityDataKey = baseActivityDataKey;
	}

	public String getIndicatorKey() {
		return indicatorKey;
	}

	public void setIndicatorKey(String indicatorKey) {
		this.indicatorKey = indicatorKey;
	}

	public String getSearchParameter() {
		return searchParameter;
	}

	public void setSearchParameter(String searchParameter) {
		this.searchParameter = searchParameter;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof ReportBusinessException)) {
			return false;
		}
		ReportBusinessException rhs = (ReportBusinessException) obj;
		return new EqualsBuilder().append(this.errorType, rhs.errorType).append(this.baseActivityDataKey, rhs.baseActivityDataKey)
				.append(this.indicatorKey, rhs.indicatorKey).append(this.searchParameter, rhs.searchParameter).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(5, 83).append(this.errorType).append(this.baseActivityDataKey).append(this.indicatorKey)
				.append(this.searchParameter).toHashCode();
	}

}
