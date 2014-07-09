package eu.factorx.awac.controllers;

import java.util.List;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.QuestionAnswerService;

@org.springframework.stereotype.Controller
public class ResultController extends Controller {

	private QuestionAnswerService questionAnswerService;
	
	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result calculateIndicator(Scope scope, Period period) {
		List<QuestionAnswer> questionAnswers = questionAnswerService.findByScopeAndPeriod(scope, period);
		
		// TODO To Implement....
		
		return ok();
	}

}
