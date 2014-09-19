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
* AUTO-GENERATED by BADImportator at 19/09/2014 10:59
* Name of the BAD : Site: utilisation aval - réfrigérant direct
 */
@Component
public class BaseActivityDataAE_BAD35D extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA244 = questionSetAnswers.get(QuestionCode.A244);
                //2.2 control if the list if different than null
                if (questionSetAnswersA244 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA244) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA245Answer = answersByCode1.get(QuestionCode.A245);
                                    QuestionAnswer questionA246Answer = answersByCode1.get(QuestionCode.A246);
                
                    //control them
                    if (                            questionA245Answer == null  ||
                                                                                questionA246Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.A291)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode2 = byQuestionCode(questionSetAnswer2.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA293Answer = answersByCode2.get(QuestionCode.A293);
                
                    //control them
                    if (                            questionA293Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer3 : getChildrenQuestionSetAnswers(questionSetAnswer2,QuestionCode.A297)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode3 = byQuestionCode(questionSetAnswer3.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA298Answer = answersByCode3.get(QuestionCode.A298);
                                    QuestionAnswer questionA299Answer = answersByCode3.get(QuestionCode.A299);
                
                    //control them
                    if (                            questionA298Answer == null  ||
                                                                                questionA299Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD35D);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionA245Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_12);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_5);
            baseActivityData.setActivityType(ActivityTypeCode.AT_8);

            baseActivityData.setActivitySource(toActivitySourceCode(questionA298Answer));

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA246Answer)*toDouble(questionA293Answer)*toDouble(questionA299Answer, getUnitByCode(UnitCode.U5135)));
            res.add(baseActivityData);

            
                         }
                         }
                         }
                 return res;
    }

}