
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;


public class BaseActivityDataAE_BAD33A extends BaseActivityDataForProducts {

	public List<BaseActivityData> getBaseActivityData(QuestionSetAnswer questionSetAnswer, QuestionAnswer questionA245Answer) {
		
		List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (GJ in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5321);

		// For each set of answers in A273, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
			if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A273)) {

				Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());

				QuestionAnswer questionA274Answer = answersByCode.get(QuestionCode.A274);

				if (questionA274Answer == null) {
					continue;
				}


				for (QuestionSetAnswer questionSetAnswersChildChild : questionSetAnswersChild.getChildren()) {
					if (questionSetAnswersChildChild.getQuestionSet().getCode().equals(QuestionCode.A275)) {

						Map<QuestionCode, QuestionAnswer> answersByCodeChild = byQuestionCode(questionSetAnswersChildChild.getQuestionAnswers());

						QuestionAnswer questionA276Answer = answersByCodeChild.get(QuestionCode.A276);
						QuestionAnswer questionA277Answer = answersByCodeChild.get(QuestionCode.A277);

						if (questionA276Answer == null || questionA277Answer == null) {
							continue;
						}

						BaseActivityData baseActivityData = new BaseActivityData();

						baseActivityData.setKey(BaseActivityDataCode.AE_BAD33A);
						baseActivityData.setRank(1);
						baseActivityData.setSpecificPurpose(toString(questionA245Answer) + "-" + toString(questionA274Answer));
						baseActivityData.setActivityCategory(ActivityCategoryCode.AC_10);
						baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
						baseActivityData.setActivityType(ActivityTypeCode.AT_1);
						baseActivityData.setActivitySource(toActivitySourceCode(questionA276Answer));
						baseActivityData.setActivityOwnership(null);
			baseActivityData.setUnit(baseActivityDataUnit);
						baseActivityData.setValue(toDouble(questionA277Answer, baseActivityDataUnit));

						res.add(baseActivityData);
					}
				}
			}
		}
		return res;
	}

}
