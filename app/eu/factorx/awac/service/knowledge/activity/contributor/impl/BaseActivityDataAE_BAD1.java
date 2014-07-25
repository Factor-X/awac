package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

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

@Component
public class BaseActivityDataAE_BAD1 extends ActivityResultContributor {

	@Override
	public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {
		List<BaseActivityData> res = new ArrayList<>();

		// Get Target Unit (GJ in this case)
		// Allow finding unit by a UnitCode: getUnitByCode(UnitCode.GJ)
		Unit baseActivityDataUnit = unitService.findBySymbol("GJ");

		// For each set of answers in A15, build an ActivityBaseData (see specifications)
		List<QuestionSetAnswer> questionSetA15Answers = questionSetAnswers.get(QuestionCode.A15);
		if (questionSetA15Answers == null) {
			return res;
		}
		for (QuestionSetAnswer questionSetAnswer : questionSetA15Answers) {

			Map<QuestionCode, QuestionAnswer> answersByCode = byQuestionCode(questionSetAnswer.getQuestionAnswers());

			QuestionAnswer questionA16Answer = answersByCode.get(QuestionCode.A16);
			QuestionAnswer questionA17Answer = answersByCode.get(QuestionCode.A17);

			if (questionA16Answer == null ||
					questionA17Answer == null) {
				continue;
			}


			BaseActivityData baseActivityData = new BaseActivityData();

			baseActivityData.setKey(BaseActivityDataCode.AE_BAD1);
			baseActivityData.setRank(1);
			baseActivityData.setSpecificPurpose(null);
			baseActivityData.setActivityCategory(ActivityCategoryCode.AC_1);
			baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
			baseActivityData.setActivityType(ActivityTypeCode.AT_1);
			
			baseActivityData.setActivitySource(toActivitySourceCode(questionA16Answer));
			
			baseActivityData.setActivityOwnership(true);
			baseActivityData.setUnit(baseActivityDataUnit);
			baseActivityData.setValue(toDouble(questionA17Answer, baseActivityDataUnit));

			res.add(baseActivityData);
		}
		return res;
	}

}
