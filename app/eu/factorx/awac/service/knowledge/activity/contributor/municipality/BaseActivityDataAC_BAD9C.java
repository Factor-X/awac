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
* AUTO-GENERATED by BADImportator at 17/09/2014 12:17
* Name of the BAD : mobilité
 */
@Component
public class BaseActivityDataAC_BAD9C extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAC73 = questionSetAnswers.get(QuestionCode.AC73);
                //2.2 control if the list if different than null
                if (questionSetAnswersAC73 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAC73) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC74Answer = answersByCode1.get(QuestionCode.AC74);
                                    QuestionAnswer questionAC77Answer = answersByCode1.get(QuestionCode.AC77);
                                    QuestionAnswer questionAC83Answer = answersByCode1.get(QuestionCode.AC83);
                                    QuestionAnswer questionAC81Answer = answersByCode1.get(QuestionCode.AC81);
                                    QuestionAnswer questionAC75Answer = answersByCode1.get(QuestionCode.AC75);
                                    QuestionAnswer questionAC78Answer = answersByCode1.get(QuestionCode.AC78);
                
                    //control them
                    if (                            questionAC74Answer == null  ||
                                                                                questionAC77Answer == null  ||
                                                                                questionAC83Answer == null  ||
                                                                                questionAC81Answer == null  ||
                                                                                questionAC75Answer == null  ||
                                                                                questionAC78Answer == null                                                 ) {
                       continue;
                    }
                    
                        if((getCode(questionAC75Answer).getKey().equals("1")) && ((getCode(questionAC78Answer).getKey().equals("7"))  || (getCode(questionAC78Answer).getKey().equals("6")))){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD9C);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(toString(questionAC74Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAC77Answer));

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC83Answer, getUnitByCode(UnitCode.U5106))/100*toDouble(questionAC81Answer));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}