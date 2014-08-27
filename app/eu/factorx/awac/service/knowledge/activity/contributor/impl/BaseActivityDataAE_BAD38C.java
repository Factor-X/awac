
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
public class BaseActivityDataAE_BAD38C extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (t in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5135);

		// For each set of answers in A322, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA322 = questionSetAnswers.get(QuestionCode.A322);
		if (questionSetAnswersA322 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA322) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA323Answer = answersByCode.get(QuestionCode.A323);
			QuestionAnswer questionA324Answer = answersByCode.get(QuestionCode.A324);

			if (questionA323Answer == null ||
					questionA324Answer == null) {
				continue;
			}


			for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
				if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A329)) {

					Map<QuestionCode, QuestionAnswer> answersByCodeChild = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());

					QuestionAnswer questionA330Answer = answersByCodeChild.get(QuestionCode.A330);
					QuestionAnswer questionA331Answer = answersByCodeChild.get(QuestionCode.A331);

					if (questionA330Answer == null ||
							questionA331Answer == null) {
						continue;
					}

					BaseActivityData baseActivityData = new BaseActivityData();

					baseActivityData.setKey(BaseActivityDataCode.AE_BAD38C);
					baseActivityData.setRank(1);
					baseActivityData.setSpecificPurpose(toString(questionA323Answer));
					baseActivityData.setActivityCategory(ActivityCategoryCode.AC_15);
					baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_5);
					baseActivityData.setActivityType(ActivityTypeCode.AT_8);
					baseActivityData.setActivitySource(toActivitySourceCode(questionA330Answer));
					baseActivityData.setActivityOwnership(null);
			        baseActivityData.setUnit(baseActivityDataUnit);
					baseActivityData.setValue(toDouble(questionA331Answer, baseActivityDataUnit) * toDouble(questionA324Answer));

					res.add(baseActivityData);
				}
			}

		}
		return res;
	}

}
