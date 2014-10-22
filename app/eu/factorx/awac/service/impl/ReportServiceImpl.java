package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.knowledge.Report;
import eu.factorx.awac.service.ReportService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

@Component
public class ReportServiceImpl extends AbstractJPAPersistenceServiceImpl<Report> implements ReportService {

	@Override
	public Report findByCode(String reportCode) {
		return JPA.em().createQuery("select e from Report e where e.code = :code", Report.class).setParameter("code", reportCode).getSingleResult();
	}
}
