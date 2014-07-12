package eu.factorx.awac.models.knowledge;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.IndicatorCategoryCode;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.code.type.IndicatorTypeCode;
import eu.factorx.awac.models.code.type.ScopeTypeCode;

@Entity
@Table(name = "indicator")
public class Indicator extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String identifier;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "type")) })
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

	public Indicator(String identifier, IndicatorTypeCode type, ScopeTypeCode scopeType,
			IndicatorIsoScopeCode isoScope, IndicatorCategoryCode indicatorCategory,
			ActivityCategoryCode activityCategory, ActivitySubCategoryCode activitySubCategory,
			Boolean activityOwnership, Unit unit, Boolean deleted) {
		super();
		this.identifier = identifier;
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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("identifier", identifier)
				.append("type", type).append("scopeType", scopeType).append("isoScope", isoScope)
				.append("indicatorCategory", indicatorCategory).append("activityCategory", activityCategory)
				.append("activitySubCategoryCode", activitySubCategory)
				.append("activityOwnership", activityOwnership).append("unit", unit).append("deleted", deleted)
				.toString();
	}
}