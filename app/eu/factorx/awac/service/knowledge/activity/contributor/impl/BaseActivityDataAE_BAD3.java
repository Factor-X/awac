
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
public class BaseActivityDataAE_BAD3 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (GJ in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
		Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

		// For each set of answers in A25, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers.get(QuestionCode.A25)) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA26Answer = answersByCode.get(QuestionCode.A26);
			QuestionAnswer questionA28Answer = answersByCode.get(QuestionCode.A28);
			QuestionAnswer questionA27Answer = answersByCode.get(QuestionCode.A27);

			if (questionA26Answer == null ||
					questionA28Answer == null ||
					questionA27Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD3);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_1);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_3);
			baseActivityData.setActivityType(ActivityTypeCode.AT_7);
			baseActivityData.setActivitySource(toActivitySourceCode(questionA26Answer));
			baseActivityData.setActivityOwnership(true);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA28Answer, baseActivityDataUnit) / toDouble(questionA27Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
