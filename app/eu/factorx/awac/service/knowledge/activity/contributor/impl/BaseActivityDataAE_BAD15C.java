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
* Name of the BAD : Site: mobilité DPRO avion indirect
 */
@Component
public class BaseActivityDataAE_BAD15C extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = passagers.km )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5328);


        //2. build BAD

                //load question without repetition
                    List<QuestionSetAnswer> questionSetAnswersA121 = questionSetAnswers.get(QuestionCode.A121);
        
        //... and control
        if (
                    questionSetAnswersA121 == null || questionSetAnswersA121.size()==0         ) {
        return res;
        }

        //load question
                        QuestionAnswer questionA122Answer = byQuestionCode(questionSetAnswersA121.get(0).getQuestionAnswers()).get(QuestionCode.A122);
                QuestionAnswer questionA123Answer = byQuestionCode(questionSetAnswersA121.get(0).getQuestionAnswers()).get(QuestionCode.A123);
                QuestionAnswer questionA124Answer = byQuestionCode(questionSetAnswersA121.get(0).getQuestionAnswers()).get(QuestionCode.A124);
                        
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
                    
                        if(!toBoolean(questionA123Answer) && !toBoolean(questionA124Answer)){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD15C);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
            baseActivityData.setActivityType(ActivityTypeCode.AT_21);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_174);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(5000*toDouble(questionA12Answer)*toDouble(questionA122Answer));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}