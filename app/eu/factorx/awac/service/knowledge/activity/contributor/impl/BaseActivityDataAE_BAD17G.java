
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
public class BaseActivityDataAE_BAD17G extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (tonne.km in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5329);

		// For each set of answers in A142, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA142 = questionSetAnswers.get(QuestionCode.A142);
		if (questionSetAnswersA142 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA142) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
			QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
			QuestionAnswer questionA153Answer = answersByCode.get(QuestionCode.A153);
			QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

			if (questionA145Answer == null ||
					questionA146Answer == null ||
					questionA153Answer == null ||
					questionA143Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD17G);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(toString(questionA143Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_6);
			baseActivityData.setActivityType(ActivityTypeCode.AT_28);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_177);
			baseActivityData.setActivityOwnership(false);
			baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA145Answer, getUnitByCode(UnitCode.U5135)) * toDouble(questionA146Answer, getUnitByCode(UnitCode.U5106)) * toDouble(questionA153Answer));

			res.add(baseActivityData);
		}
		return res;
	}

}
