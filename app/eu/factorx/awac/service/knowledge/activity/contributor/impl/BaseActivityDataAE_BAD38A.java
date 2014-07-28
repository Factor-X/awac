
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
 * CHECKED BY FLO
 */
public class BaseActivityDataAE_BAD38A extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (GJ in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
		Unit baseActivityDataUnit = getUnitBySymbol("GJ");

		// For each set of answers in A322, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers.get(QuestionCode.A322)) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA323Answer = answersByCode.get(QuestionCode.A323);
			QuestionAnswer questionA324Answer = answersByCode.get(QuestionCode.A324);

			if (questionA323Answer == null ||
					questionA324Answer == null) {
				continue;
			}

			for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
				if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A325)) {


					Map<QuestionCode, QuestionAnswer> answersByCodeChild = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());

					QuestionAnswer questionA326Answer = answersByCodeChild.get(QuestionCode.A326);
					QuestionAnswer questionA327Answer = answersByCodeChild.get(QuestionCode.A327);

					if (questionA326Answer == null ||
							questionA327Answer == null) {
						continue;
					}

					BaseActivityData baseActivityData = new BaseActivityData();

					baseActivityData.setKey(BaseActivityDataCode.AE_BAD38A);
					baseActivityData.setRank(1);
					baseActivityData.setSpecificPurpose(toString(questionA323Answer));
					baseActivityData.setActivityCategory(ActivityCategoryCode.AC_15);
					baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
					baseActivityData.setActivityType(ActivityTypeCode.AT_1);
					baseActivityData.setActivitySource(toActivitySourceCode(questionA326Answer));
					baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
					baseActivityData.setValue(toDouble(questionA327Answer, baseActivityDataUnit) * toDouble(questionA327Answer, baseActivityDataUnit));

					res.add(baseActivityData);
				}
			}


		}
		return res;
	}

}
