
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
public class BaseActivityDataAE_BAD15 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (passagers.km in this case)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5328);
		Unit kmUnit = getUnitByCode(UnitCode.U5106);
		Unit employesUnit = getUnitByCode(UnitCode.U5336);

		// Get reference Number of Employees
		List<QuestionSetAnswer> questionSetAnswersA1 = questionSetAnswers.get(QuestionCode.A1);
		if ((questionSetAnswersA1 == null) || questionSetAnswersA1.isEmpty()) {
			return res;
		}
		QuestionSetAnswer questionSet1Answer = questionSetAnswersA1.get(0);
		Map<QuestionCode, QuestionAnswer> questionSet1AnswerQuestionAnswers = byQuestionCode(questionSet1Answer.getQuestionAnswers());
		QuestionAnswer questionA12Answer = questionSet1AnswerQuestionAnswers.get(QuestionCode.A12);

		if (questionA12Answer == null) {
			return res;
		}


		// For each set of answers in A121, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA121 = questionSetAnswers.get(QuestionCode.A121);
		if (questionSetAnswersA121 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA121) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA122Answer = answersByCode.get(QuestionCode.A122);
			QuestionAnswer questionA123Answer = answersByCode.get(QuestionCode.A123);
			QuestionAnswer questionA124Answer = answersByCode.get(QuestionCode.A124);
			QuestionAnswer questionA125Answer = answersByCode.get(QuestionCode.A125);
			QuestionAnswer questionA126Answer = answersByCode.get(QuestionCode.A126);
			QuestionAnswer questionA127Answer = answersByCode.get(QuestionCode.A127);

			if (questionA122Answer == null ||
					questionA123Answer == null ||
					(toBoolean(questionA123Answer) && questionA127Answer == null) ||
					(!toBoolean(questionA123Answer) && questionA124Answer == null)) {
				continue;
			}

			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD15);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
			baseActivityData.setActivityType(ActivityTypeCode.AT_21);
            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);

            if (toBoolean(questionA123Answer))  {
                baseActivityData.setActivitySource(ActivitySourceCode.AS_174);
                baseActivityData.setValue(toDouble(questionA12Answer) * toDouble(questionA122Answer) * toDouble(questionA127Answer,getUnitByCode(UnitCode.U5106)));
            } else  {
                if (toBoolean(questionA124Answer))  {
                    baseActivityData.setActivitySource(ActivitySourceCode.AS_173);
                    baseActivityData.setValue(toDouble(questionA12Answer) * toDouble(questionA122Answer) * 2500.0);
                } else  {
                    baseActivityData.setActivitySource(ActivitySourceCode.AS_174);
                    baseActivityData.setValue(toDouble(questionA12Answer) * toDouble(questionA122Answer) * 5000.0);
                }
            }

            res.add(baseActivityData);
		}
		return res;
	}

}
