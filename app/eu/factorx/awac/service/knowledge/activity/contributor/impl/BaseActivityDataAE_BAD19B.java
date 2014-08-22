
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
public class BaseActivityDataAE_BAD19B extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (kWh in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kWh)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5156);

		// For each set of answers in A164, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA164 = questionSetAnswers.get(QuestionCode.A164);
		if (questionSetAnswersA164 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA164) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA165Answer = answersByCode.get(QuestionCode.A165);

			if (questionA165Answer == null) {
				continue;
			}

            QuestionAnswer questionA169Answer = answersByCode.get(QuestionCode.A169);

			if (questionA169Answer == null) {
				continue;
			}

			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD19B);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(toString(questionA165Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_6);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_2);
			baseActivityData.setActivityType(ActivityTypeCode.AT_3);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_44);
			baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA169Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
