
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
public class BaseActivityDataAE_BAD30 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (tCO2e in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tCO2e)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5331);

		// For each set of answers in A238, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA238 = questionSetAnswers.get(QuestionCode.A238);		if (questionSetAnswersA238 == null) {			return res;		}		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA238) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA240Answer = answersByCode.get(QuestionCode.A240);
			QuestionAnswer questionA242Answer = answersByCode.get(QuestionCode.A242);
			QuestionAnswer questionA239Answer = answersByCode.get(QuestionCode.A239);

			if (questionA240Answer == null ||
					questionA242Answer == null ||
					questionA239Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD30);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(toString(questionA239Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_9);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_11);
			baseActivityData.setActivityType(ActivityTypeCode.AT_69);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_344);
			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA240Answer, baseActivityDataUnit) * toDouble(questionA242Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
