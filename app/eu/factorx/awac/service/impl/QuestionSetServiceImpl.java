package eu.factorx.awac.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.service.QuestionSetService;

@Component
public class QuestionSetServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionSet> implements QuestionSetService {

	@Override
	public List<QuestionSet> findQuestionSetsWithoutParentByCodes(Collection<String> codes) {
		List<QuestionSet> resultList = JPA.em().createNamedQuery(QuestionSet.FIND_WITHOUT_PARENT_BY_CODES, QuestionSet.class)
				.setParameter("codes", codes).getResultList();
		return resultList;
	}

}
