package eu.factorx.awac.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Component;

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
			DetachedCriteria formCriteria = DetachedCriteria.forClass(Form.class);
			formCriteria.add(Restrictions.eq("id", searchParameter.getForm().getId()));
			formCriteria.createAlias("questionSets", "fqs");
			formCriteria.setProjection(Projections.property("fqs.id"));

			criteria.add(Subqueries.propertyIn("questionSet.id", formCriteria));
		}

		if (searchParameter.getScope() != null) {
			criteria.add(Restrictions.eq("scope.id", searchParameter.getScope().getId()));
		}

		if (searchParameter.getPeriod() != null) {
			criteria.add(Restrictions.eq("period.id", searchParameter.getPeriod().getId()));
		}

		if (!searchParameter.getWithSubItems()) {
			criteria.add(Restrictions.isNull("parent"));
		}
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

}
