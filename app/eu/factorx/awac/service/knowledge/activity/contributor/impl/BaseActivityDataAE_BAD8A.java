
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
 * CHECK XM
 */
public class BaseActivityDataAE_BAD8A extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (l in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
		Unit baseActivityDataUnit = unitService.findBySymbol("l");

		// For each set of answers in A58, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers.get(QuestionCode.A58)) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA59Answer = answersByCode.get(QuestionCode.A59);

			if (questionA59Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD8A);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_8);
			baseActivityData.setActivityType(ActivityTypeCode.AT_1);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_5);
			baseActivityData.setActivityOwnership(false);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA59Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
