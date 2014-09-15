package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.ReportResult;

import java.util.List;

public interface ReportResultService {

	List<ReportResult> getReportResults(AwacCalculator awacCalculator, List<Scope> scopes, Period period);

}
