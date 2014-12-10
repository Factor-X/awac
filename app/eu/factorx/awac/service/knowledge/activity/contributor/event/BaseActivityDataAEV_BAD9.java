package eu.factorx.awac.service.knowledge.activity.contributor.event;

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
* AUTO-GENERATED by BADImportator at 10/12/2014 06:24
* Name of the BAD : transport amont
 */
@Component
public class BaseActivityDataAEV_BAD9 extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAEV98 = questionSetAnswers.get(QuestionCode.AEV98);
                //2.2 control if the list if different than null
                if (questionSetAnswersAEV98 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAEV98) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAEV99Answer = answersByCode1.get(QuestionCode.AEV99);
                                    QuestionAnswer questionAEV102Answer = answersByCode1.get(QuestionCode.AEV102);
                                    QuestionAnswer questionAEV105Answer = answersByCode1.get(QuestionCode.AEV105);
                                    QuestionAnswer questionAEV103Answer = answersByCode1.get(QuestionCode.AEV103);
                                    QuestionAnswer questionAEV104Answer = answersByCode1.get(QuestionCode.AEV104);
                                    QuestionAnswer questionAEV101Answer = answersByCode1.get(QuestionCode.AEV101);
                
                    //control them
                    if (                            questionAEV99Answer == null  ||
                                                                                questionAEV102Answer == null  ||
                                                                                questionAEV105Answer == null  ||
                                                                                questionAEV103Answer == null  ||
                                                                                questionAEV104Answer == null  ||
                                                                                questionAEV101Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AEV_BAD9);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(toString(questionAEV99Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_6);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAEV102Answer));

            baseActivityData.setActivityOwnership(toBoolean(questionAEV105Answer));
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAEV103Answer)*toDouble(questionAEV104Answer, getUnitByCode(UnitCode.U5106))*toDouble(questionAEV101Answer)/100);
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}