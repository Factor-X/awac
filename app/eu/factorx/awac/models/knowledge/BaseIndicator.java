package eu.factorx.awac.models.knowledge;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.*;

@Entity
@Table(name = "baseindicator")
@NamedQueries({
		@NamedQuery(name = BaseIndicator.REMOVE_ALL, query = "delete from BaseIndicator i where i.id is not null"),
})
public class BaseIndicator extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String REMOVE_ALL = "BaseIndicator.removeAll";

	@Embedded
	@Column(unique = true)
	private BaseIndicatorCode code;

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

	protected BaseIndicator() {
		super();
	}

	public BaseIndicator(BaseIndicatorCode code, IndicatorTypeCode type, ScopeTypeCode scopeType, IndicatorIsoScopeCode isoScope,
			IndicatorCategoryCode indicatorCategory, ActivityCategoryCode activityCategory,
			ActivitySubCategoryCode activitySubCategory, Boolean activityOwnership, Unit unit, Boolean deleted) {
		super();
		this.code = code;
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

	public BaseIndicatorCode getCode() {
		return code;
	}

	public void setCode(BaseIndicatorCode code) {
		this.code = code;
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
		return "BaseIndicator [id = " + id + ", code = " + code + "]";
	}

}