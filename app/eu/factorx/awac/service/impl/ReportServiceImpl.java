package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.models.reporting.Report;
import eu.factorx.awac.service.QuestionAnswerService;
import eu.factorx.awac.service.ReportService;

@Component
public class ReportServiceImpl implements ReportService {

	@Autowired
	private QuestionAnswerService questionAnswerService;
	
	@Override
	public Report getReport(Scope scope, Period period) {
		List<QuestionAnswer> questionAnswers = questionAnswerService.findByScopeAndPeriod(scope, period);
		
		// TODO Implement this
		
		return null;
	}

}
