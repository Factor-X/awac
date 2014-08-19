
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
public class BaseActivityDataAE_BAD4 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (t in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5135);

		// For each set of answers in A34, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA34 = questionSetAnswers.get(QuestionCode.A34);		if (questionSetAnswersA34 == null) {			return res;		}		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA34) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA35Answer = answersByCode.get(QuestionCode.A35);
			QuestionAnswer questionA36Answer = answersByCode.get(QuestionCode.A36);

			if (questionA35Answer == null ||
					questionA36Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD4);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_2);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_4);
			baseActivityData.setActivityType(ActivityTypeCode.AT_8);
			baseActivityData.setActivitySource(toActivitySourceCode(questionA35Answer));
			baseActivityData.setActivityOwnership(true);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA36Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
