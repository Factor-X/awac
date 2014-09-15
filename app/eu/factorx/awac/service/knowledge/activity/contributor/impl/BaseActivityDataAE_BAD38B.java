package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AUTO-GENERATED by BADImportator at 15/09/2014 12:57
 * Name of the BAD : Site: franchise - électricité
 */
@Component
public class BaseActivityDataAE_BAD38B extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = kW.h )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5156);


        //2. build BAD


        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD


        List<QuestionSetAnswer> questionSetAnswersA322 = questionSetAnswers.get(QuestionCode.A322);
        //2.2 control if the list if different than null
        if (questionSetAnswersA322 == null) {
            return res;
        }

        //loop ($repetition.mainQuestionSetDescription)
        for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA322) {

            //create a map for each repetition level
            Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

            //load question for this level
            QuestionAnswer questionA323Answer = answersByCode1.get(QuestionCode.A323);
            QuestionAnswer questionA328Answer = answersByCode1.get(QuestionCode.A328);
            QuestionAnswer questionA324Answer = answersByCode1.get(QuestionCode.A324);

            //control them
            if (questionA323Answer == null ||
                    questionA328Answer == null ||
                    questionA324Answer == null) {
                continue;
            }


            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD38B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionA323Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_15);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_2);
            baseActivityData.setActivityType(ActivityTypeCode.AT_3);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_44);

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA328Answer, getUnitByCode(UnitCode.U5156)) * toDouble(questionA324Answer));
            res.add(baseActivityData);


        }
        return res;
    }

}