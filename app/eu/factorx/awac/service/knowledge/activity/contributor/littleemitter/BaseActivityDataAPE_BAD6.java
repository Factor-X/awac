package eu.factorx.awac.service.knowledge.activity.contributor.littleemitter;

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
* AUTO-GENERATED by BADImportator at 10/12/2014 03:43
* Name of the BAD : mobilité DPRO kilométrage
 */
@Component
public class BaseActivityDataAPE_BAD6 extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAP70 = questionSetAnswers.get(QuestionCode.AP70);
                //2.2 control if the list if different than null
                if (questionSetAnswersAP70 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAP70) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAP71Answer = answersByCode1.get(QuestionCode.AP71);
                                    QuestionAnswer questionAP73Answer = answersByCode1.get(QuestionCode.AP73);
                                    QuestionAnswer questionAP76Answer = answersByCode1.get(QuestionCode.AP76);
                                    QuestionAnswer questionAP77Answer = answersByCode1.get(QuestionCode.AP77);
                
                    //control them
                    if (                            questionAP71Answer == null  ||
                                                                                questionAP73Answer == null  ||
                                                                                questionAP76Answer == null  ||
                                                                                questionAP77Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.APE_BAD6);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(toString(questionAP71Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAP73Answer));

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAP76Answer)*toDouble(questionAP77Answer, getUnitByCode(UnitCode.U5106))/100);
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}