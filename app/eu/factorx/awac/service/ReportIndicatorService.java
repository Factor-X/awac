package eu.factorx.awac.service;

import eu.factorx.awac.models.knowledge.ReportIndicator;

public interface ReportIndicatorService extends PersistenceService<ReportIndicator>{

	ReportIndicator findByReportCodeAndIndicatorCode(String reportCode, String indicator);
}
