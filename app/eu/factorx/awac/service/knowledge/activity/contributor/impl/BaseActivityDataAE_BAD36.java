
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;

/**
 * CHECKED BY FLO
 */
public class BaseActivityDataAE_BAD36 extends BaseActivityDataForProducts {

	public List<BaseActivityData> getBaseActivityData(QuestionSetAnswer questionSetAnswer, QuestionAnswer questionA245Answer) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (t in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.t)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5135);

		// For each set of answers in A300, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswersChild : questionSetAnswer.getChildren()) {
			if (questionSetAnswersChild.getQuestionSet().getCode().equals(QuestionCode.A300)) {

				Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswersChild.getQuestionAnswers());

				QuestionAnswer questionA302Answer = answersByCode.get(QuestionCode.A302);

				if (questionA302Answer == null) {
					continue;
				}
				for (QuestionSetAnswer questionSetAnswersChildChild : questionSetAnswersChild.getChildren()) {
					if (questionSetAnswersChildChild.getQuestionSet().getCode().equals(QuestionCode.A303)) {

						Map<QuestionCode, QuestionAnswer> answersByCodeChild = byQuestionCode(questionSetAnswersChildChild.getQuestionAnswers());

						QuestionAnswer questionA304Answer = answersByCodeChild.get(QuestionCode.A304);
						QuestionAnswer questionA307Answer = answersByCodeChild.get(QuestionCode.A307);
						QuestionAnswer questionA308Answer = answersByCodeChild.get(QuestionCode.A308);
						QuestionAnswer questionA306Answer = answersByCodeChild.get(QuestionCode.A306);
						QuestionAnswer questionA305Answer = answersByCodeChild.get(QuestionCode.A305);

						if (questionA304Answer == null || questionA305Answer == null ||
								questionA307Answer == null ||
								questionA308Answer == null ||
								questionA306Answer == null) {
							continue;
						}


						BaseActivityData baseActivityData = new BaseActivityData();

						baseActivityData.setKey(BaseActivityDataCode.AE_BAD36);
						baseActivityData.setRank(3);
						baseActivityData.setSpecificPurpose(toString(questionA245Answer) + " - " + toString(questionA304Answer));
						baseActivityData.setActivityCategory(ActivityCategoryCode.AC_13);
						baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
						baseActivityData.setActivityType(toActivityTypeCode(questionA307Answer));
						baseActivityData.setActivitySource(toActivitySourceCode(questionA306Answer));
						baseActivityData.setActivityOwnership(null);
            			baseActivityData.setUnit(baseActivityDataUnit);

                        Double percentage = 0.0;
                        if (getCode(questionA308Answer).getKey().equals("1")) { 
                            percentage = 0.0;
                        } else if (getCode(questionA308Answer).getKey().equals("2")) {
                            percentage = 0.1;
                        } else if (getCode(questionA308Answer).getKey().equals("3")) {
                            percentage = 0.2;
                        } else if (getCode(questionA308Answer).getKey().equals("4")) {
                            percentage = 0.3;
                        } else if (getCode(questionA308Answer).getKey().equals("5")) {
                            percentage = 0.4;
                        } else if (getCode(questionA308Answer).getKey().equals("6")) {
                            percentage = 0.5;
                        } else if (getCode(questionA308Answer).getKey().equals("7")) {
                            percentage = 0.6;
                        } else if (getCode(questionA308Answer).getKey().equals("8")) {
                            percentage = 0.7;
                        } else if (getCode(questionA308Answer).getKey().equals("9")) {
                            percentage = 0.8;
                        } else if (getCode(questionA308Answer).getKey().equals("10")) {
                            percentage = 0.9;
                        } else if (getCode(questionA308Answer).getKey().equals("11")) {
                            percentage = 1.0;
                        }

						baseActivityData.setValue(percentage * toDouble(questionA305Answer, baseActivityDataUnit));

						res.add(baseActivityData);
					}
				}
			}
		}
		return res;
	}
}
