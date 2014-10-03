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
* AUTO-GENERATED by BADImportator at 03/10/2014 09:04
* Name of the BAD : Site: transport amont possédé essence
 */
@Component
public class BaseActivityDataAE_BAD16A extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA132 = questionSetAnswers.get(QuestionCode.A132);
                //2.2 control if the list if different than null
                if (questionSetAnswersA132 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA132) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA133Answer = answersByCode1.get(QuestionCode.A133);
                
                    //control them
                    if (                            questionA133Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD16A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_6);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_5);

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA133Answer, baseActivityDataUnit));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}