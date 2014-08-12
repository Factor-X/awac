
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
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
public class BaseActivityDataAE_BAD15 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (km.passager in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.km.passager)
		Unit baseActivityDataUnit = getUnitBySymbol("km.passager");
		Unit kmUnit = getUnitBySymbol("km");
		Unit employesUnit = getUnitBySymbol("employé");

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
					(!toBoolean(questionA123Answer) && questionA124Answer == null) ||
					(!toBoolean(questionA123Answer) && questionA125Answer == null && questionA126Answer == null)) {
				continue;
			}

			// intermediate variables to estimate flights
			Double travelDistance;
			// TODO: flight codes in variable
           /* FlightCode flightType;
            if (toBoolean(questionA123Answer)) {
                travelDistance = toDouble(questionA127Answer, kmUnit);
                // TODO code de vol
                FlightCode = "Vols Intercontinentaux (>4000 km A/R)";
            } else {
                //TODO code destination
                if (getCode(questionA124Answer, code) == Europe) {
                    travelDistance = 2500.0;
                    // TODO code de vol
                    FlightCode = "Vols europe (<4000km A/R)";
                } else {
                    travelDistance = 5000.0;
                    // TODO code de vol
                    FlightCode = "Vols Intercontinentaux (>4000 km A/R)";
                }
            }

            // TODO: comment déclarer que A122 est un %, i.e. sans unité?
            travelDistance *= toDouble(questionA12Answer, employesUnit) * toDouble(questionA122Answer);
*/
			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD15);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
			baseActivityData.setActivityType(ActivityTypeCode.AT_21);
			//TODO baseActivityData.setActivitySource(ActivitySourceCode.flightType);
			baseActivityData.setActivityOwnership(false);
			//TODO baseActivityData.setValue(travelDistance);

			res.add(baseActivityData);
		}
		return res;
	}

}
