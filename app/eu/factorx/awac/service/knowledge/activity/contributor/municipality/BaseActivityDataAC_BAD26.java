package eu.factorx.awac.service.knowledge.activity.contributor.municipality;

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
* AUTO-GENERATED by BADImportator at 28/10/2014 11:46
* Name of the BAD : Bâtiment: combustion directe
 */
@Component
public class BaseActivityDataAC_BAD26 extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersAC10 = questionSetAnswers.get(QuestionCode.AC10);
                //2.2 control if the list if different than null
                if (questionSetAnswersAC10 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAC10) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC11Answer = answersByCode1.get(QuestionCode.AC11);
                
                    //control them
                    if (                            questionAC11Answer == null                                                 ) {
                       continue;
                    }
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.AC24)) {
             
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer3 : getChildrenQuestionSetAnswers(questionSetAnswer2,QuestionCode.AC900)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode3 = byQuestionCode(questionSetAnswer3.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC901Answer = answersByCode3.get(QuestionCode.AC901);
                                    QuestionAnswer questionAC902Answer = answersByCode3.get(QuestionCode.AC902);
                
                    //control them
                    if (                            questionAC901Answer == null  ||
                                                                                questionAC902Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD26);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(0);
            baseActivityData.setSpecificPurpose(toString(questionAC11Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_1);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_1);
            baseActivityData.setActivityType(ActivityTypeCode.AT_1);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAC901Answer));

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC902Answer, getUnitByCode(UnitCode.U5126)));
            res.add(baseActivityData);

            
                         }
                         }
                         }
                 return res;
    }

}