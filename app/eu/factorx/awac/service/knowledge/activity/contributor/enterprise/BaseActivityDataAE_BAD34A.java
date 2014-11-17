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
* Name of the BAD : Site: traitement aval - combustion directe
 */
@Component
public class BaseActivityDataAE_BAD34A extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = GJ )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5321);


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
                
                    //control them
                    if (                            questionA245Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.A282)) {
             
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer3 : getChildrenQuestionSetAnswers(questionSetAnswer2,QuestionCode.A284)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode3 = byQuestionCode(questionSetAnswer3.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA285Answer = answersByCode3.get(QuestionCode.A285);
                                    QuestionAnswer questionA286Answer = answersByCode3.get(QuestionCode.A286);
                
                    //control them
                    if (                            questionA285Answer == null  ||
                                                                                questionA286Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD34A);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(toString(questionA245Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_11);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionA285Answer));

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA286Answer, getUnitByCode(UnitCode.U5321)));
            res.add(baseActivityData);

            
                         }
                         }
                         }
                 return res;
    }

}