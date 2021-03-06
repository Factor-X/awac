package eu.factorx.awac.service.impl;

import java.util.List;

import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.knowledge.ReportIndicator;
import eu.factorx.awac.service.ReportIndicatorService;

@Component
public class ReportIndicatorServiceImpl extends AbstractJPAPersistenceServiceImpl<ReportIndicator> implements ReportIndicatorService {
	@Override
	public List<ReportIndicator> findAll() {
		return (List<ReportIndicator>) JPA.em().createQuery("select e from ReportIndicator e order by e.orderIndex asc", ReportIndicator.class).getResultList();
	}

	@Override
	public ReportIndicator findByReportCodeAndIndicatorCode(String reportCode, String indicator) {
		return JPA.em().createQuery("select e from ReportIndicator e where e.report.code.key = :rc and e.indicator.code.key = :ic", ReportIndicator.class)
			.setParameter("rc", reportCode)
			.setParameter("ic", indicator)
			.setHint(QueryHints.CACHEABLE, Boolean.TRUE)
			.getSingleResult();
	}
}
