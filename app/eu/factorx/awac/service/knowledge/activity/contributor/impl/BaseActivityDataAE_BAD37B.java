
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
 * CHECKED BY FLO
 */
public class BaseActivityDataAE_BAD37B extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (kWh in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
		Unit baseActivityDataUnit = unitService.findBySymbol("kWh");

		// For each set of answers in A311, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers.get(QuestionCode.A311)) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA312Answer = answersByCode.get(QuestionCode.A312);

			if (questionA312Answer == null) {
				continue;
			}
			for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
				if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A313)) {


					Map<QuestionCode, QuestionAnswer> answersByCodeChild = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());

					QuestionAnswer questionA316Answer = answersByCodeChild.get(QuestionCode.A316);

					if (questionA316Answer == null) {
						continue;
					}


					BaseActivityData baseActivityData = new BaseActivityData();

					baseActivityData.setKey(BaseActivityDataCode.AE_BAD37B);
					baseActivityData.setRank(1);
					baseActivityData.setSpecificPurpose(toString(questionA312Answer));
					baseActivityData.setActivityCategory(ActivityCategoryCode.AC_14);
					baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_5);
					baseActivityData.setActivityType(ActivityTypeCode.AT_8);
					baseActivityData.setActivitySource(ActivitySourceCode.AS_164);
					baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
					baseActivityData.setValue(toDouble(questionA316Answer, baseActivityDataUnit));

					res.add(baseActivityData);
				}
			}
		}
		return res;
	}

}
