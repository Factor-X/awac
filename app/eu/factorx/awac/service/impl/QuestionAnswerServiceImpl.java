package eu.factorx.awac.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.service.QuestionAnswerSearchParameter;
import eu.factorx.awac.service.QuestionAnswerService;
import eu.factorx.awac.util.JPAUtils;

@Component
public class QuestionAnswerServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionAnswer> implements QuestionAnswerService {

	@Override
	public List<QuestionAnswer> findByParameters(QuestionAnswerSearchParameter searchParameter) {

		List<String> parts = new ArrayList<>();
		Map<String, Object> parameters = new HashMap<>();

		parts.add("select e from QuestionAnswer e where 1 = 1");
		if (searchParameter.getForm() != null) {
			parts.add("and e.questionSetAnswer.questionSet in :questionSets");
			parameters.put("questionSets", searchParameter.getForm().getAllQuestionSets());
		}
		if (searchParameter.getScope() != null) {
			parts.add("and e.questionSetAnswer.scope = :scope");
			parameters.put("scope", searchParameter.getScope());
		}
		if (searchParameter.getPeriod() != null) {
			parts.add("and e.questionSetAnswer.period = :period");
			parameters.put("period", searchParameter.getPeriod());
		}
		parts.add("order by e.questionSetAnswer.repetitionIndex");

		TypedQuery<QuestionAnswer> query = JPAUtils.build(parts, parameters, QuestionAnswer.class);
		return query.getResultList();


	}

	@Override
	public List<QuestionAnswer> findByCodes(List<QuestionCode> codes) {
		return JPA.em().createNamedQuery(QuestionAnswer.FIND_BY_CODES, QuestionAnswer.class).setParameter("codes", codes).getResultList();
	}

}
