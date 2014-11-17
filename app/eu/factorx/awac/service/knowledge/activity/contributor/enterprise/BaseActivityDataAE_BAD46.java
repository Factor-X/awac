package eu.factorx.awac.service.knowledge.activity.contributor.enterprise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;

/**
* AUTO-GENERATED by BADImportator at 27/10/2014 09:10
* Name of the BAD : Site: mobilité véhicules non possédés DDT indirecte par euros
 */
@Component
public class BaseActivityDataAE_BAD46 extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA513 = questionSetAnswers.get(QuestionCode.A513);
                //2.2 control if the list if different than null
                if (questionSetAnswersA513 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA513) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA515Answer = answersByCode1.get(QuestionCode.A515);
                                    QuestionAnswer questionA517Answer = answersByCode1.get(QuestionCode.A517);
                                    QuestionAnswer questionA516Answer = answersByCode1.get(QuestionCode.A516);
                
                    //control them
                    if (                            questionA515Answer == null  ||
                                                                                questionA517Answer == null  ||
                                                                                questionA516Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD46);
                            baseActivityData.setAlternativeGroup("ALT_E_11_ROUTE_DDT_PROPRE");
                        baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_8);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionA515Answer));

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA517Answer, getUnitByCode(UnitCode.U5170))/toDouble(questionA516Answer, getUnitByCode(UnitCode.U5170)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}