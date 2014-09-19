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
* AUTO-GENERATED by BADImportator at 19/09/2014 11:42
* Name of the BAD : Site: actif loué - réfrigérant direct
 */
@Component
public class BaseActivityDataAE_BAD37C extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA311 = questionSetAnswers.get(QuestionCode.A311);
                //2.2 control if the list if different than null
                if (questionSetAnswersA311 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA311) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA312Answer = answersByCode1.get(QuestionCode.A312);
                
                    //control them
                    if (                            questionA312Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.A317)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode2 = byQuestionCode(questionSetAnswer2.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA318Answer = answersByCode2.get(QuestionCode.A318);
                                    QuestionAnswer questionA319Answer = answersByCode2.get(QuestionCode.A319);
                
                    //control them
                    if (                            questionA318Answer == null  ||
                                                                                questionA319Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD37C);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionA312Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_14);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_5);
            baseActivityData.setActivityType(ActivityTypeCode.AT_8);

            baseActivityData.setActivitySource(toActivitySourceCode(questionA318Answer));

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA319Answer, getUnitByCode(UnitCode.U5135)));
            res.add(baseActivityData);

            
                         }
                         }
                 return res;
    }

}