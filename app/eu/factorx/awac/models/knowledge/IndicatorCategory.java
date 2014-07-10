package eu.factorx.awac.models.knowledge;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import eu.factorx.awac.models.AbstractEntity;

@Entity
public class IndicatorCategory extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	// TODO i18n?
	private String identifier;

	@ManyToOne(optional = true)
	private IndicatorCategory parent;

	protected IndicatorCategory() {
		super();
	}

	public IndicatorCategory(String identifier) {
		super();
		this.identifier = identifier;
	}

	public IndicatorCategory(String identifier, IndicatorCategory parent) {
		super();
		this.identifier = identifier;
		this.parent = parent;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public IndicatorCategory getParent() {
		return parent;
	}

	public void setParent(IndicatorCategory parent) {
		this.parent = parent;
	}

}
