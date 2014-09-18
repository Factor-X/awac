package eu.factorx.awac.models.knowledge;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.code.type.ReportCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.reporting.ReportResult;

@Entity
@Table(name = "report")
public class Report extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private ReportCode code;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "retricted_scope")) })
	private IndicatorIsoScopeCode restrictedScope;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private AwacCalculator awacCalculator;

	@OneToMany(mappedBy = ReportIndicator.PROPERTY_NAME_REPORT, fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OrderBy(ReportIndicator.PROPERTY_NAME_ORDER_INDEX)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<ReportIndicator> reportIndicators;

	public Report() {
		super();
	}

	/**
	 * Creates a new <code>Report</code> for given {@link AwacCalculator}.
	 * 
	 * @param reportCode
	 *            The {@link ReportCode} identifying this report.
	 * @param awacCalculator
	 *            The {@link AwacCalculator} which this report belongs to.
	 * @param restrictedScope
	 *            An {@link IndicatorIsoScopeCode}, indicating if we have to filter the {@link BaseIndicator}s belong their {@link BaseIndicator#isoScope isoScope}. (If
	 *            <code>restrictedScope</code> is null, all base indicators linked to this report can be added to the {@link ReportResult}.)
	 */
	public Report(ReportCode reportCode, AwacCalculator awacCalculator, IndicatorIsoScopeCode restrictedScope) {
		super();
		this.code = reportCode;
		this.restrictedScope = restrictedScope;
		this.awacCalculator = awacCalculator;
	}

	public ReportCode getCode() {
		return code;
	}

	public void setCode(ReportCode code) {
		this.code = code;
	}

	public IndicatorIsoScopeCode getRestrictedScope() {
		return restrictedScope;
	}

	public void setRestrictedScope(IndicatorIsoScopeCode restrictedScope) {
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
		for (ReportIndicator reportIndicator : this.getReportIndicators()) {
			res.add(reportIndicator.getIndicator());
		}
		return res;
	}

	public List<BaseIndicator> getBaseIndicators() {
		List<BaseIndicator> res = new ArrayList<>();
		IndicatorIsoScopeCode restrictedScope = this.getRestrictedScope();
		if (restrictedScope == null) {
			for (Indicator indicator : this.getIndicators()) {
				for (BaseIndicator baseIndicator : indicator.getBaseIndicators()) {
					if (!baseIndicator.getDeleted()) {
						res.add(baseIndicator);						
					}
				}
			}
		} else {
			for (Indicator indicator : this.getIndicators()) {
				for (BaseIndicator baseIndicator : indicator.getBaseIndicators()) {
					if ((!baseIndicator.getDeleted()) && restrictedScope.equals(baseIndicator.getIsoScope())) {
						res.add(baseIndicator);						
					}
				}
			}
		}
		return res;
	}

	@Override
	public String toString() {
		return "report [id=" + id + ", code=" + code + ", restrictedScope=" + restrictedScope + "]";
	}

}