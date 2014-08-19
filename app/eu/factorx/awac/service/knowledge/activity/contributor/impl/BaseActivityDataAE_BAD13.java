
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
public class BaseActivityDataAE_BAD13 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (employé in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.employé)
		Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5336);

		// Get reference Number of Employees
		// TODO : check si 12 est bien aussi son propre question set? et faire que question12Answer soit du coup correct...
		List<QuestionSetAnswer> questionSetAnswersA12 = questionSetAnswers.get(QuestionCode.A12);
		if ((questionSetAnswersA12 == null) || questionSetAnswersA12.isEmpty()) {
			return res;
		}
		QuestionSetAnswer questionSet12Answer = questionSetAnswersA12.get(0);
		Map<QuestionCode, QuestionAnswer> questionSet12AnswerQuestionAnswers = byQuestionCode(questionSet12Answer.getQuestionAnswers());
		QuestionAnswer questionA12Answer = questionSet12AnswerQuestionAnswers.get(QuestionCode.A12);


		if (questionA12Answer == null) {
			return res;
		}


		// For each set of answers in A109, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetAnswersA109 = questionSetAnswers.get(QuestionCode.A109);
		if (questionSetAnswersA109 == null) {
			return res;
		}

		for (QuestionSetAnswer questionSetAnswer : questionSetAnswersA109) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA110Answer = answersByCode.get(QuestionCode.A110);
			QuestionAnswer questionA111Answer = answersByCode.get(QuestionCode.A111);
			QuestionAnswer questionA112Answer = answersByCode.get(QuestionCode.A112);

			if (questionA110Answer == null ||
					questionA111Answer == null ||
					questionA112Answer == null) {
				continue;
			}

			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD13);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose(toString(questionA110Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_8);
			baseActivityData.setActivityType(ActivityTypeCode.AT_17);
			// TODO: utiliser codes pour assigner la source
			/*if (getValueBoolean(answersByCode.get(QuestionCode.A110))) {
                    if (getValueBoolean(answersByCode.get(QuestionCode.A110))) {
                        baseActivityData.setActivitySource(ActivitySourceCode."Wallonie,  gare et bus en agglo");
                    } else {
                        baseActivityData.setActivitySource(ActivitySourceCode."Wallonie,  gare et bus hors agglo");
                    }
                } else {
                    if (getValueBoolean(answersByCode.get(QuestionCode.A110))) {
                        baseActivityData.setActivitySource(ActivitySourceCode."Wallonie,  gare pas de bus en agglo");
                    } else {
                        baseActivityData.setActivitySource(ActivitySourceCode."Wallonie,  gare pas de bus hors agglo");
                    }
                }
            } else {
                if (getValueBoolean(answersByCode.get(QuestionCode.A110))) {
                    if (getValueBoolean(answersByCode.get(QuestionCode.A110))) {
                        baseActivityData.setActivitySource(ActivitySourceCode."Wallonie, sans gare mais bus en agglo");
                    } else {
                        baseActivityData.setActivitySource(ActivitySourceCode."Wallonie, sans gare mais bus hors agglo");
                    }
                } else {
                    if (getValueBoolean(answersByCode.get(QuestionCode.A110))) {
                        baseActivityData.setActivitySource(ActivitySourceCode."Wallonie, sans gare ni bus en agglo");
                    } else {
                        baseActivityData.setActivitySource(ActivitySourceCode."Wallonie, sans gare ni bus hors agglo");
                    }
                }
            }*/
			baseActivityData.setActivityOwnership(false);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA12Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
