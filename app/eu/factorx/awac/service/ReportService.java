package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.Report;

public interface ReportService {

	Report getReport(Scope scope, Period period);

}
