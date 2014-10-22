package eu.factorx.awac.service;

import eu.factorx.awac.models.knowledge.Report;
import eu.factorx.awac.models.knowledge.ReportIndicator;

public interface ReportIndicatorService extends PersistenceService<ReportIndicator>{

	ReportIndicator findByReportCodeAndIndicaotrCode(String reportCode, String indicator);
}
