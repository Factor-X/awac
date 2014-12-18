package eu.factorx.awac.service.knowledge.activity.contributor.littleemitter;

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
* AUTO-GENERATED by BADImportator at 18/12/2014 10:10
* Name of the BAD : Chauffage: combustion directe
 */
@Component
public class BaseActivityDataAPE_BAD1I extends ActivityResultContributor {

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

                //load question without repetition
                    List<QuestionSetAnswer> questionSetAnswersAP2 = questionSetAnswers.get(QuestionCode.AP2);
        
        //... and control
        if (
                    questionSetAnswersAP2 == null || questionSetAnswersAP2.size()==0         ) {
        return res;
        }

        //load question
                        QuestionAnswer questionAP11Answer = byQuestionCode(questionSetAnswersAP2.get(0).getQuestionAnswers()).get(QuestionCode.AP11);
                QuestionAnswer questionAP12Answer = byQuestionCode(questionSetAnswersAP2.get(0).getQuestionAnswers()).get(QuestionCode.AP12);
                
        //control
        if(false
                             || questionAP11Answer==null
                     || questionAP12Answer==null
                     ){
            return res;
        }

        



        //and control

        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAP17 = questionSetAnswers.get(QuestionCode.AP17);
                //2.2 control if the list if different than null
                if (questionSetAnswersAP17 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAP17) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAP18Answer = answersByCode1.get(QuestionCode.AP18);
                                    QuestionAnswer questionAP25Answer = answersByCode1.get(QuestionCode.AP25);
                
                    //control them
                    if (                            questionAP18Answer == null  ||
                                                                                questionAP25Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.APE_BAD1I);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_1);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_14);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAP18Answer));

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAP25Answer, getUnitByCode(UnitCode.U5126))*toDouble(questionAP11Answer, getUnitByCode(UnitCode.U5115))/toDouble(questionAP12Answer, getUnitByCode(UnitCode.U5115)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}