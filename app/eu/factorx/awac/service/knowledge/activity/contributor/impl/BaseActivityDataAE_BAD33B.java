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
* AUTO-GENERATED by BADImportator at 15/09/2014 04:29
* Name of the BAD : Site: distribution aval - électricité
 */
@Component
public class BaseActivityDataAE_BAD33B extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA244 = questionSetAnswers.get(QuestionCode.A244);
                //2.2 control if the list if different than null
                if (questionSetAnswersA244 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA244) {
             
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.A272)) {
             
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer3 : getChildrenQuestionSetAnswers(questionSetAnswer2,QuestionCode.A273)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode3 = byQuestionCode(questionSetAnswer3.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA278Answer = answersByCode3.get(QuestionCode.A278);
                
                    //control them
                    if (                            questionA278Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD33B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose("A245-A274");
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_10);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_2);
            baseActivityData.setActivityType(ActivityTypeCode.AT_3);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_44);

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA278Answer, getUnitByCode(UnitCode.U5156)));
            res.add(baseActivityData);

            
                         }
                         }
                         }
                 return res;
    }

}