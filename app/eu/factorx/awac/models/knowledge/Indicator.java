package eu.factorx.awac.models.knowledge;

import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.IndicatorCode;

@Entity
@Table(name = "indicator")
@NamedQueries({
	@NamedQuery(name = Indicator.REMOVE_ALL, query = "delete from Indicator i where i.id is not null")
})
public class Indicator extends AuditedAbstractEntity {

	public static final String REMOVE_ALL = "Indicator.removeAll";

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private IndicatorCode code;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
	@JoinTable(name = "mm_indicator_baseindicator",
		joinColumns = @JoinColumn(name = "indicator_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "baseindicator_id", referencedColumnName = "id"))
	private List<BaseIndicator> baseIndicators;

	@OneToMany(mappedBy = "indicator", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	// -> hibernate-specific annotation to fix DDL generation problem
	private List<ReportIndicator> reportIndicators;


	public Indicator() {
		super();
	}

	/**
	 * @param code
	 */
	public Indicator(IndicatorCode code) {
		super();
		this.code = code;
	}

	public IndicatorCode getCode() {
		return code;
	}

	public void setCode(IndicatorCode code) {
		this.code = code;
	}

	public List<BaseIndicator> getBaseIndicators() {
		return baseIndicators;
	}

	public void setBaseIndicators(List<BaseIndicator> baseIndicators) {
		this.baseIndicators = baseIndicators;
	}

	public List<ReportIndicator> getReportIndicators() {
		return reportIndicators;
	}

	public void setReportIndicators(List<ReportIndicator> reportIndicators) {
		this.reportIndicators = reportIndicators;
	}

	@Override
	public String toString() {
		return "Indicator [id=" + id + ", code=" + code + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Indicator)) {
			return false;
		}
		Indicator rhs = (Indicator) obj;
		return new EqualsBuilder().append(this.code, rhs.code).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(47, 59).append(this.code).toHashCode();
	}


}