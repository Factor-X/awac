package eu.factorx.awac.service.impl.reporting;

import java.util.ArrayList;
import java.util.List;

public class MergedReportResultCollectionAggregation {
	private List<MergedReportResultAggregation> mergedReportResultAggregations;

	public MergedReportResultCollectionAggregation() {
		mergedReportResultAggregations = new ArrayList<>();
	}

	public List<MergedReportResultAggregation> getMergedReportResultAggregations() {
		return mergedReportResultAggregations;
	}
}
