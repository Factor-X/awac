package eu.factorx.awac.models.forms;

import java.util.List;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.knowledge.Indicator;

@Entity
@Table(name = "awaccalculator")
public class AwacCalculator extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "code")) })
	@Column(unique = true, nullable = false)
	private InterfaceTypeCode interfaceTypeCode;

	@ManyToMany
	@JoinTable(name = "mm_calculator_indicator",
			joinColumns = @JoinColumn(name = "calculator_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "indicator_id", referencedColumnName = "id"))
	private List<Indicator> indicators;

	public AwacCalculator() {
		super();
	}

	public InterfaceTypeCode getInterfaceTypeCode() {
		return interfaceTypeCode;
	}

	public void setInterfaceTypeCode(InterfaceTypeCode interfaceTypeCode) {
		this.interfaceTypeCode = interfaceTypeCode;
	}

	public List<Indicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<Indicator> indicators) {
		this.indicators = indicators;
	}

}
