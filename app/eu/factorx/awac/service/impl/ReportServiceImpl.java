package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.ReportCode;
import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.knowledge.Report;
import eu.factorx.awac.service.ReportService;

@Component
public class ReportServiceImpl extends AbstractJPAPersistenceServiceImpl<Report> implements ReportService {

	@Override
	public Report findByCode(ReportCode reportCode) {
		return JPA.em().createQuery("select e from Report e where e.code = :code", Report.class).setParameter("code", reportCode).getSingleResult();
	}
}
