
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
 * CHECK XM
 */
public class BaseActivityDataAE_BAD26A extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (t in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5135);

		// For each set of answers in A201, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA201 = questionSetAnswers.get(QuestionCode.A201);		if (questionSetAnswersA201 == null) {			return res;		}		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA201) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA202Answer = answersByCode.get(QuestionCode.A202);
			QuestionAnswer questionA204Answer = answersByCode.get(QuestionCode.A204);
			QuestionAnswer questionA501Answer = answersByCode.get(QuestionCode.A501);

			if (questionA202Answer == null ||
					questionA204Answer == null ||
					questionA501Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD26A);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
			// TODO: baseActivityData.setActivityType(toDouble(questionA204Answer));
			// TODO: Code à matcher
			// TODO: baseActivityData.setActivitySource(getCode(ActivitySourceCode("DCO"));
			baseActivityData.setActivityOwnership(toBoolean(questionA501Answer));
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA202Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
