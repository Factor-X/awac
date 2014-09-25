package eu.factorx.awac.service.impl.reporting;

import java.util.ArrayList;
import java.util.List;

public class MergedReportResultAggregation {

	List<MergedReportResultIndicatorAggregation> mergedReportResultIndicatorAggregationList;
	private String reportCode;

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
}
