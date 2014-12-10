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
* AUTO-GENERATED by BADImportator at 09/12/2014 04:36
* Name of the BAD : mobilité DDT par employé
 */
@Component
public class BaseActivityDataAPE_BAD7F extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = passagers.km )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5328);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAP81 = questionSetAnswers.get(QuestionCode.AP81);
                //2.2 control if the list if different than null
                if (questionSetAnswersAP81 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAP81) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAP82Answer = answersByCode1.get(QuestionCode.AP82);
                                    QuestionAnswer questionAP86Answer = answersByCode1.get(QuestionCode.AP86);
                                    QuestionAnswer questionAP87Answer = answersByCode1.get(QuestionCode.AP87);
                                    QuestionAnswer questionAP83Answer = answersByCode1.get(QuestionCode.AP83);
                
                    //control them
                    if (                            questionAP82Answer == null  ||
                                                                                questionAP86Answer == null  ||
                                                                                questionAP87Answer == null  ||
                                                                                questionAP83Answer == null                                                 ) {
                       continue;
                    }
                    
                        if(getCode(questionAP83Answer).getKey().equals("6")){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.APE_BAD7F);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(toString(questionAP82Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_8);
            baseActivityData.setActivityType(ActivityTypeCode.AT_12);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_164);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAP86Answer)*2*toDouble(questionAP87Answer, getUnitByCode(UnitCode.U5106)));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}