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
* AUTO-GENERATED by BADImportator at 09/12/2014 11:43
* Name of the BAD : mobilité metro
 */
@Component
public class BaseActivityDataAM_BAD8 extends ActivityResultContributor {

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

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAM130 = questionSetAnswers.get(QuestionCode.AM130);
                //2.2 control if the list if different than null
                if (questionSetAnswersAM130 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAM130) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAM131Answer = answersByCode1.get(QuestionCode.AM131);
                                    QuestionAnswer questionAM132Answer = answersByCode1.get(QuestionCode.AM132);
                                    QuestionAnswer questionAM133Answer = answersByCode1.get(QuestionCode.AM133);
                
                    //control them
                    if (                            questionAM131Answer == null  ||
                                                                                questionAM132Answer == null  ||
                                                                                questionAM133Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AM_BAD8);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_18);
            baseActivityData.setActivityType(ActivityTypeCode.AT_12);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_164);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAM131Answer)*toDouble(questionAM132Answer)*toDouble(questionAM133Answer, getUnitByCode(UnitCode.U5106))*2);
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}