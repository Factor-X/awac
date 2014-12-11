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
* AUTO-GENERATED by BADImportator at 11/12/2014 11:21
* Name of the BAD : déchet matière volume
 */
@Component
public class BaseActivityDataAEV_BAD10A extends ActivityResultContributor {

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

        



        //and control

        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAEV109 = questionSetAnswers.get(QuestionCode.AEV109);
                //2.2 control if the list if different than null
                if (questionSetAnswersAEV109 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAEV109) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAEV110Answer = answersByCode1.get(QuestionCode.AEV110);
                
                    //control them
                    if (                            questionAEV110Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.AEV112)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode2 = byQuestionCode(questionSetAnswer2.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAEV113Answer = answersByCode2.get(QuestionCode.AEV113);
                                    QuestionAnswer questionAEV114Answer = answersByCode2.get(QuestionCode.AEV114);
                                    QuestionAnswer questionAEV115Answer = answersByCode2.get(QuestionCode.AEV115);
                
                    //control them
                    if (                            questionAEV113Answer == null  ||
                                                                                questionAEV114Answer == null  ||
                                                                                questionAEV115Answer == null                                                 ) {
                       continue;
                    }
                    
                        if((getCode(questionAEV115Answer).getKey().equals("1"))){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AEV_BAD10A);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
            baseActivityData.setActivityType(toActivityTypeCode(questionAEV110Answer));

            baseActivityData.setActivitySource(toActivitySourceCode(questionAEV110Answer));

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAEV113Answer)*12*toDouble(questionAEV114Answer, getUnitByCode(UnitCode.U5126))*0.25);
            res.add(baseActivityData);

                        }
            
                         }
                         }
                 return res;
    }

}