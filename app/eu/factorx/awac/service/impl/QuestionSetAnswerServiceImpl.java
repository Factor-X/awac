package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.QuestionSetAnswerSearchParameter;
import eu.factorx.awac.service.QuestionSetAnswerService;

@Component
public class QuestionSetAnswerServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionSetAnswer> implements QuestionSetAnswerService {

	@Override
	public List<QuestionSetAnswer> findByParameters(QuestionSetAnswerSearchParameter searchParameter) {
//		Session session = JPA.em().unwrap(Session.class);
//		Criteria criteria = session.createCriteria(QuestionSetAnswer.class);
//		if (searchParameter.getForm() != null) {
//			criteria.add(Restrictions.eq("form", searchParameter.getForm()));
//		}
//		if (searchParameter.getScope() != null) {
//			criteria.add(Restrictions.eq("scope", searchParameter.getScope()));
//		}
//		... TODO
		return null;
	}

	@Override
	public List<QuestionSetAnswer> findByScope(Scope scope) {
		List<QuestionSetAnswer> resultList = JPA.em().createNamedQuery(QuestionSetAnswer.FIND_BY_SCOPE, QuestionSetAnswer.class)
				.setParameter("scope", scope).getResultList();
		return resultList;
	}

	@Override
	public List<QuestionSetAnswer> findByScopeAndPeriod(Scope scope, Period period) {
		List<QuestionSetAnswer> resultList = JPA.em().createNamedQuery(QuestionSetAnswer.FIND_BY_SCOPE_AND_PERIOD, QuestionSetAnswer.class)
				.setParameter("scope", scope).setParameter("period", period).getResultList();
		return resultList;
	}

	@Override
	public List<QuestionSetAnswer> findByScopeAndPeriodAndForm(Scope scope, Period period, Form form) {
		List<QuestionSetAnswer> resultList = JPA.em()
				.createNamedQuery(QuestionSetAnswer.FIND_BY_SCOPE_AND_PERIOD_AND_QUESTION_SETS, QuestionSetAnswer.class)
				.setParameter("scope", scope).setParameter("period", period).setParameter("questionSets", form.getQuestionSets())
				.getResultList();
		return resultList;
	}

	@Override
	public List<QuestionSetAnswer> findByScopeAndForm(Scope scope, Form form) {
		List<QuestionSetAnswer> resultList = JPA.em()
				.createNamedQuery(QuestionSetAnswer.FIND_BY_SCOPE_AND_QUESTION_SETS, QuestionSetAnswer.class).setParameter("scope", scope)
				.setParameter("questionSets", form.getQuestionSets()).getResultList();
		return resultList;
	}

	@Override
	public List<QuestionSetAnswer> findByCodes(List<QuestionCode> codes) {
		List<QuestionSetAnswer> resultList = JPA.em().createNamedQuery(QuestionSetAnswer.FIND_BY_CODES, QuestionSetAnswer.class)
				.setParameter("codes", codes).getResultList();
		return resultList;
	}

	@Override
	public void deleteAllFormAnswers(Scope scope, Period period, Form form) {
		List<QuestionSetAnswer> questionSetAnswers = findByScopeAndPeriodAndForm(scope, period, form);
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
			remove(questionSetAnswer);
		}
	}

	@Override
	public List<Period> getAllQuestionSetAnswersPeriodsByScope(Long scopeId) {
		List<Period> resultList = JPA.em().createNamedQuery(QuestionSetAnswer.FIND_DISTINCT_PERIODS, Period.class)
				.setParameter("scopeId", scopeId).getResultList();
		return resultList;
	}

}
