package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.service.QuestionSetService;

@Component
public class QuestionSetServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionSet> implements QuestionSetService {

	@Override
	public List<QuestionSet> findByForm(Form form) {
		List<QuestionSet> resultList = JPA.em().createNamedQuery(QuestionSet.FIND_ONLY_PARENTS, QuestionSet.class)
				.setParameter("questionSets", form.getQuestionSets()).getResultList();
		return resultList;
	}

}
