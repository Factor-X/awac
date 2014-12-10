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
* Name of the BAD : Combustibles
 */
@Component
public class BaseActivityDataAEV_BAD1E extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAEV13 = questionSetAnswers.get(QuestionCode.AEV13);
                //2.2 control if the list if different than null
                if (questionSetAnswersAEV13 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAEV13) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAEV14Answer = answersByCode1.get(QuestionCode.AEV14);
                                    QuestionAnswer questionAEV18Answer = answersByCode1.get(QuestionCode.AEV18);
                                    QuestionAnswer questionAEV21Answer = answersByCode1.get(QuestionCode.AEV21);
                
                    //control them
                    if (                            questionAEV14Answer == null  ||
                                                                                questionAEV18Answer == null  ||
                                                                                questionAEV21Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AEV_BAD1E);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_1);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAEV14Answer));

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAEV18Answer, getUnitByCode(UnitCode.U5170))/toDouble(questionAEV21Answer, getUnitByCode(UnitCode.U5170))/1000);
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}