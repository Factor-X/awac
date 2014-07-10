package eu.factorx.awac.models.reporting;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.IsoIndicatorScope;
import eu.factorx.awac.models.knowledge.ActivityCategory;
import eu.factorx.awac.models.knowledge.IndicatorCategory;
import eu.factorx.awac.models.knowledge.Unit;

@Entity
@Table(name = "indicator")
public class Indicator extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String identifier;

	@ManyToOne
	private Scope scope;

	@Embedded
	private IsoIndicatorScope isoScope;

	@ManyToOne
	private IndicatorCategory indicatorCategory;

	@ManyToOne
	private Unit unit;

	@ManyToOne
	private ActivityCategory activityCategory;

	private Boolean activityOwnership;

	private Boolean deleted;

	protected Indicator() {
		super();
	}

	public Indicator(String identifier, Scope scope, IsoIndicatorScope isoScope, IndicatorCategory indicatorCategory,
			Unit unit, ActivityCategory activityCategory, boolean activityOwnership, boolean deleted) {
		super();
		this.identifier = identifier;
		this.scope = scope;
		this.isoScope = isoScope;
		this.indicatorCategory = indicatorCategory;
		this.unit = unit;
		this.activityCategory = activityCategory;
		this.activityOwnership = activityOwnership;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String name) {
		this.identifier = name;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public IsoIndicatorScope getIsoScope() {
		return isoScope;
	}

	public void setIsoScope(IsoIndicatorScope isoScope) {
		this.isoScope = isoScope;
	}

	public IndicatorCategory getIndicatorCategory() {
		return indicatorCategory;
	}

	public void setIndicatorCategory(IndicatorCategory indicatorCategory) {
		this.indicatorCategory = indicatorCategory;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public ActivityCategory getActivityCategory() {
		return activityCategory;
	}

	public void setActivityCategory(ActivityCategory activityCategory) {
		this.activityCategory = activityCategory;
	}

	public boolean isActivityOwnership() {
		return activityOwnership;
	}

	public void setActivityOwnership(boolean activityOwnership) {
		this.activityOwnership = activityOwnership;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}