package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.code.type.QuestionCode;
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

	@Override
	public QuestionSet findByCode(QuestionCode code) {
		List<QuestionSet> resultList = JPA.em().createNamedQuery(QuestionSet.FIND_BY_CODE, QuestionSet.class)
				.setParameter("code", code).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one question set with code = '" + code + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

}
