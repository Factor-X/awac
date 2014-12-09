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
* AUTO-GENERATED by BADImportator at 09/12/2014 04:04
* Name of the BAD : Climatisation
 */
@Component
public class BaseActivityDataAPE_BAD4 extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = t )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5135);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAP58 = questionSetAnswers.get(QuestionCode.AP58);
                //2.2 control if the list if different than null
                if (questionSetAnswersAP58 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAP58) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAP59Answer = answersByCode1.get(QuestionCode.AP59);
                                    QuestionAnswer questionAP60Answer = answersByCode1.get(QuestionCode.AP60);
                
                    //control them
                    if (                            questionAP59Answer == null  ||
                                                                                questionAP60Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.APE_BAD4);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_2);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_21);
            baseActivityData.setActivityType(ActivityTypeCode.AT_8);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAP59Answer));

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAP60Answer, baseActivityDataUnit));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}