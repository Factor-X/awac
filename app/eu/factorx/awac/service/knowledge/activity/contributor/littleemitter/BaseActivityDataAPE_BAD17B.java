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
* AUTO-GENERATED by BADImportator at 18/12/2014 10:10
* Name of the BAD : transport amont
 */
@Component
public class BaseActivityDataAPE_BAD17B extends ActivityResultContributor {

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

        



        //and control

        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAP152 = questionSetAnswers.get(QuestionCode.AP152);
                //2.2 control if the list if different than null
                if (questionSetAnswersAP152 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAP152) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAP153Answer = answersByCode1.get(QuestionCode.AP153);
                                    QuestionAnswer questionAP154Answer = answersByCode1.get(QuestionCode.AP154);
                                    QuestionAnswer questionAP155Answer = answersByCode1.get(QuestionCode.AP155);
                                    QuestionAnswer questionAP156Answer = answersByCode1.get(QuestionCode.AP156);
                
                    //control them
                    if (                            questionAP153Answer == null  ||
                                                                                questionAP154Answer == null  ||
                                                                                questionAP155Answer == null  ||
                                                                                questionAP156Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.APE_BAD17B);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(toString(questionAP153Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_12);
            baseActivityData.setActivityType(ActivityTypeCode.AT_23);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_177);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAP154Answer, getUnitByCode(UnitCode.U5135))*toDouble(questionAP155Answer, getUnitByCode(UnitCode.U5106))*toDouble(questionAP156Answer));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}