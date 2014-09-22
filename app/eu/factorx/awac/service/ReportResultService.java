package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.ReportResult;

public interface ReportResultService {

	eu.factorx.awac.service.impl.reporting.ReportResultCollection getReportResults(AwacCalculator awacCalculator, List<Scope> scopes, Period period);

}
