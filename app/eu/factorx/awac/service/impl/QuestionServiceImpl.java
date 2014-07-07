package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.service.QuestionService;

@Component
public class QuestionServiceImpl extends AbstractJPAPersistenceServiceImpl<Question> implements QuestionService {

	@Override
	public List<Question> findByCodes(List<QuestionCode> codes) {
		@SuppressWarnings("unchecked")
		List<Question> resultList = JPA.em().createNamedQuery(Question.FIND_BY_CODES).setParameter("codes", codes)
				.getResultList();
		return resultList;
	}

}
