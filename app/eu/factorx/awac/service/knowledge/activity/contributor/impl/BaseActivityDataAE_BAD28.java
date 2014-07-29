
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.models.code.type.BaseActivityDataCode;
import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;

/**
 * CHECKED FJ
 */
public class BaseActivityDataAE_BAD28 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (tCO2e in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tCO2e)
		Unit baseActivityDataUnit = getUnitBySymbol("tCO2e");

		// For each set of answers in A224, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA224 = questionSetAnswers.get(QuestionCode.A224);		if (questionSetAnswersA224 == null) {			return res;		}		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA224) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA226Answer = answersByCode.get(QuestionCode.A226);
			QuestionAnswer questionA228Answer = answersByCode.get(QuestionCode.A228);
			QuestionAnswer questionA225Answer = answersByCode.get(QuestionCode.A225);

			if (questionA226Answer == null ||
					questionA228Answer == null ||
					questionA225Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD28);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(toString(questionA225Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_8);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_11);
			baseActivityData.setActivityType(ActivityTypeCode.AT_69);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_344);
			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA226Answer, baseActivityDataUnit) * toDouble(questionA228Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
