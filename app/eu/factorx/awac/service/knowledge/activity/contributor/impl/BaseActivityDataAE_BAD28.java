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
* AUTO-GENERATED by BADImportator at 15/09/2014 12:57
* Name of the BAD : Site: achats matière spécifique
 */
@Component
public class BaseActivityDataAE_BAD28 extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = tCO2e )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5331);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA224 = questionSetAnswers.get(QuestionCode.A224);
                //2.2 control if the list if different than null
                if (questionSetAnswersA224 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA224) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA225Answer = answersByCode1.get(QuestionCode.A225);
                                    QuestionAnswer questionA226Answer = answersByCode1.get(QuestionCode.A226);
                                    QuestionAnswer questionA228Answer = answersByCode1.get(QuestionCode.A228);
                
                    //control them
                    if (                            questionA225Answer == null  ||
                                                                                questionA226Answer == null  ||
                                                                                questionA228Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD28);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionA225Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_8);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
            baseActivityData.setActivityType(ActivityTypeCode.AT_69);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_344);

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA226Answer)*toDouble(questionA228Answer));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}