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
* AUTO-GENERATED by BADImportator at 22/10/2014 11:56
* Name of the BAD : Site: distribution amont - combustion directe
 */
@Component
public class BaseActivityDataAE_BAD19E extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA164 = questionSetAnswers.get(QuestionCode.A164);
                //2.2 control if the list if different than null
                if (questionSetAnswersA164 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA164) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA165Answer = answersByCode1.get(QuestionCode.A165);
                
                    //control them
                    if (                            questionA165Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.A1009)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode2 = byQuestionCode(questionSetAnswer2.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA1010Answer = answersByCode2.get(QuestionCode.A1010);
                                    QuestionAnswer questionA1011Answer = answersByCode2.get(QuestionCode.A1011);
                
                    //control them
                    if (                            questionA1010Answer == null  ||
                                                                                questionA1011Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD19E);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(toString(questionA165Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_6);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionA1010Answer));

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA1011Answer, getUnitByCode(UnitCode.U5135)));
            res.add(baseActivityData);

            
                         }
                         }
                 return res;
    }

}