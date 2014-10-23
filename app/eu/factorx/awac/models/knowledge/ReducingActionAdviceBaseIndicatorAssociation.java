package eu.factorx.awac.models.knowledge;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import eu.factorx.awac.models.AbstractEntity;

@Entity
@Table(name = "mm_reducingactionadvice_baseindicator", uniqueConstraints = {
		@UniqueConstraint(columnNames = { ReducingActionAdviceBaseIndicatorAssociation.ACTION_ADVICE_COLUMN,
				ReducingActionAdviceBaseIndicatorAssociation.BASE_INDICATOR_COLUMN })
})
public class ReducingActionAdviceBaseIndicatorAssociation extends AbstractEntity {

	public static final String ACTION_ADVICE_COLUMN = "actionadvice_id";
	public static final String BASE_INDICATOR_COLUMN = "baseindicator_id";

	private static final long serialVersionUID = 6627513478859914699L;

	@ManyToOne
	@JoinColumn(name = ACTION_ADVICE_COLUMN, nullable = false)
	private ReducingActionAdvice actionAdvice;

	@ManyToOne
	@JoinColumn(name = BASE_INDICATOR_COLUMN, nullable = false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private BaseIndicator baseIndicator;

	private Double percent;

	public ReducingActionAdviceBaseIndicatorAssociation() {
		super();
	}

	public ReducingActionAdviceBaseIndicatorAssociation(ReducingActionAdvice actionAdvice, BaseIndicator baseIndicator, Double percent) {
		super();
		this.actionAdvice = actionAdvice;
		this.baseIndicator = baseIndicator;
		this.percent = percent;
	}

	public ReducingActionAdvice getActionAdvice() {
		return actionAdvice;
	}

	public void setActionAdvice(ReducingActionAdvice actionAdvice) {
		this.actionAdvice = actionAdvice;
	}

	public BaseIndicator getBaseIndicator() {
		return baseIndicator;
	}

	public void setBaseIndicator(BaseIndicator baseIndicator) {
		this.baseIndicator = baseIndicator;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

}
