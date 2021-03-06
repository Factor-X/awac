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
* AUTO-GENERATED by BADImportator at 01/12/2014 12:38
* Name of the BAD : Site: mobilité DPRO avion indirect
 */
@Component
public class BaseActivityDataAE_BAD15A extends ActivityResultContributor {

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

                //load question without repetition
                    List<QuestionSetAnswer> questionSetAnswersA1 = questionSetAnswers.get(QuestionCode.A1);
        
        //... and control
        if (
                    questionSetAnswersA1 == null || questionSetAnswersA1.size()==0         ) {
        return res;
        }

        //load question
                        QuestionAnswer questionA12Answer = byQuestionCode(questionSetAnswersA1.get(0).getQuestionAnswers()).get(QuestionCode.A12);
                        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA121 = questionSetAnswers.get(QuestionCode.A121);
                //2.2 control if the list if different than null
                if (questionSetAnswersA121 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA121) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA127Answer = answersByCode1.get(QuestionCode.A127);
                                    QuestionAnswer questionA122Answer = answersByCode1.get(QuestionCode.A122);
                                    QuestionAnswer questionA123Answer = answersByCode1.get(QuestionCode.A123);
                
                    //control them
                    if (                            questionA127Answer == null  ||
                                                                                questionA122Answer == null  ||
                                                                                questionA123Answer == null                                                 ) {
                       continue;
                    }
                    
                        if(toBoolean(questionA123Answer)){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD15A);
                            baseActivityData.setAlternativeGroup("ALT_E_4_AVION");
                        baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_5);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_7);
            baseActivityData.setActivityType(ActivityTypeCode.AT_21);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_174);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA127Answer, getUnitByCode(UnitCode.U5106))*toDouble(questionA12Answer)*toDouble(questionA122Answer));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}