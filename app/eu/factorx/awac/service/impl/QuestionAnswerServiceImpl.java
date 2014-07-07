package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.answer.AnswerValue;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.QuestionAnswerService;

@Component
public class QuestionAnswerServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionAnswer> implements
		QuestionAnswerService {

	@Override
	public <T extends AnswerValue> QuestionAnswer<T> findByScopeAndPeriod(Question<T> question, Scope scope,
			Period period) {
		@SuppressWarnings("unchecked")
		List<QuestionAnswer<T>> resultList = JPA.em().createNamedQuery(QuestionAnswer.FIND_BY_PARAMETERS)
				.setParameter("question", question).setParameter("scope", scope).setParameter("period", period)
				.getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one answer to the question (id = " + question.getId()
					+ ") for given scope (id = " + scope.getId() + ") and period (id = " + period.getId() + ")!";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		return resultList.get(0);
	}

}
