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
<<<<<<< HEAD
* AUTO-GENERATED by BADImportator at 19/09/2014 11:42
=======
* AUTO-GENERATED by BADImportator at 19/09/2014 11:04
>>>>>>> origin/master
* Name of the BAD : mobilité
 */
@Component
public class BaseActivityDataAC_BAD8B extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAC66 = questionSetAnswers.get(QuestionCode.AC66);
                //2.2 control if the list if different than null
                if (questionSetAnswersAC66 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAC66) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC67Answer = answersByCode1.get(QuestionCode.AC67);
                                    QuestionAnswer questionAC69Answer = answersByCode1.get(QuestionCode.AC69);
                                    QuestionAnswer questionAC70Answer = answersByCode1.get(QuestionCode.AC70);
                                    QuestionAnswer questionAC71Answer = answersByCode1.get(QuestionCode.AC71);
                                    QuestionAnswer questionAC68Answer = answersByCode1.get(QuestionCode.AC68);
                
                    //control them
                    if (                            questionAC67Answer == null  ||
                                                                                questionAC69Answer == null  ||
                                                                                questionAC70Answer == null  ||
                                                                                questionAC71Answer == null  ||
                                                                                questionAC68Answer == null                                                 ) {
                       continue;
                    }
                    
                        if((!getCode(questionAC68Answer).getKey().equals("1"))){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD8B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionAC67Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(toActivitySubCategoryCode(questionAC69Answer));
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAC70Answer));

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC71Answer, getUnitByCode(UnitCode.U5126)));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}