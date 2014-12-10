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
* Name of the BAD : Mobilité routière des visiteurs
 */
@Component
public class BaseActivityDataAEV_BAD4A extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAEV38 = questionSetAnswers.get(QuestionCode.AEV38);
                //2.2 control if the list if different than null
                if (questionSetAnswersAEV38 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAEV38) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAEV40Answer = answersByCode1.get(QuestionCode.AEV40);
                                    QuestionAnswer questionAEV41Answer = answersByCode1.get(QuestionCode.AEV41);
                
                    //control them
                    if (                            questionAEV40Answer == null  ||
                                                                                questionAEV41Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.AEV42)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode2 = byQuestionCode(questionSetAnswer2.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAEV43Answer = answersByCode2.get(QuestionCode.AEV43);
                                    QuestionAnswer questionAEV44Answer = answersByCode2.get(QuestionCode.AEV44);
                                    QuestionAnswer questionAEV45Answer = answersByCode2.get(QuestionCode.AEV45);
                
                    //control them
                    if (                            questionAEV43Answer == null  ||
                                                                                questionAEV44Answer == null  ||
                                                                                questionAEV45Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AEV_BAD4A);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_9);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_162);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAEV40Answer)*toDouble(questionAEV41Answer, getUnitByCode(UnitCode.U5106))*toDouble(questionAEV43Answer)*toDouble(questionAEV44Answer)/100*toDouble(questionAEV45Answer));
            res.add(baseActivityData);

            
                         }
                         }
                 return res;
    }

}