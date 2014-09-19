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
* AUTO-GENERATED by BADImportator at 19/09/2014 10:59
* Name of the BAD : Site: infrastructure spécifique
 */
@Component
public class BaseActivityDataAE_BAD30 extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = tCO2e )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5331);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA238 = questionSetAnswers.get(QuestionCode.A238);
                //2.2 control if the list if different than null
                if (questionSetAnswersA238 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA238) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA239Answer = answersByCode1.get(QuestionCode.A239);
                                    QuestionAnswer questionA240Answer = answersByCode1.get(QuestionCode.A240);
                                    QuestionAnswer questionA242Answer = answersByCode1.get(QuestionCode.A242);
                
                    //control them
                    if (                            questionA239Answer == null  ||
                                                                                questionA240Answer == null  ||
                                                                                questionA242Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD30);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionA239Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_9);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_11);
            baseActivityData.setActivityType(ActivityTypeCode.AT_69);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_344);

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA240Answer)*toDouble(questionA242Answer));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}