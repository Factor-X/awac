package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.QuestionSetAnswerService;

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
	public List<QuestionSetAnswer> findByScopeAndPeriodAndQuestionSet(Scope scope, Period period,
			QuestionCode questionSetCode) {
		List<QuestionSetAnswer> resultList = JPA.em()
				.createNamedQuery(QuestionSetAnswer.FIND_BY_SCOPE_AND_PERIOD_AND_QUESTION_SET, QuestionSetAnswer.class)
				.setParameter("scope", scope).setParameter("period", period)
				.setParameter("questionSetCode", questionSetCode).getResultList();
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
