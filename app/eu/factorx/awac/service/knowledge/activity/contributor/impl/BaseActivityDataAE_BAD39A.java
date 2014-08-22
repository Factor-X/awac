
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
 * CHECKED BY FLO
 */
public class BaseActivityDataAE_BAD39A extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (tCO2e in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tCO2e)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5331);

		// For each set of answers in A322, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA334 = questionSetAnswers.get(QuestionCode.A334);
		if (questionSetAnswersA334 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA334) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA336Answer = answersByCode.get(QuestionCode.A336);
			QuestionAnswer questionA337Answer = answersByCode.get(QuestionCode.A337);
			QuestionAnswer questionA335Answer = answersByCode.get(QuestionCode.A335);

			if (questionA336Answer == null ||
					questionA337Answer == null ||
					questionA335Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD39A);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(toString(questionA335Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_16);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_11);
			baseActivityData.setActivityType(ActivityTypeCode.AT_69);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_344);
			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA336Answer) * toDouble(questionA337Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
