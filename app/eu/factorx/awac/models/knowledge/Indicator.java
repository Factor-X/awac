package eu.factorx.awac.models.knowledge;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.code.type.IndicatorTypeCode;
import eu.factorx.awac.models.code.type.ScopeTypeCode;

@Entity
@Table(name = "indicator")
@NamedQueries({
		@NamedQuery(name = Indicator.FIND_BY_PARAMETERS, query = "select i from Indicator i where i.type = :type and i.scopeType = :scopeType and i.activityCategory = :activityCategory and i.activitySubCategory = :activitySubCategory and (i.activityOwnership is null or i.activityOwnership = :activityOwnership) and i.deleted = false"),
		@NamedQuery(name = Indicator.FIND_ALL_INDICATOR_NAMES, query = "select distinct i.name from Indicator i"),})
public class Indicator extends AbstractEntity {

	/**
	 * @param type : an {@link IndicatorCategoryCode}
	 * @param scopeType : a {@link ScopeTypeCode}
	 * @param activityCategory : an {@link ActivityCategoryCode}
	 * @param activitySubCategory : an {@link ActivitySubCategoryCode}
	 * @param activityOwnership : a {@link Boolean}
	 */
	public static final String FIND_BY_PARAMETERS = "Indicator.findByParametersAndOwnership";
	public static final String FIND_ALL_INDICATOR_NAMES = "Indicator.findAllIndicatorNames";
	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String key;

	// not unique !!
	private String name;

	@Embedded
	@AttributeOverrides({@AttributeOverride(name = "key", column = @Column(name = "type"))})
	private IndicatorTypeCode type;

	@Embedded
	private ScopeTypeCode scopeType;

	@Embedded
	private IndicatorIsoScopeCode isoScope;

	@Embedded
	private IndicatorCategoryCode indicatorCategory;

	@Embedded
	private ActivityCategoryCode activityCategory;

	@Embedded
	private ActivitySubCategoryCode activitySubCategory;

	private Boolean activityOwnership;

	@ManyToOne
	private Unit unit;

	private Boolean deleted;

	protected Indicator() {
		super();
	}

	public Indicator(String key, String name, IndicatorTypeCode type, ScopeTypeCode scopeType, IndicatorIsoScopeCode isoScope,
	                 IndicatorCategoryCode indicatorCategory, ActivityCategoryCode activityCategory,
	                 ActivitySubCategoryCode activitySubCategory, Boolean activityOwnership, Unit unit, Boolean deleted) {
		super();
		this.key = key;
		this.name = name;
		this.type = type;
		this.scopeType = scopeType;
		this.isoScope = isoScope;
		this.indicatorCategory = indicatorCategory;
		this.activityCategory = activityCategory;
		this.activitySubCategory = activitySubCategory;
		this.activityOwnership = activityOwnership;
		this.unit = unit;
		this.deleted = deleted;
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IndicatorTypeCode getType() {
		return type;
	}

	public void setType(IndicatorTypeCode type) {
		this.type = type;
	}

	public ScopeTypeCode getScopeType() {
		return scopeType;
	}

	public void setScopeType(ScopeTypeCode scopeType) {
		this.scopeType = scopeType;
	}

	public IndicatorIsoScopeCode getIsoScope() {
		return isoScope;
	}

	public void setIsoScope(IndicatorIsoScopeCode isoScope) {
		this.isoScope = isoScope;
	}

	public IndicatorCategoryCode getIndicatorCategory() {
		return indicatorCategory;
	}

	public void setIndicatorCategory(IndicatorCategoryCode indicatorCategory) {
		this.indicatorCategory = indicatorCategory;
	}

	public ActivityCategoryCode getActivityCategory() {
		return activityCategory;
	}

	public void setActivityCategory(ActivityCategoryCode activityCategory) {
		this.activityCategory = activityCategory;
	}

	public ActivitySubCategoryCode getActivitySubCategory() {
		return activitySubCategory;
	}

	public void setActivitySubCategory(ActivitySubCategoryCode activitySubCategory) {
		this.activitySubCategory = activitySubCategory;
	}

	public Boolean getActivityOwnership() {
		return activityOwnership;
	}

	public void setActivityOwnership(Boolean activityOwnership) {
		this.activityOwnership = activityOwnership;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Indicator [key=" + key + ", name=" + name + ", type=" + type + ", scopeType=" + scopeType + ", isoScope=" + isoScope
				+ ", indicatorCategory=" + indicatorCategory + ", activityCategory=" + activityCategory + ", activitySubCategory="
				+ activitySubCategory + ", activityOwnership=" + activityOwnership + ", unit=" + unit + ", deleted=" + deleted + "]";
	}

}