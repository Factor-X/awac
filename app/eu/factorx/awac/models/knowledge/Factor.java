package eu.factorx.awac.models.knowledge;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "factor")
@NamedQueries({
	@NamedQuery(
		name = Factor.FIND_BY_PARAMETERS,
		query = "select f from Factor f where f.indicatorCategory = :indicatorCategory and f.activitySource = :activitySource and f.activityType = :activityType and f.unitIn = :unitIn and f.unitOut = :unitOut and f.values is not empty"
	),
	@NamedQuery(name = Factor.REMOVE_ALL, query = "delete from Factor f where f.id is not null")
})
@Cacheable(true)
public class Factor extends AuditedAbstractEntity {

	/**
	 * @param indicatorCategory : an {@link IndicatorCategoryCode}
	 * @param activitySource : an {@link ActivitySourceCode}
	 * @param activityType : an {@link ActivityTypeCode}
	 * @param unitIn : a {@link Unit}
	 * @param unitOut : a {@link Unit}
	 */
	public static final String FIND_BY_PARAMETERS = "Factor.findByParameters";
	public static final String REMOVE_ALL = "Factor.removeAll";
	private static final long serialVersionUID = 1L;
	@Column(unique = true)
	private String key;

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

	@OneToMany(mappedBy = "factor", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<FactorValue> values = new ArrayList<>();

	protected Factor() {
		super();
	}

	public Factor(String key, IndicatorCategoryCode indicatorCategory, ActivityTypeCode activityType,
				  ActivitySourceCode activitySource, Unit unitIn, Unit unitOut, String institution) {
		super();
		this.key = key;
		this.indicatorCategory = indicatorCategory;
		this.activityType = activityType;
		this.activitySource = activitySource;
		this.unitIn = unitIn;
		this.unitOut = unitOut;
		this.institution = institution;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
/*
	public Double getCurrentValue() {
		FactorValue factorValue = this.values.get(0); // TODO Find this value from the current date
		return factorValue.getValue();
	}
*/

	@Override
	public String toString() {
		return "Factor [key=" + key + ", indicatorCategory=" + indicatorCategory + ", activityType=" + activityType + ", activitySource="
			+ activitySource + ", unitIn=" + unitIn + ", unitOut=" + unitOut + ", institution=" + institution + ", values=" + values
			+ "]";
	}

}
