package eu.factorx.awac.service.impl.reporting;

import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;

import java.util.ArrayList;
import java.util.List;

public class ReportResultAggregation {

	private List<ReportResultIndicatorAggregation> reportResultIndicatorAggregationList;
	private IndicatorIsoScopeCode                  reportRestrictedScope;

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
}

