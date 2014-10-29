package eu.factorx.awac.service.impl.reporting;

import java.util.ArrayList;
import java.util.List;

import eu.factorx.awac.models.code.type.IndicatorIsoScopeCode;
import eu.factorx.awac.models.knowledge.Period;

public class MergedReportResultAggregation {

	List<MergedReportResultIndicatorAggregation> mergedReportResultIndicatorAggregationList;
	private String                reportCode;
	private IndicatorIsoScopeCode reportRestrictedScope;
	private Period                leftPeriod;
	private Period                rightPeriod;

	public MergedReportResultAggregation() {
		mergedReportResultIndicatorAggregationList = new ArrayList<>();
	}

	public List<MergedReportResultIndicatorAggregation> getMergedReportResultIndicatorAggregationList() {
		return mergedReportResultIndicatorAggregationList;
	}

	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	public IndicatorIsoScopeCode getReportRestrictedScope() {
		return reportRestrictedScope;
	}

	public void setReportRestrictedScope(IndicatorIsoScopeCode reportRestrictedScope) {
		this.reportRestrictedScope = reportRestrictedScope;
	}

	public Period getLeftPeriod() {
		return leftPeriod;
	}

	public void setLeftPeriod(Period leftPeriod) {
		this.leftPeriod = leftPeriod;
	}

	public Period getRightPeriod() {
		return rightPeriod;
	}

	public void setRightPeriod(Period rightPeriod) {
		this.rightPeriod = rightPeriod;
	}
}
