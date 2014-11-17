package eu.factorx.awac.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.service.QuestionService;

@Component
public class QuestionServiceImpl extends AbstractJPAPersistenceServiceImpl<Question> implements QuestionService {

	@Override
	public List<Question> findByCodes(List<QuestionCode> codes) {
		List<Question> resultList = JPA.em().createNamedQuery(Question.FIND_BY_CODES, Question.class)
				.setParameter("codes", codes).getResultList();
		return resultList;
	}

	@Override
	public Question findByCode(QuestionCode code) {
		List<Question> resultList = JPA.em().createNamedQuery(Question.FIND_BY_CODE, Question.class)
				.setParameter("code", code).getResultList();
		if (resultList.size() > 1) {
			String errorMsg = "More than one question with code = '" + code.getKey() + "'";
			Logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	@Override
	public List<Question> findByForm(Form form) {
		Set<Long> questionSetsIds = getQuestionSetsIds(form.getQuestionSets());
		List<Question> resultList = JPA.em().createNamedQuery(Question.FIND_BY_QUESTION_SETS_IDS, Question.class)
				.setParameter("questionSetsIds", questionSetsIds).getResultList();
		return resultList;
	}

	private Set<Long> getQuestionSetsIds(List<QuestionSet> questionSets) {
		Set<Long> questionSetsIds = new LinkedHashSet<>();
		for (QuestionSet questionSet : questionSets) {
			questionSetsIds.add(questionSet.getId());
			questionSetsIds.addAll(getQuestionSetsIds(questionSet.getChildren()));
		}
		return questionSetsIds;
	}

}
