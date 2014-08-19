
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
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
 * CHECK XM
 */
public class BaseActivityDataAE_BAD5 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (kg in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kg)
		Unit baseActivityDataUnit = getUnitBySymbol("kg");

		// For each set of answers in A42, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA42 = questionSetAnswers.get(QuestionCode.A42);
		if (questionSetAnswersA42 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA42) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA43Answer = answersByCode.get(QuestionCode.A43);
			QuestionAnswer questionA44Answer = answersByCode.get(QuestionCode.A44);

			if (questionA43Answer == null ||
					questionA44Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD5);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_3);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_5);
			baseActivityData.setActivityType(ActivityTypeCode.AT_8);
			baseActivityData.setActivitySource(toActivitySourceCode(questionA43Answer));
			baseActivityData.setActivityOwnership(true);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA44Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
