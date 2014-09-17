package eu.factorx.awac.service.knowledge.activity.contributor.enterprise;

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
* AUTO-GENERATED by BADImportator at 17/09/2014 12:31
* Name of the BAD : Site: mobilité indirecte par km
 */
@Component
public class BaseActivityDataAE_BAD10G extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA67 = questionSetAnswers.get(QuestionCode.A67);
                //2.2 control if the list if different than null
                if (questionSetAnswersA67 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA67) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA70Answer = answersByCode1.get(QuestionCode.A70);
                                    QuestionAnswer questionA71Answer = answersByCode1.get(QuestionCode.A71);
                                    QuestionAnswer questionA69Answer = answersByCode1.get(QuestionCode.A69);
                                    QuestionAnswer questionA76Answer = answersByCode1.get(QuestionCode.A76);
                                    QuestionAnswer questionA75Answer = answersByCode1.get(QuestionCode.A75);
                                    QuestionAnswer questionA72Answer = answersByCode1.get(QuestionCode.A72);
                
                    //control them
                    if (                            questionA70Answer == null  ||
                                                                                questionA71Answer == null  ||
                                                                                questionA69Answer == null  ||
                                                                                questionA76Answer == null  ||
                                                                                questionA75Answer == null  ||
                                                                                questionA72Answer == null                                                 ) {
                       continue;
                    }
                    
                        if(!toBoolean(questionA69Answer) && ((getCode(questionA72Answer).getKey().equals("6")) || (getCode(questionA72Answer).getKey().equals("7")))){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD10G);
            baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(toActivitySubCategoryCode(questionA70Answer));
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionA71Answer));

            baseActivityData.setActivityOwnership(toBoolean(questionA69Answer));
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA76Answer)*toDouble(questionA75Answer)/100);
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}