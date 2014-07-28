
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
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
public class BaseActivityDataAE_BAD25 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (m3 in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.m3)
		Unit baseActivityDataUnit = unitService.findBySymbol("m3");

		// For each set of answers in A197, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers.get(QuestionCode.A197)) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA198Answer = answersByCode.get(QuestionCode.A198);
			QuestionAnswer questionA199Answer = answersByCode.get(QuestionCode.A199);
			QuestionAnswer questionA200Answer = answersByCode.get(QuestionCode.A200);
			QuestionAnswer questionA195Answer = answersByCode.get(QuestionCode.A195);

			if (questionA198Answer == null ||
					questionA199Answer == null ||
					questionA200Answer == null ||
					questionA195Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD25);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
			baseActivityData.setActivityType(toActivityTypeCode(questionA200Answer));
			baseActivityData.setActivitySource(toActivitySourceCode(questionA198Answer));
			baseActivityData.setActivityOwnership(toBoolean(questionA195Answer));
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA199Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
