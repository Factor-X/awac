package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.service.QuestionAnswerSearchParameter;
import eu.factorx.awac.service.QuestionAnswerService;
import eu.factorx.awac.util.JPAUtils;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuestionAnswerServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionAnswer> implements QuestionAnswerService {

	@Override
	public List<QuestionAnswer> findByParameters(QuestionAnswerSearchParameter searchParameter) {

		List<String> parts = new ArrayList<>();
		Map<String, Object> parameters = new HashMap<>();

		parts.add("select e from QuestionAnswer e where 1 = 1");
		if (searchParameter.getForm() != null) {
			parts.add("and e.questionSet in :questionSets");
			parameters.put("questionSets", searchParameter.getForm().getAllQuestionSets());
		}
		if (searchParameter.getScope() != null) {
			parts.add("and e.scope = :scope");
			parameters.put("scope", searchParameter.getScope());
		}
		if (searchParameter.getPeriod() != null) {
			parts.add("and e.period = :period");
			parameters.put("period", searchParameter.getPeriod());
		}
		parts.add("order by e.repetitionIndex");

		TypedQuery<QuestionAnswer> query = JPAUtils.build(parts, parameters, QuestionAnswer.class);
		return query.getResultList();


/*
		Session session = JPA.em().unwrap(Session.class);
		Criteria criteria = session.createCriteria(QuestionAnswer.class, "qa");

		criteria.createAlias("qa.questionSetAnswer", "qsa");
		if (searchParameter.getForm() != null) {
			criteria.add(Restrictions.in("qsa.questionSet", searchParameter.getForm().getAllQuestionSets()));
		}
		if (searchParameter.getScope() != null) {
			criteria.add(Restrictions.eq("qsa.scope", searchParameter.getScope()));
		}
		if (searchParameter.getPeriod() != null) {
			criteria.add(Restrictions.eq("qsa.period", searchParameter.getPeriod()));
		}
		criteria.addOrder(Order.asc("qsa.repetitionIndex"));

		criteria.setCacheable(true);
		@SuppressWarnings("unchecked")
		List<QuestionAnswer> result = criteria.list();
		return result;
		*/
	}

	@Override
	public List<QuestionAnswer> findByCodes(List<QuestionCode> codes) {
		return JPA.em().createNamedQuery(QuestionAnswer.FIND_BY_CODES, QuestionAnswer.class).setParameter("codes", codes).getResultList();
	}

}
