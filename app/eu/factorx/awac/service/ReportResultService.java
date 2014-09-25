package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.impl.reporting.MergedReportResultCollectionAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultAggregation;
import eu.factorx.awac.service.impl.reporting.ReportResultCollection;
import eu.factorx.awac.service.impl.reporting.ReportResultCollectionAggregation;

import java.util.List;

public interface ReportResultService {

	ReportResultCollection getReportResults(AwacCalculator awacCalculator, List<Scope> scopes, Period period);

	ReportResultCollectionAggregation aggregate(ReportResultCollection reportResultCollection);

	ReportResultAggregation aggregate(ReportResult reportResult);

	MergedReportResultCollectionAggregation mergeAsComparision(ReportResultCollectionAggregation a1, ReportResultCollectionAggregation a2);
}
