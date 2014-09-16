package eu.factorx.awac.models.knowledge;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.IndicatorCode;
import eu.factorx.awac.models.code.type.ScopeTypeCode;
import eu.factorx.awac.models.forms.AwacCalculator;

@Entity
@Table(name = "report")
public class Report extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private IndicatorCode code;

	@Embedded
	private ScopeTypeCode restrictedScope;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private AwacCalculator awacCalculator;

	@OneToMany(mappedBy = "report", fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	// -> hibernate-specific annotation to fix DDL generation problem
	private List<ReportIndicator> reportIndicators;

	public Report() {
		super();
	}

	/**
	 * @param code
	 */
	public Report(IndicatorCode code, ScopeTypeCode restrictedScope) {
		super();
		this.code = code;
		this.restrictedScope = restrictedScope;
	}

	public IndicatorCode getCode() {
		return code;
	}

	public void setCode(IndicatorCode code) {
		this.code = code;
	}

	public ScopeTypeCode getRestrictedScope() {
		return restrictedScope;
	}

	public void setRestrictedScope(ScopeTypeCode restrictedScope) {
		this.restrictedScope = restrictedScope;
	}

	public AwacCalculator getAwacCalculator() {
		return awacCalculator;
	}

	public void setAwacCalculator(AwacCalculator awacCalculator) {
		this.awacCalculator = awacCalculator;
	}

	public List<ReportIndicator> getReportIndicators() {
		return reportIndicators;
	}

	public void setReportIndicators(List<ReportIndicator> reportIndicators) {
		this.reportIndicators = reportIndicators;
	}

	public List<Indicator> getIndicators() {
		List<Indicator> res = new ArrayList<>();
		for (ReportIndicator reportIndicator : getReportIndicators()) {
			res.add(reportIndicator.getIndicator());
		}
		return res;
	}

	@Override
	public String toString() {
		return "Indicator [id=" + id + ", code=" + code + ", restrictedScope=" + restrictedScope + "]";
	}

}