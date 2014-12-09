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
* AUTO-GENERATED by BADImportator at 09/12/2014 03:30
* Name of the BAD : mobilité avion
 */
@Component
public class BaseActivityDataAPE_BAD15 extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAP134 = questionSetAnswers.get(QuestionCode.AP134);
                //2.2 control if the list if different than null
                if (questionSetAnswersAP134 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAP134) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAP135Answer = answersByCode1.get(QuestionCode.AP135);
                                    QuestionAnswer questionAP137Answer = answersByCode1.get(QuestionCode.AP137);
                                    QuestionAnswer questionAP136Answer = answersByCode1.get(QuestionCode.AP136);
                                    QuestionAnswer questionAP139Answer = answersByCode1.get(QuestionCode.AP139);
                                    QuestionAnswer questionAP138Answer = answersByCode1.get(QuestionCode.AP138);
                
                    //control them
                    if (                            questionAP135Answer == null  ||
                                                                                questionAP137Answer == null  ||
                                                                                questionAP136Answer == null  ||
                                                                                questionAP139Answer == null  ||
                                                                                questionAP138Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.APE_BAD15);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(toString(questionAP135Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
            baseActivityData.setActivityType(toActivityTypeCode(questionAP137Answer));

            baseActivityData.setActivitySource(toActivitySourceCode(questionAP136Answer));

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAP139Answer, getUnitByCode(UnitCode.U5106))*toDouble(questionAP138Answer));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}