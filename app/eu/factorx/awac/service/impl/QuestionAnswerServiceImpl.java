package eu.factorx.awac.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.service.QuestionAnswerSearchParameter;
import eu.factorx.awac.service.QuestionAnswerService;

@Component
public class QuestionAnswerServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionAnswer> implements QuestionAnswerService {

	@Override
	public List<QuestionAnswer> findByParameters(QuestionAnswerSearchParameter searchParameter) {
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
	}

	@Override
	public List<QuestionAnswer> findByCodes(List<QuestionCode> codes) {
		List<QuestionAnswer> resultList = JPA.em().createNamedQuery(QuestionAnswer.FIND_BY_CODES, QuestionAnswer.class).setParameter("codes", codes).getResultList();
		return resultList;
	}

}
