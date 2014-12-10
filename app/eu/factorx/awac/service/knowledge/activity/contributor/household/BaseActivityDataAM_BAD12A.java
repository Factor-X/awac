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
* AUTO-GENERATED by BADImportator at 10/12/2014 05:44
* Name of the BAD : déchet matière volume
 */
@Component
public class BaseActivityDataAM_BAD12A extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAM153 = questionSetAnswers.get(QuestionCode.AM153);
                //2.2 control if the list if different than null
                if (questionSetAnswersAM153 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAM153) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAM154Answer = answersByCode1.get(QuestionCode.AM154);
                
                    //control them
                    if (                            questionAM154Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.AM156)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode2 = byQuestionCode(questionSetAnswer2.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAM157Answer = answersByCode2.get(QuestionCode.AM157);
                                    QuestionAnswer questionAM158Answer = answersByCode2.get(QuestionCode.AM158);
                                    QuestionAnswer questionAM159Answer = answersByCode2.get(QuestionCode.AM159);
                
                    //control them
                    if (                            questionAM157Answer == null  ||
                                                                                questionAM158Answer == null  ||
                                                                                questionAM159Answer == null                                                 ) {
                       continue;
                    }
                    
                        if((getCode(questionAM159Answer).getKey().equals("1"))){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AM_BAD12A);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
            baseActivityData.setActivityType(toActivityTypeCode(questionAM154Answer));

            baseActivityData.setActivitySource(toActivitySourceCode(questionAM154Answer));

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAM157Answer)*12*toDouble(questionAM158Answer, getUnitByCode(UnitCode.U5126))*0.25);
            res.add(baseActivityData);

                        }
            
                         }
                         }
                 return res;
    }

}