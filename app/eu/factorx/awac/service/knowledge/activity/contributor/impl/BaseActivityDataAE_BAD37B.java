package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;
import org.springframework.stereotype.Component;

/**
* AUTO-GENERATED by BADImportator at 16/09/2014 12:14
* Name of the BAD : Site: actif loué - électricité
 */
@Component
public class BaseActivityDataAE_BAD37B extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA311 = questionSetAnswers.get(QuestionCode.A311);
                //2.2 control if the list if different than null
                if (questionSetAnswersA311 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA311) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA312Answer = answersByCode1.get(QuestionCode.A312);
                                    QuestionAnswer questionA316Answer = answersByCode1.get(QuestionCode.A316);
                
                    //control them
                    if (                            questionA312Answer == null  ||
                                                                                questionA316Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD37B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionA312Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_14);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_2);
            baseActivityData.setActivityType(ActivityTypeCode.AT_3);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_44);

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA316Answer, getUnitByCode(UnitCode.U5156)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}