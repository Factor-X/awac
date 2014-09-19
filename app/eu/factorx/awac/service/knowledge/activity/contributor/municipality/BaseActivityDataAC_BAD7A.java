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
* AUTO-GENERATED by BADImportator at 19/09/2014 09:44
* Name of the BAD : Coffret de voirie: électricité grise
 */
@Component
public class BaseActivityDataAC_BAD7A extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAC56 = questionSetAnswers.get(QuestionCode.AC56);
                //2.2 control if the list if different than null
                if (questionSetAnswersAC56 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAC56) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC57Answer = answersByCode1.get(QuestionCode.AC57);
                                    QuestionAnswer questionAC59Answer = answersByCode1.get(QuestionCode.AC59);
                
                    //control them
                    if (                            questionAC57Answer == null  ||
                                                                                questionAC59Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD7A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionAC57Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_19);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_2);
            baseActivityData.setActivityType(ActivityTypeCode.AT_3);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_44);

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC59Answer, getUnitByCode(UnitCode.U5156)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}