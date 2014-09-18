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
* Name of the BAD : Site: mobilité indirecte par euros
 */
@Component
public class BaseActivityDataAE_BAD11C extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA78 = questionSetAnswers.get(QuestionCode.A78);
                //2.2 control if the list if different than null
                if (questionSetAnswersA78 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA78) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA83Answer = answersByCode1.get(QuestionCode.A83);
                                    QuestionAnswer questionA80Answer = answersByCode1.get(QuestionCode.A80);
                                    QuestionAnswer questionA88Answer = answersByCode1.get(QuestionCode.A88);
                                    QuestionAnswer questionA91Answer = answersByCode1.get(QuestionCode.A91);
                
                    //control them
                    if (                            questionA83Answer == null  ||
                                                                                questionA80Answer == null  ||
                                                                                questionA88Answer == null  ||
                                                                                questionA91Answer == null                                                 ) {
                       continue;
                    }
                    
                        if(toBoolean(questionA80Answer) && ((getCode(questionA83Answer).getKey().equals("AS_6")) || (getCode(questionA83Answer).getKey().equals("AS_7")))){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD11C);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionA83Answer));

            baseActivityData.setActivityOwnership(toBoolean(questionA80Answer));
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA88Answer, getUnitByCode(UnitCode.U5170))/toDouble(questionA91Answer, getUnitByCode(UnitCode.U5170)));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}