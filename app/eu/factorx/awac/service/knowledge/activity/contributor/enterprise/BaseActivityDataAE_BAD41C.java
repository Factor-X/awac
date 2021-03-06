package eu.factorx.awac.service.knowledge.activity.contributor.enterprise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.conversion.ConversionCriterion;
import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;
import org.springframework.stereotype.Component;

/**
* AUTO-GENERATED by BADImportator at 01/12/2014 12:38
* Name of the BAD : Site: mobilité  véhicules propres DPRO GPL direct
 */
@Component
public class BaseActivityDataAE_BAD41C extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = l )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5126);


        //2. build BAD


        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD


                            List<QuestionSetAnswer> questionSetAnswersA402 = questionSetAnswers.get(QuestionCode.A402);
                //2.2 control if the list if different than null
                if (questionSetAnswersA402 == null) {
                    return res;
                }

            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA402) {

                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA405Answer = answersByCode1.get(QuestionCode.A405);
                                    QuestionAnswer questionA403Answer = answersByCode1.get(QuestionCode.A403);
                                    QuestionAnswer questionA404Answer = answersByCode1.get(QuestionCode.A404);

                    //control them
                    if (                            questionA405Answer == null  ||
                                                                                questionA403Answer == null  ||
                                                                                questionA404Answer == null                                                 ) {
                       continue;
                    }


            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD41C);
                            baseActivityData.setAlternativeGroup("ALT_E_10_ROUTE_PROPRE");
                        baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_2);

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA405Answer, baseActivityDataUnit)+0*toDouble(questionA403Answer, baseActivityDataUnit)+0*toDouble(questionA404Answer, baseActivityDataUnit));
            res.add(baseActivityData);


                         }
                 return res;
    }

}
