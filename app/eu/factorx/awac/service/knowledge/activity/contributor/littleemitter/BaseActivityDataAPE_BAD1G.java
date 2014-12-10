package eu.factorx.awac.service.knowledge.activity.contributor.littleemitter;

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
* AUTO-GENERATED by BADImportator at 10/12/2014 10:03
* Name of the BAD : Chauffage: combustion directe
 */
@Component
public class BaseActivityDataAPE_BAD1G extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAP17 = questionSetAnswers.get(QuestionCode.AP17);
                //2.2 control if the list if different than null
                if (questionSetAnswersAP17 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAP17) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAP18Answer = answersByCode1.get(QuestionCode.AP18);
                                    QuestionAnswer questionAP24Answer = answersByCode1.get(QuestionCode.AP24);
                                    QuestionAnswer questionAP29Answer = answersByCode1.get(QuestionCode.AP29);
                
                    //control them
                    if (                            questionAP18Answer == null  ||
                                                                                questionAP24Answer == null  ||
                                                                                questionAP29Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.APE_BAD1G);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_1);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_14);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAP18Answer));

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAP24Answer, getUnitByCode(UnitCode.U5170))/toDouble(questionAP29Answer, getUnitByCode(UnitCode.U5170))/1000);
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}