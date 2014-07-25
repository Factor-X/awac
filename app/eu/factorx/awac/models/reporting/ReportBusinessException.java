package eu.factorx.awac.models.reporting;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.knowledge.Indicator;

@Entity
public class ReportBusinessException extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	public enum ErrorType {
		NO_SUITABLE_INDICATOR, NO_SUITABLE_FACTOR,
	}

	@Enumerated(EnumType.STRING)
	private ErrorType errorType;

	@Embedded
	private BaseActivityData baseActivityData;

	@ManyToOne
	private Indicator indicator;

	public ReportBusinessException() {
		super();
	}

	/**
	 * @param errorType
	 * @param baseActivityData
	 * @param indicator
	 */
	public ReportBusinessException(ErrorType errorType, BaseActivityData baseActivityData, Indicator indicator) {
		super();
		this.errorType = errorType;
		this.baseActivityData = baseActivityData;
		this.indicator = indicator;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	public BaseActivityData getBaseActivityData() {
		return baseActivityData;
	}

	public void setBaseActivityData(BaseActivityData baseActivityData) {
		this.baseActivityData = baseActivityData;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

}
