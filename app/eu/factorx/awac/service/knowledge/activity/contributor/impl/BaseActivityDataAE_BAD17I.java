
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
public class BaseActivityDataAE_BAD17I extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (tonne.km in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.tonne.km)
		Unit baseActivityDataUnit = getUnitBySymbol("tonnes.km");

		// For each set of answers in A142, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA142 = questionSetAnswers.get(QuestionCode.A142);
		if (questionSetAnswersA142 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA142) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA145Answer = answersByCode.get(QuestionCode.A145);
			QuestionAnswer questionA146Answer = answersByCode.get(QuestionCode.A146);
			QuestionAnswer questionA155Answer = answersByCode.get(QuestionCode.A155);
			QuestionAnswer questionA143Answer = answersByCode.get(QuestionCode.A143);

			if (questionA145Answer == null ||
					questionA146Answer == null ||
					questionA155Answer == null ||
					questionA143Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD17I);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(toString(questionA143Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_6);
			baseActivityData.setActivityType(ActivityTypeCode.AT_30);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_177);
			baseActivityData.setActivityOwnership(false);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA145Answer, baseActivityDataUnit) * toDouble(questionA146Answer, baseActivityDataUnit) * toDouble(questionA155Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
