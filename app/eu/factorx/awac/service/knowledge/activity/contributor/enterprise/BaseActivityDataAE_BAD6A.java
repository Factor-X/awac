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
* Name of the BAD : Site: rejet réfrigérant estimé
 */
@Component
public class BaseActivityDataAE_BAD6A extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = kW )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5324);


        //2. build BAD

                //load question without repetition
                    List<QuestionSetAnswer> questionSetAnswersA47 = questionSetAnswers.get(QuestionCode.A47);
        
        //... and control
        if (
                    questionSetAnswersA47 == null || questionSetAnswersA47.size()==0         ) {
        return res;
        }

        //load question
                        QuestionAnswer questionA49Answer = byQuestionCode(questionSetAnswersA47.get(0).getQuestionAnswers()).get(QuestionCode.A49);
                QuestionAnswer questionA48Answer = byQuestionCode(questionSetAnswersA47.get(0).getQuestionAnswers()).get(QuestionCode.A48);
                        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA22 = questionSetAnswers.get(QuestionCode.A22);
                //2.2 control if the list if different than null
                if (questionSetAnswersA22 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA22) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA23Answer = answersByCode1.get(QuestionCode.A23);
                
                    //control them
                    if (                            questionA23Answer == null                                                 ) {
                       continue;
                    }
                    
                        if(!toBoolean(questionA48Answer)){
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD6A);
                            baseActivityData.setAlternativeGroup("ALT_E_1_FROID");
                        baseActivityData.setRank(3);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_3);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_5);
            baseActivityData.setActivityType(ActivityTypeCode.AT_10);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_161);

            baseActivityData.setActivityOwnership(true);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(2*(toDouble(questionA23Answer, getUnitByCode(UnitCode.U5156)))/toDouble(questionA49Answer, getUnitByCode(UnitCode.U5147)));
            res.add(baseActivityData);

                        }
            
                         }
                 return res;
    }

}