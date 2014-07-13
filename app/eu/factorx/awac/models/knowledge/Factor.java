package eu.factorx.awac.models.knowledge;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;

@Entity
@Table(name = "factor")
@NamedQueries({
		@NamedQuery(name = Factor.FIND_BY_PARAMETERS, query = "select f from Factor f where f.indicatorCategory = :indicatorCategory and f.activitySource = :activitySource and f.activityType = :activityType and f.unitIn = :unitIn and f.unitOut = :unitOut and f.values is not empty"),
})
public class Factor extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * Parameters:<br>
	 * - indicatorCategory : an {@link IndicatorCategoryCode}<br>
	 * - activitySource : an {@link ActivitySourceCode}<br>
	 * - activityType : an {@link ActivityTypeCode}<br>
	 * - unitIn : a {@link Unit}<br>
	 * - unitOut : a {@link Unit}
	 */
	public static final String FIND_BY_PARAMETERS = "Factor.findByParameters";

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

}