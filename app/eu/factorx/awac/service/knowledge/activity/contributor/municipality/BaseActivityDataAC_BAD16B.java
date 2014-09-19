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
<<<<<<< HEAD
* AUTO-GENERATED by BADImportator at 19/09/2014 11:42
=======
* AUTO-GENERATED by BADImportator at 19/09/2014 11:04
>>>>>>> origin/master
* Name of the BAD : investissement scope 2
 */
@Component
public class BaseActivityDataAC_BAD16B extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = tCO2e )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5331);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersAC139 = questionSetAnswers.get(QuestionCode.AC139);
                //2.2 control if the list if different than null
                if (questionSetAnswersAC139 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersAC139) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionAC140Answer = answersByCode1.get(QuestionCode.AC140);
                                    QuestionAnswer questionAC141Answer = answersByCode1.get(QuestionCode.AC141);
                                    QuestionAnswer questionAC143Answer = answersByCode1.get(QuestionCode.AC143);
                
                    //control them
                    if (                            questionAC140Answer == null  ||
                                                                                questionAC141Answer == null  ||
                                                                                questionAC143Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AC_BAD16B);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(toString(questionAC140Answer));
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_17);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_11);
            baseActivityData.setActivityType(ActivityTypeCode.AT_69);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_344);

            baseActivityData.setActivityOwnership(null);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionAC141Answer)*toDouble(questionAC143Answer));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}