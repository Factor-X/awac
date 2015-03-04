package eu.factorx.awac.models.knowledge;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.BaseIndicatorCode;

import javax.persistence.*;

@Entity
@Table(name = "reducingactionadvice_baseindicator")
public class ReducingActionAdviceBaseIndicatorAssociation extends AbstractEntity {

	public static final String ACTION_ADVICE_COLUMN = "actionadvice_id";

	private static final long serialVersionUID = 6627513478859914699L;

	@ManyToOne
	@JoinColumn(name = ACTION_ADVICE_COLUMN, nullable = false)
	private ReducingActionAdvice actionAdvice;

	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "baseindicatorkey", length = 10, nullable = false))})
	private BaseIndicatorCode baseIndicatorCode;

	private Double percent;

	private Double percentMax;

	public ReducingActionAdviceBaseIndicatorAssociation() {
		super();
	}

	public ReducingActionAdviceBaseIndicatorAssociation(ReducingActionAdvice actionAdvice, BaseIndicatorCode baseIndicatorCode, Double percent, Double percentMax) {
		super();
		this.actionAdvice = actionAdvice;
		this.baseIndicatorCode = baseIndicatorCode;
		this.percent = percent;
		this.percentMax = percentMax;
	}

	public ReducingActionAdvice getActionAdvice() {
		return actionAdvice;
	}

	public void setActionAdvice(ReducingActionAdvice actionAdvice) {
		this.actionAdvice = actionAdvice;
	}

	public BaseIndicatorCode getBaseIndicatorCode() {
		return baseIndicatorCode;
	}

	public void setBaseIndicatorCode(BaseIndicatorCode baseIndicatorCode) {
		this.baseIndicatorCode = baseIndicatorCode;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Double getPercentMax() {
		return percentMax;
	}

	public void setPercentMax(Double percentMax) {
		this.percentMax = percentMax;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ReducingActionAdviceBaseIndicatorAssociation)) return false;
		if (!super.equals(o)) return false;

		ReducingActionAdviceBaseIndicatorAssociation that = (ReducingActionAdviceBaseIndicatorAssociation) o;

		if (!actionAdvice.equals(that.actionAdvice)) return false;
		if (!baseIndicatorCode.equals(that.baseIndicatorCode)) return false;
		if (!percent.equals(that.percent)) return false;
		if (!percentMax.equals(that.percentMax)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + actionAdvice.hashCode();
		result = 31 * result + baseIndicatorCode.hashCode();
		result = 31 * result + percent.hashCode();
		result = 31 * result + percentMax.hashCode();
		return result;
	}
}
