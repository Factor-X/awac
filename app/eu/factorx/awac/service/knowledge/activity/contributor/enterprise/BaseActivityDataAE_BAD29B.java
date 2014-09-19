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
* AUTO-GENERATED by BADImportator at 18/09/2014 06:52
* Name of the BAD : Site: infrastructure
 */
@Component
public class BaseActivityDataAE_BAD29B extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = t )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5135);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA231 = questionSetAnswers.get(QuestionCode.A231);
                //2.2 control if the list if different than null
                if (questionSetAnswersA231 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA231) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA232Answer = answersByCode1.get(QuestionCode.A232);
                                    QuestionAnswer questionA233Answer = answersByCode1.get(QuestionCode.A233);
                                    QuestionAnswer questionA235Answer = answersByCode1.get(QuestionCode.A235);
                
                    //control them
                    if (                            questionA232Answer == null  ||
                                                                                questionA233Answer == null  ||
                                                                                questionA235Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD29B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionA232Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_9);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
            baseActivityData.setActivityType(toActivityTypeCode(questionA233Answer));

            baseActivityData.setActivitySource(toActivitySourceCode(questionA233Answer));

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA235Answer, getUnitByCode(UnitCode.U5135)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}