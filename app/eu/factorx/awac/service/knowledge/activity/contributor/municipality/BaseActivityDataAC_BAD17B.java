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
* AUTO-GENERATED by BADImportator at 22/10/2014 08:03
* Name of the BAD : mobilité
 */
@Component
public class BaseActivityDataAC_BAD17B extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAC402 = questionSetAnswers.get(QuestionCode.AC402);
                //2.2 control if the list if different than null
                if (questionSetAnswersAC402 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAC402) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC404Answer = answersByCode1.get(QuestionCode.AC404);
                                    QuestionAnswer questionAC403Answer = answersByCode1.get(QuestionCode.AC403);
                                    QuestionAnswer questionAC405Answer = answersByCode1.get(QuestionCode.AC405);
                
                    //control them
                    if (                            questionAC404Answer == null  ||
                                                                                questionAC403Answer == null  ||
                                                                                questionAC405Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD17B);
                            baseActivityData.setAlternativeGroup("ALT_C_1_ROUTE_PROPRE");
                        baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_162);

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC404Answer, baseActivityDataUnit)+0*toDouble(questionAC403Answer, baseActivityDataUnit)+0*toDouble(questionAC405Answer, baseActivityDataUnit));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}