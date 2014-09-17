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
* AUTO-GENERATED by BADImportator at 17/09/2014 11:22
* Name of the BAD : Site: mobilité DPRO avion direct
 */
@Component
public class BaseActivityDataAE_BAD14 extends ActivityResultContributor {

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
        
            
                            List<QuestionSetAnswer> questionSetAnswersA115 = questionSetAnswers.get(QuestionCode.A115);
                //2.2 control if the list if different than null
                if (questionSetAnswersA115 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA115) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA118Answer = answersByCode1.get(QuestionCode.A118);
                                    QuestionAnswer questionA117Answer = answersByCode1.get(QuestionCode.A117);
                                    QuestionAnswer questionA119Answer = answersByCode1.get(QuestionCode.A119);
                                    QuestionAnswer questionA120Answer = answersByCode1.get(QuestionCode.A120);
                
                    //control them
                    if (                            questionA118Answer == null  ||
                                                                                questionA117Answer == null  ||
                                                                                questionA119Answer == null  ||
                                                                                questionA120Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD14);
            baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
            baseActivityData.setActivityType(toActivityTypeCode(questionA118Answer));

            baseActivityData.setActivitySource(toActivitySourceCode(questionA117Answer));

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA119Answer)*toDouble(questionA120Answer, getUnitByCode(UnitCode.U5106)));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}