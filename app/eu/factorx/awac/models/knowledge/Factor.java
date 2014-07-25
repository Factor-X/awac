package eu.factorx.awac.models.knowledge;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "factor")
@NamedQueries({
		@NamedQuery(name = Factor.FIND_BY_PARAMETERS, query = "select f from Factor f where f.indicatorCategory = :indicatorCategory and f.activitySource = :activitySource and f.activityType = :activityType and f.unitIn = :unitIn and f.unitOut = :unitOut and f.values is not empty"),
})
public class Factor extends AbstractEntity {

	/**
	 * @param indicatorCategory : an {@link IndicatorCategoryCode}
	 * @param activitySource : an {@link ActivitySourceCode}
	 * @param activityType : an {@link ActivityTypeCode}
	 * @param unitIn : a {@link Unit}
	 * @param unitOut : a {@link Unit}
	 */
	public static final String FIND_BY_PARAMETERS = "Factor.findByParameters";
	private static final long serialVersionUID = 1L;

	@Embedded
	private IndicatorCategoryCode indicatorCategory;

	@Embedded
	private ActivityTypeCode activityType;

	@Embedded
	private ActivitySourceCode activitySource;

	@ManyToOne
	private Unit unitIn;

	@ManyToOne
	private Unit unitOut;

	private String institution;

	@OneToMany(mappedBy = "factor", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<FactorValue> values;

	protected Factor() {
		super();
	}

	public Factor(IndicatorCategoryCode indicatorCategory, ActivityTypeCode activityType,
	              ActivitySourceCode activitySource, Unit unitIn, Unit unitOut, String institution) {
		super();
		this.indicatorCategory = indicatorCategory;
		this.activityType = activityType;
		this.activitySource = activitySource;
		this.unitIn = unitIn;
		this.unitOut = unitOut;
		this.institution = institution;
	}

	public IndicatorCategoryCode getIndicatorCategory() {
		return indicatorCategory;
	}

	public void setIndicatorCategory(IndicatorCategoryCode indicatorCategory) {
		this.indicatorCategory = indicatorCategory;
	}

	public ActivityTypeCode getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityTypeCode activityType) {
		this.activityType = activityType;
	}

	public ActivitySourceCode getActivitySource() {
		return activitySource;
	}

	public void setActivitySource(ActivitySourceCode activitySource) {
		this.activitySource = activitySource;
	}

	public Unit getUnitIn() {
		return unitIn;
	}

	public void setUnitIn(Unit unitIn) {
		this.unitIn = unitIn;
	}

	public Unit getUnitOut() {
		return unitOut;
	}

	public void setUnitOut(Unit unitOut) {
		this.unitOut = unitOut;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public List<FactorValue> getValues() {
		return values;
	}

	public void setValues(List<FactorValue> values) {
		this.values = values;
	}

	public Double getCurrentValue() {
		FactorValue factorValue = this.values.get(0); // TODO Find this value from the current date
		return factorValue.getValue();
	}

	@Override
	public String toString() {
		return "Factor [indicatorCategory=" + indicatorCategory + ", activityType=" + activityType + ", activitySource=" + activitySource
				+ ", unitIn=" + unitIn + ", unitOut=" + unitOut + ", institution=" + institution + ", values=" + values + "]";
	}

	
}