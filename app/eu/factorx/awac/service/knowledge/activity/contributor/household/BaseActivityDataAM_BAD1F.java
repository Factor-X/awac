package eu.factorx.awac.service.knowledge.activity.contributor.household;

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
* AUTO-GENERATED by BADImportator at 09/12/2014 11:33
* Name of the BAD : Chauffage: combustion directe (prix propane)
 */
@Component
public class BaseActivityDataAM_BAD1F extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAM26 = questionSetAnswers.get(QuestionCode.AM26);
                //2.2 control if the list if different than null
                if (questionSetAnswersAM26 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAM26) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAM27Answer = answersByCode1.get(QuestionCode.AM27);
                                    QuestionAnswer questionAM33Answer = answersByCode1.get(QuestionCode.AM33);
                                    QuestionAnswer questionAM36Answer = answersByCode1.get(QuestionCode.AM36);
                
                    //control them
                    if (                            questionAM27Answer == null  ||
                                                                                questionAM33Answer == null  ||
                                                                                questionAM36Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AM_BAD1F);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_1);
            baseActivityData.setActivitySubCategory(null);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAM27Answer));

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAM33Answer, baseActivityDataUnit)*1000/toDouble(questionAM36Answer, getUnitByCode(UnitCode.U5170)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}