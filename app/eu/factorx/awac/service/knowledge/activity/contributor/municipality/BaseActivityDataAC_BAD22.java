package eu.factorx.awac.service.knowledge.activity.contributor.municipality;

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
* AUTO-GENERATED by BADImportator at 07/10/2014 09:39
* Name of the BAD : Site: mobilité véhicules non possédés DDT indirecte par euros
 */
@Component
public class BaseActivityDataAC_BAD22 extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAC513 = questionSetAnswers.get(QuestionCode.AC513);
                //2.2 control if the list if different than null
                if (questionSetAnswersAC513 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAC513) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC515Answer = answersByCode1.get(QuestionCode.AC515);
                                    QuestionAnswer questionAC517Answer = answersByCode1.get(QuestionCode.AC517);
                                    QuestionAnswer questionAC516Answer = answersByCode1.get(QuestionCode.AC516);
                
                    //control them
                    if (                            questionAC515Answer == null  ||
                                                                                questionAC517Answer == null  ||
                                                                                questionAC516Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD22);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_8);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAC515Answer));

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC517Answer, getUnitByCode(UnitCode.U5170))/toDouble(questionAC516Answer, getUnitByCode(UnitCode.U5170)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}