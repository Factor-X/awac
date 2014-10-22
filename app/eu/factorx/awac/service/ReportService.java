package eu.factorx.awac.service;

import eu.factorx.awac.models.knowledge.Report;

public interface ReportService extends PersistenceService<Report> {

	Report findByCode(String reportCode);
}
