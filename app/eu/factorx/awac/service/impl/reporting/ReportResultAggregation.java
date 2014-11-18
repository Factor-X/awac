package eu.factorx.awac.service.impl.reporting;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.knowledge.Period;

public class ReportResultAggregation {

	private List<ReportResultIndicatorAggregation> reportResultIndicatorAggregationList;
	private IndicatorIsoScopeCode                  reportRestrictedScope;
	private String                                 reportCode;
	private Period                                 period;

	public ReportResultAggregation() {
		reportResultIndicatorAggregationList = new ArrayList<>();
	}

	public List<ReportResultIndicatorAggregation> getReportResultIndicatorAggregationList() {
		return reportResultIndicatorAggregationList;
	}

	public IndicatorIsoScopeCode getReportRestrictedScope() {
		return reportRestrictedScope;
	}

	public void setReportRestrictedScope(IndicatorIsoScopeCode reportRestrictedScope) {
		this.reportRestrictedScope = reportRestrictedScope;
	}

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}
}

