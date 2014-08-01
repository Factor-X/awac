package eu.factorx.awac.service;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;

import java.util.List;

public interface QuestionSetAnswerService extends PersistenceService<QuestionSetAnswer> {

	List<QuestionSetAnswer> findByParameters(QuestionSetAnswerSearchParameter searchParameter);

	/**
     * Gets all 'ancestor' QuestionSetAnswers (where parent is null) corresponding to given context (scope) and for given form.
     *
     * @param scope
     */
    List<QuestionSetAnswer> findByScope(Scope scope);

	/**
	 * Gets all QuestionSetAnswers corresponding to given context (period and scope).
	 *
	 * @param scope
	 * @param period
	 */
	List<QuestionSetAnswer> findByScopeAndPeriod(Scope scope, Period period);

	/**
	 * Gets all 'ancestor' QuestionSetAnswers (where parent is null) corresponding to given context (period and scope) and for given form.
	 *
	 * @param scope
	 * @param period
	 * @param form
	 */
	List<QuestionSetAnswer> findByScopeAndPeriodAndForm(Scope scope, Period period, Form form);

    /**
     * Gets all 'ancestor' QuestionSetAnswers (where parent is null) corresponding to given context (scope) and for given form.
     *
     * @param scope
     * @param form
     */
    List<QuestionSetAnswer> findByScopeAndForm(Scope scope,Form form);

	List<QuestionSetAnswer> findByCodes(List<QuestionCode> codes);

	void deleteAllFormAnswers(Scope scope, Period period, Form form);

	/***
	 * Find all distinct periods for which data related to given scope were saved
	 * @param scopeId
	 * @return a list of {@link Period}
	 */
	List<Period> getAllQuestionSetAnswersPeriodsByScope(Long scopeId);

}
