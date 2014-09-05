package eu.factorx.awac.models.forms;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.knowledge.Indicator;

@Entity
@Table(name = "awaccalculator")
public class AwacCalculator extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String INTERFACE_TYPE_CODE_PROPERTY = "interfaceTypeCode";

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "code")) })
	@Column(unique = true, nullable = false)
	private InterfaceTypeCode interfaceTypeCode;

	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.REMOVE, CascadeType.DETACH})
	@JoinTable(name = "mm_calculator_indicator",
			joinColumns = @JoinColumn(name = "calculator_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "indicator_id", referencedColumnName = "id"))
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private List<Indicator> indicators;

	public AwacCalculator() {
		super();
	}

	/**
	 * @param interfaceTypeCode
	 */
	public AwacCalculator(InterfaceTypeCode interfaceTypeCode) {
		super();
		this.interfaceTypeCode = interfaceTypeCode;
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
