
package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.ActivityCategoryCode;
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
public class BaseActivityDataAE_BAD10 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
				List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (l in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.l)
		Unit baseActivityDataUnit = getUnitBySymbol("l");

		// For each set of answers in A67, build an ActivityBaseData (see specifications)
		for (QuestionSetAnswer questionSetAnswer : questionSetAnswers.get(QuestionCode.A67)) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA70Answer = answersByCode.get(QuestionCode.A70);
			QuestionAnswer questionA71Answer = answersByCode.get(QuestionCode.A71);
			QuestionAnswer questionA76Answer = answersByCode.get(QuestionCode.A76);
			QuestionAnswer questionA72Answer = answersByCode.get(QuestionCode.A72);
			QuestionAnswer questionA73Answer = answersByCode.get(QuestionCode.A73);
			QuestionAnswer questionA74Answer = answersByCode.get(QuestionCode.A74);
			QuestionAnswer questionA75Answer = answersByCode.get(QuestionCode.A75);
			QuestionAnswer questionA69Answer = answersByCode.get(QuestionCode.A69);
			QuestionAnswer questionA68Answer = answersByCode.get(QuestionCode.A68);

			if (questionA70Answer == null ||
					questionA71Answer == null ||
					questionA76Answer == null ||
					questionA72Answer == null ||
					(questionA73Answer == null &&
							questionA74Answer == null &&
							questionA75Answer == null) ||
					questionA69Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD10);
			baseActivityData.setRank(2);
			baseActivityData.setSpecificPurpose(toString(questionA68Answer));
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
			baseActivityData.setActivitySubCategory(toActivitySubCategoryCode(questionA70Answer));
			baseActivityData.setActivityType(ActivityTypeCode.AT_1);
			baseActivityData.setActivitySource(toActivitySourceCode(questionA71Answer));
			baseActivityData.setActivityOwnership(toBoolean(questionA69Answer));

			// TODO
			// Constantes adéquates de la réponse
			// if getCode(questionA73Answer,CODE) == 1
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA76Answer, baseActivityDataUnit) * (toDouble(questionA73Answer, baseActivityDataUnit)));
			// elseif getCode(questionA73Answer,CODE) == 2 || 3 || 4
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA76Answer, baseActivityDataUnit) * (toDouble(questionA74Answer, baseActivityDataUnit)));
			// elseif getCode(questionA73Answer,CODE) == 5 || 6
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA76Answer, baseActivityDataUnit) * (toDouble(questionA75Answer, baseActivityDataUnit)));

			res.add(baseActivityData);
		}
		return res;
	}

}
