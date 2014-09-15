package eu.factorx.awac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.code.type.ReportCode;
import eu.factorx.awac.models.knowledge.Report;
import eu.factorx.awac.service.BaseIndicatorService;
import eu.factorx.awac.service.FactorService;
import eu.factorx.awac.service.QuestionSetAnswerService;
import eu.factorx.awac.service.ReportService;

@Component
public class ReportServiceImpl extends AbstractJPAPersistenceServiceImpl<Report> implements ReportService {

	@Autowired
	private QuestionSetAnswerService questionSetAnswerService;

	@Autowired
	private BaseIndicatorService baseIndicatorService;

	@Autowired
	private FactorService factorService;

	public Report getReport(ReportCode code) {
		return null;
	}

}
