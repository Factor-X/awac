package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.QuestionAnswerService;

@Component
public class QuestionAnswerServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionAnswer> implements
		QuestionAnswerService {

	@Override
	public List<QuestionAnswer> findByScopeAndPeriod(Scope scope, Period period) {
		@SuppressWarnings("unchecked")
		List<QuestionAnswer> resultList = JPA.em().createNamedQuery(QuestionAnswer.FIND_BY_SCOPE_AND_PERIOD)
				.setParameter("scope", scope).setParameter("period", period).getResultList();
		return resultList;
	}

	@Override
	public List<QuestionAnswer> findByCodes(List<QuestionCode> codes) {
		@SuppressWarnings("unchecked")
		List<QuestionAnswer> resultList = JPA.em().createNamedQuery(QuestionAnswer.FIND_BY_CODES)
				.setParameter("codes", codes).getResultList();
		return resultList;
	}

}
