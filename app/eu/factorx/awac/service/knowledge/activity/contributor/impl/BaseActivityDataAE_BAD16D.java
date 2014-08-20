
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;

/**
 * CHECK XM
 */
public class BaseActivityDataAE_BAD16D extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (kg in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5133);

		// For each set of answers in A132, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA132 = questionSetAnswers.get(QuestionCode.A132);		if (questionSetAnswersA132 == null) {			return res;		}		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA132) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA137Answer = answersByCode.get(QuestionCode.A137);
			QuestionAnswer questionA136Answer = answersByCode.get(QuestionCode.A136);
			QuestionAnswer questionA138Answer = answersByCode.get(QuestionCode.A138);
			QuestionAnswer questionA139Answer = answersByCode.get(QuestionCode.A139);
			QuestionAnswer questionA500Answer = answersByCode.get(QuestionCode.A500);

			if (questionA136Answer == null ||
					(questionA136Answer == null && (
							questionA137Answer == null ||
									questionA138Answer == null ||
									(questionA139Answer == null &&
											questionA500Answer == null)))) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD16D);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_6);
			baseActivityData.setActivityType(ActivityTypeCode.AT_8);
			baseActivityData.setActivitySource(toActivitySourceCode(questionA137Answer));
			baseActivityData.setActivityOwnership(true);
			// TODO: toutes les valeurs ne sont pas exprimées dans la même unité ! Comment gérer?
			// TODO: OK d'utiliser A138 comm eune valeur 1 ou 0 au lieu d'un booleén?
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA136Answer, baseActivityDataUnit) * (toDouble(questionA138Answer, baseActivityDataUnit) * toDouble(questionA139Answer, baseActivityDataUnit) + (1 - toDouble(questionA138Answer, baseActivityDataUnit)) * toDouble(questionA500Answer, baseActivityDataUnit)));

			res.add(baseActivityData);
		}
		return res;
	}

}
