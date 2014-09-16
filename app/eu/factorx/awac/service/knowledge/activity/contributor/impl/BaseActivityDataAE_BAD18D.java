package eu.factorx.awac.service.knowledge.activity.contributor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.factorx.awac.models.code.type.*;
import eu.factorx.awac.models.data.answer.QuestionAnswer;
import eu.factorx.awac.models.data.answer.QuestionSetAnswer;
import eu.factorx.awac.models.knowledge.Unit;
import eu.factorx.awac.models.reporting.BaseActivityData;
import eu.factorx.awac.service.knowledge.activity.contributor.ActivityResultContributor;
import org.springframework.stereotype.Component;

/**
* AUTO-GENERATED by BADImportator at 16/09/2014 12:14
* Name of the BAD : Site: transport amont externe estimé hors camion Bel
 */
@Component
public class BaseActivityDataAE_BAD18D extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = tonnes.km )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5329);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA157 = questionSetAnswers.get(QuestionCode.A157);
                //2.2 control if the list if different than null
                if (questionSetAnswersA157 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA157) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA158Answer = answersByCode1.get(QuestionCode.A158);
                                    QuestionAnswer questionA159Answer = answersByCode1.get(QuestionCode.A159);
                
                    //control them
                    if (                            questionA158Answer == null  ||
                                                                                questionA159Answer == null                                                 ) {
                       continue;
                    }
                    
                        if(getCode(questionA159Answer).getKey().equals("1")){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD18D);
            baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_6);
            baseActivityData.setActivityType(ActivityTypeCode.AT_34);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_178);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA158Answer, getUnitByCode(UnitCode.U5135))*200);
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}