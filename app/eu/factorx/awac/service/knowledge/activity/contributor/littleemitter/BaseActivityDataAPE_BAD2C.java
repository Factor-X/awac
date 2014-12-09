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
* AUTO-GENERATED by BADImportator at 09/12/2014 02:30
* Name of the BAD : électricité grise (prix)
 */
@Component
public class BaseActivityDataAPE_BAD2C extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = GJ )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5321);


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
                        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAP41 = questionSetAnswers.get(QuestionCode.AP41);
                //2.2 control if the list if different than null
                if (questionSetAnswersAP41 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAP41) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAP49Answer = answersByCode1.get(QuestionCode.AP49);
                                    QuestionAnswer questionAP43Answer = answersByCode1.get(QuestionCode.AP43);
                
                    //control them
                    if (                            questionAP49Answer == null  ||
                                                                                questionAP43Answer == null                                                 ) {
                       continue;
                    }
                    
                        if((getCode(questionAP43Answer).getKey().equals("AT_3"))){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.APE_BAD2C);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_1);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_2);
            baseActivityData.setActivityType(ActivityTypeCode.AT_3);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_164);

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAP49Answer, getUnitByCode(UnitCode.U5321))*toDouble(questionAP11Answer, getUnitByCode(UnitCode.U5115))/toDouble(questionAP12Answer, getUnitByCode(UnitCode.U5115)));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}