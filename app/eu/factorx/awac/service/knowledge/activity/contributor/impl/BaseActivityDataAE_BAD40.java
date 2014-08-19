
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


public class BaseActivityDataAE_BAD40 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (kW in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.kW)
		Unit baseActivityDataUnit = getUnitBySymbol("kW");

		// For each set of answers in A45, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA45 = questionSetAnswers.get(QuestionCode.A45);
		if (questionSetAnswersA45 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA45) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA46Answer = answersByCode.get(QuestionCode.A46);

			if (questionA46Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD40);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_3);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_5);
			baseActivityData.setActivityType(ActivityTypeCode.AT_10);
			baseActivityData.setActivitySource(ActivitySourceCode.AS_161);
			baseActivityData.setActivityOwnership(true);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA46Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
