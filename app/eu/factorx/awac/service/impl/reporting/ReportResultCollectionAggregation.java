package eu.factorx.awac.service.impl.reporting;

import java.util.ArrayList;
import java.util.List;

public class ReportResultCollectionAggregation {

	private List<ReportResultAggregation> reportResultAggregations;

	public ReportResultCollectionAggregation() {
		reportResultAggregations = new ArrayList<>();
	}

	public List<ReportResultAggregation> getReportResultAggregations() {
		return reportResultAggregations;
	}
}

