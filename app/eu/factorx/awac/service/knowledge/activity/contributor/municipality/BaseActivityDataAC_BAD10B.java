package eu.factorx.awac.service.knowledge.activity.contributor.municipality;

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
* AUTO-GENERATED by BADImportator at 19/09/2014 10:46
* Name of the BAD : mobilité
 */
@Component
public class BaseActivityDataAC_BAD10B extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAC85 = questionSetAnswers.get(QuestionCode.AC85);
                //2.2 control if the list if different than null
                if (questionSetAnswersAC85 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAC85) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC86Answer = answersByCode1.get(QuestionCode.AC86);
                                    QuestionAnswer questionAC91Answer = answersByCode1.get(QuestionCode.AC91);
                                    QuestionAnswer questionAC87Answer = answersByCode1.get(QuestionCode.AC87);
                                    QuestionAnswer questionAC88Answer = answersByCode1.get(QuestionCode.AC88);
                                    QuestionAnswer questionAC89Answer = answersByCode1.get(QuestionCode.AC89);
                                    QuestionAnswer questionAC90Answer = answersByCode1.get(QuestionCode.AC90);
                
                    //control them
                    if (                            questionAC86Answer == null  ||
                                                                                questionAC91Answer == null  ||
                                                                                questionAC87Answer == null  ||
                                                                                questionAC88Answer == null  ||
                                                                                questionAC89Answer == null  ||
                                                                                questionAC90Answer == null                                                 ) {
                       continue;
                    }
                    
                        if(!getCode(questionAC90Answer).getKey().equals("1")){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD10B);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(toString(questionAC86Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(toActivitySubCategoryCode(questionAC91Answer));
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAC87Answer));

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC88Answer, getUnitByCode(UnitCode.U5170))/toDouble(questionAC89Answer, getUnitByCode(UnitCode.U5170)));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}