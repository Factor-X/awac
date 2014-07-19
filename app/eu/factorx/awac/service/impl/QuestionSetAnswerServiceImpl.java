package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.QuestionSetAnswerService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

import java.util.List;

@Component
public class QuestionSetAnswerServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionSetAnswer> implements
		QuestionSetAnswerService {

	@Override
	public List<QuestionSetAnswer> findByScopeAndPeriod(Scope scope, Period period) {
		List<QuestionSetAnswer> resultList = JPA.em()
				.createNamedQuery(QuestionSetAnswer.FIND_BY_SCOPE_AND_PERIOD, QuestionSetAnswer.class)
				.setParameter("scope", scope).setParameter("period", period).getResultList();
		return resultList;
	}

	@Override
	public List<QuestionSetAnswer> findByScopeAndPeriodAndForm(Scope scope, Period period, Form form) {
		List<QuestionSetAnswer> resultList = JPA
				.em()
				.createNamedQuery(QuestionSetAnswer.FIND_BY_SCOPE_AND_PERIOD_AND_QUESTION_SETS, QuestionSetAnswer.class)
				.setParameter("scope", scope).setParameter("period", period)
				.setParameter("questionSets", form.getQuestionSets()).getResultList();
		return resultList;
	}

	@Override
	public List<QuestionSetAnswer> findByCodes(List<QuestionCode> codes) {
		List<QuestionSetAnswer> resultList = JPA.em()
				.createNamedQuery(QuestionSetAnswer.FIND_BY_CODES, QuestionSetAnswer.class)
				.setParameter("codes", codes).getResultList();
		return resultList;
	}

}
