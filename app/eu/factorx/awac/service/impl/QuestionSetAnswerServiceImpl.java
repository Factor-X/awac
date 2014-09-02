package eu.factorx.awac.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.QuestionSetAnswerSearchParameter;
import eu.factorx.awac.service.QuestionSetAnswerService;

@Component
public class QuestionSetAnswerServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionSetAnswer> implements QuestionSetAnswerService {

	@Override
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<QuestionSetAnswer> findByParameters(QuestionSetAnswerSearchParameter searchParameter) {
		Session session = JPA.em().unwrap(Session.class);
		Criteria criteria = session.createCriteria(QuestionSetAnswer.class);

		if (searchParameter.getForm() != null) {
			Form form = searchParameter.getForm();
			if (searchParameter.getWithChildren()) {
				criteria.add(Restrictions.in("questionSet", form.getAllQuestionSets()));
			} else {
				criteria.add(Restrictions.in("questionSet", form.getQuestionSets()));
			}
		}

		if (searchParameter.getScope() != null) {
			criteria.add(Restrictions.eq("scope", searchParameter.getScope()));
		}

		if (searchParameter.getPeriod() != null) {
			criteria.add(Restrictions.eq("period", searchParameter.getPeriod()));
		}

		if (!searchParameter.getWithChildren()) {
			criteria.add(Restrictions.isNull("parent"));
		}
		criteria.addOrder(Order.asc("repetitionIndex"));

		criteria.setCacheable(true);
		@SuppressWarnings("unchecked")
		List<QuestionSetAnswer> result = criteria.list();
		return result;
	}

	@Override
	public List<QuestionSetAnswer> findByScopeAndPeriod(Scope scope, Period period) {
		return findByParameters(new QuestionSetAnswerSearchParameter(true).appendScope(scope).appendPeriod(period));
	}

	@Override
	public List<QuestionSetAnswer> findByScopeAndPeriodAndForm(Scope scope, Period period, Form form) {
		return findByParameters(new QuestionSetAnswerSearchParameter(false).appendScope(scope).appendPeriod(period).appendForm(form));
	}

	@Override
	public List<QuestionSetAnswer> findByScopeAndForm(Scope scope, Form form) {
		return findByParameters(new QuestionSetAnswerSearchParameter(false).appendScope(scope).appendForm(form));
	}

	@Override
	public List<Period> getAllQuestionSetAnswersPeriodsByScope(Long scopeId) {
		List<Period> resultList = JPA.em().createNamedQuery(QuestionSetAnswer.FIND_DISTINCT_PERIODS, Period.class).setParameter("scopeId", scopeId).getResultList();
		return resultList;
	}

	@Override
	public void deleteEmptyQuestionSetAnswers(Scope scope, Period period, Form form) {
		List<QuestionSetAnswer> questionSetAnswers = findByParameters(new QuestionSetAnswerSearchParameter(true).appendScope(scope).appendPeriod(period).appendForm(form));
		deleteEmptyQuestionSetAnswers(questionSetAnswers);
	}

	private void deleteEmptyQuestionSetAnswers(List<QuestionSetAnswer> questionSetAnswers) {
		for (int i = questionSetAnswers.size() - 1; i >= 0; i--) { // thx to Florian for the tip!!
			QuestionSetAnswer questionSetAnswer = questionSetAnswers.get(i);

			// first delete empty children (if any)
			if (!questionSetAnswer.getChildren().isEmpty()) {
				deleteEmptyQuestionSetAnswers(questionSetAnswer.getChildren());
			}

			if (questionSetAnswer.getChildren().isEmpty() && questionSetAnswer.getQuestionAnswers().isEmpty()) {
				QuestionSetAnswer parent = questionSetAnswer.getParent();
				Logger.info("DELETING (empty) {}", questionSetAnswer);
				if (parent == null) {
					remove(questionSetAnswer);
				} else {
					// in case of there is a parent, we have to update it (by simply removing the link with child, see "children" JPA annotation in QuestionSetAnswer entity)
					parent.getChildren().remove(questionSetAnswer);
					update(parent);
				}
			}
		}
	}

	@Override
	public void deleteByScopeAndPeriod(Scope scope, Period period) {
		List<QuestionSetAnswer> toDelete = findByParameters(new QuestionSetAnswerSearchParameter(false).appendPeriod(period).appendScope(scope));
		for (QuestionSetAnswer questionSetAnswer : toDelete) {
			remove(questionSetAnswer);
		}
	}

}
