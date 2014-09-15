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
* AUTO-GENERATED by BADImportator at 15/09/2014 03:04
* Name of the BAD : Site: investissement scope 1
 */
@Component
public class BaseActivityDataAE_BAD39A extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA334 = questionSetAnswers.get(QuestionCode.A334);
                //2.2 control if the list if different than null
                if (questionSetAnswersA334 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA334) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA335Answer = answersByCode1.get(QuestionCode.A335);
                                    QuestionAnswer questionA336Answer = answersByCode1.get(QuestionCode.A336);
                                    QuestionAnswer questionA337Answer = answersByCode1.get(QuestionCode.A337);
                
                    //control them
                    if (                            questionA335Answer == null  ||
                                                                                questionA336Answer == null  ||
                                                                                questionA337Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD39A);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionA335Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_16);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_11);
            baseActivityData.setActivityType(ActivityTypeCode.AT_69);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_344);

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA336Answer)*toDouble(questionA337Answer));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}