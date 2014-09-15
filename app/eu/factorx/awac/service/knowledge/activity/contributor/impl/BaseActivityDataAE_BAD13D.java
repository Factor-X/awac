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
* AUTO-GENERATED by BADImportator at 15/09/2014 12:52
* Name of the BAD : Site: mobilité DDT indirecte par employé
 */
@Component
public class BaseActivityDataAE_BAD13D extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = employé )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5336);


        //2. build BAD

                //load question without repetition
                    List<QuestionSetAnswer> questionSetAnswersA109 = questionSetAnswers.get(QuestionCode.A109);
        
        //... and control
        if (
                    questionSetAnswersA109 == null || questionSetAnswersA109.size()==0         ) {
        return res;
        }

        //load question
                        QuestionAnswer questionA110Answer = byQuestionCode(questionSetAnswersA109.get(0).getQuestionAnswers()).get(QuestionCode.A110);
                QuestionAnswer questionA111Answer = byQuestionCode(questionSetAnswersA109.get(0).getQuestionAnswers()).get(QuestionCode.A111);
                QuestionAnswer questionA112Answer = byQuestionCode(questionSetAnswersA109.get(0).getQuestionAnswers()).get(QuestionCode.A112);
                        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA1 = questionSetAnswers.get(QuestionCode.A1);
                //2.2 control if the list if different than null
                if (questionSetAnswersA1 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA1) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA12Answer = answersByCode1.get(QuestionCode.A12);
                
                    //control them
                    if (                            questionA12Answer == null                                                 ) {
                       continue;
                    }
                    
                        if(toBoolean(questionA110Answer) && !toBoolean(questionA111Answer) && !toBoolean(questionA112Answer)){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD13D);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_8);
            baseActivityData.setActivityType(ActivityTypeCode.AT_17);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_169);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA12Answer));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}