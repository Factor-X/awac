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
* AUTO-GENERATED by BADImportator at 19/09/2014 11:04
* Name of the BAD : Bâtiment: usage vapeur
 */
@Component
public class BaseActivityDataAC_BAD3 extends ActivityResultContributor {

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
                             for (QuestionSetAnswer questionSetAnswer2 : getChildrenQuestionSetAnswers(questionSetAnswer1,QuestionCode.AC32)) {
             
                    
            
            
            //loop ($repetition.mainQuestionSetDescription)
                             for (QuestionSetAnswer questionSetAnswer3 : getChildrenQuestionSetAnswers(questionSetAnswer2,QuestionCode.AC33)) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode3 = byQuestionCode(questionSetAnswer3.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC34Answer = answersByCode3.get(QuestionCode.AC34);
                                    QuestionAnswer questionAC36Answer = answersByCode3.get(QuestionCode.AC36);
                                    QuestionAnswer questionAC35Answer = answersByCode3.get(QuestionCode.AC35);
                
                    //control them
                    if (                            questionAC34Answer == null  ||
                                                                                questionAC36Answer == null  ||
                                                                                questionAC35Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD3);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionAC11Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_1);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_3);
            baseActivityData.setActivityType(ActivityTypeCode.AT_7);

            baseActivityData.setActivitySource(toActivitySourceCode(questionAC34Answer));

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC36Answer, getUnitByCode(UnitCode.U5321))/toDouble(questionAC35Answer));
            res.add(baseActivityData);

            
                         }
                         }
                         }
                 return res;
    }

}