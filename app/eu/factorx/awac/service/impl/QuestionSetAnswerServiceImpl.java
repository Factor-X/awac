package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.QuestionSetAnswerService;

@Component
public class QuestionSetAnswerServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionSetAnswer> implements
		QuestionSetAnswerService {

	@Override
	public List<QuestionSetAnswer> findByScopeAndPeriod(Scope scope, Period period) {
		return JPA.em().createNamedQuery(QuestionSetAnswer.FIND_BY_SCOPE_AND_PERIOD, QuestionSetAnswer.class)
				.setParameter("scope", scope).setParameter("period", period).getResultList();
	}

}
