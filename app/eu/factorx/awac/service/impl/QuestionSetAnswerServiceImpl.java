package eu.factorx.awac.service.impl;

import eu.factorx.awac.controllers.AverageController;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;
import eu.factorx.awac.service.QuestionSetAnswerSearchParameter;
import eu.factorx.awac.service.QuestionSetAnswerService;
import eu.factorx.awac.util.JPAUtils;
import org.springframework.stereotype.Component;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuestionSetAnswerServiceImpl extends AbstractJPAPersistenceServiceImpl<QuestionSetAnswer> implements QuestionSetAnswerService {

	@Override
	public List<QuestionSetAnswer> findByParameters(QuestionSetAnswerSearchParameter searchParameter) {

		List<String> parts = new ArrayList<>();
		Map<String, Object> parameters = new HashMap<>();

		parts.add("select e from QuestionSetAnswer e where 1 = 1");

		if (searchParameter.getForm() != null) {
			Form form = searchParameter.getForm();
			if (searchParameter.getWithChildren()) {
				parts.add("and e.questionSet in :questionSets");
				parameters.put("questionSets", form.getAllQuestionSets());
			} else {
				parts.add("and e.questionSet in :questionSets");
				parameters.put("questionSets", form.getQuestionSets());
			}
		}

		if (searchParameter.getScope() != null) {
			parts.add("and e.scope = :scope");
			parameters.put("scope", searchParameter.getScope());
		}

		if (searchParameter.getQuestionSet() != null) {
			parts.add("and e.questionSet = :questionSet");
			parameters.put("questionSet", searchParameter.getQuestionSet());
		}

		if (searchParameter.getPeriod() != null) {
			parts.add("and e.period = :period");
			parameters.put("period", searchParameter.getPeriod());
		}

		if (!searchParameter.getWithChildren()) {
			parts.add("and e.parent = null");
		}

		parts.add("order by e.repetitionIndex");

		TypedQuery<QuestionSetAnswer> query = JPAUtils.build(parts, parameters, QuestionSetAnswer.class);
		return query.getResultList();

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
	public List<QuestionSetAnswer> findByScopeAndPeriodAndQuestionSet(Scope scope, Period period, QuestionSet questionSet) {
		return findByParameters(new QuestionSetAnswerSearchParameter(false).appendScope(scope).appendPeriod(period).appendQuestionSet(questionSet));
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
		List<QuestionSetAnswer> questionSetAnswers = findByParameters(new QuestionSetAnswerSearchParameter(false).appendScope(scope).appendPeriod(period).appendForm(form));
		deleteEmptyQuestionSetAnswers(questionSetAnswers);
	}


	@Override
	public Map<QuestionCode, List<QuestionSetAnswer>> getAllQuestionSetAnswers(Scope scope, Period period) {
		List<QuestionSetAnswer> questionSetAnswers = this.findByScopeAndPeriod(scope, period);

		Map<QuestionCode, List<QuestionSetAnswer>> res = new HashMap<>();
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers) {
			QuestionCode code = questionSetAnswer.getQuestionSet().getCode();
			if (!res.containsKey(code)) {
				res.put(code, new ArrayList<QuestionSetAnswer>());
			}
			res.get(code).add(questionSetAnswer);
		}

		return res;
	}

	@Override
	public List<QuestionSetAnswer> findByScope(Scope scope) {
		return findByParameters(new QuestionSetAnswerSearchParameter(true).appendScope(scope));
	}

	@Override
	public List<QuestionSetAnswer> findByCodes(List<QuestionCode> codes) {
		List<QuestionSetAnswer> resultList = JPA.em().createNamedQuery(QuestionSetAnswer.FIND_BY_CODES, QuestionSetAnswer.class).setParameter("codes", codes).getResultList();
		return resultList;
	}

	@Override
	public List<QuestionSetAnswer> findByQuestionSetAndCalculatorInstance(QuestionSet questionSet, AverageController.ScopeAndPeriod scopeAndPeriod) {

		List<QuestionSetAnswer> resultList = JPA.em().createNamedQuery(QuestionSetAnswer.FIND_BY_QUESTION_SET, QuestionSetAnswer.class)
				.setParameter("questionSet", questionSet)
				.getResultList();

		ArrayList<QuestionSetAnswer> finalList = new ArrayList<>();

		for (QuestionSetAnswer questionSetAnswer : resultList) {
			if (questionSetAnswer.getScope().equals(scopeAndPeriod.getScope()) &&
					questionSetAnswer.getPeriod().equals(scopeAndPeriod.getPeriod())) {

				finalList.add(questionSetAnswer);
			}
		}

		return finalList;

	}

	/**
	 * ***
	 * PRIVATE
	 * ******
	 */
	private void deleteEmptyQuestionSetAnswers(List<QuestionSetAnswer> questionSetAnswers) {
		for (int i = questionSetAnswers.size() - 1; i >= 0; i--) { // thx to Florian for the tip!!
			QuestionSetAnswer questionSetAnswer = questionSetAnswers.get(i);

			// first delete empty children (if any)
			if (!questionSetAnswer.getChildren().isEmpty()) {
				deleteEmptyQuestionSetAnswers(questionSetAnswer.getChildren());
			}

			if (questionSetAnswer.getChildren().isEmpty() && questionSetAnswer.getQuestionAnswers().isEmpty()) {
				Logger.info("--> DELETING (empty) {}", questionSetAnswer);
				QuestionSetAnswer parent = questionSetAnswer.getParent();
				if (parent == null) {
					if (questionSetAnswer.getQuestionSet().getParent() != null) {
						remove(questionSetAnswer);
					}
				} else {
					// in case of there is a parent, we have to update it (by removing the link with child; children are automatically deleted due to 'orphanRemoval' attribute value)
					parent.getChildren().remove(questionSetAnswer);
					update(parent);
				}
			}
		}
	}


}
