
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
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
public class BaseActivityDataAE_BAD23 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (équivalent.habitant in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.équivalent.habitant)
		Unit baseActivityDataUnit = getUnitBySymbol("équivalent.habitant");

		// For each set of answers in A188, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers.get(QuestionCode.A188)) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA189Answer = answersByCode.get(QuestionCode.A189);
			QuestionAnswer questionA190Answer = answersByCode.get(QuestionCode.A190);

			if (questionA189Answer == null ||
					questionA190Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD23);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose("-pension");
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
			// TODO: FLORIAN
			// baseActivityData.setActivityType(ActivityTypeCode.EPURATION);
			// TODO: FLORIAN
			// baseActivityData.setActivitySource(ActivitySourceCode.EAU_USEE);
			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA189Answer, baseActivityDataUnit) * toDouble(questionA190Answer, baseActivityDataUnit) / 365);

			res.add(baseActivityData);
		}
		return res;
	}

}
