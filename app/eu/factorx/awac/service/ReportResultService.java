package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.BaseActivityResult;
import eu.factorx.awac.models.reporting.ReportResult;
import eu.factorx.awac.service.impl.reporting.*;

import java.util.List;
import java.util.Map;

public interface ReportResultService {

    ReportResultCollection getReportResults(AwacCalculator awacCalculator, List<Scope> scopes, Period period);

    List<BaseActivityResult> getBaseActivityResults(AwacCalculator awacCalculator, List<Scope> scopes, Period period, List<ReportLogEntry> logEntries);

    ReportResultCollectionAggregation aggregate(ReportResultCollection reportResultCollection);

    ReportResultAggregation aggregate(ReportResult reportResult);

    MergedReportResultCollectionAggregation mergeAsComparision(ReportResultCollectionAggregation a1, ReportResultCollectionAggregation a2);

    Map<String, List<Double>> getScopeValuesByIndicator(ReportResult reportResult);

}
