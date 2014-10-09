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
* AUTO-GENERATED by BADImportator at 09/10/2014 02:32
* Name of the BAD : Site: transport amont externe bateau
 */
@Component
public class BaseActivityDataAE_BAD17E extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = tonnes.km )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5329);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA142 = questionSetAnswers.get(QuestionCode.A142);
                //2.2 control if the list if different than null
                if (questionSetAnswersA142 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA142) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA145Answer = answersByCode1.get(QuestionCode.A145);
                                    QuestionAnswer questionA146Answer = answersByCode1.get(QuestionCode.A146);
                                    QuestionAnswer questionA151Answer = answersByCode1.get(QuestionCode.A151);
                
                    //control them
                    if (                            questionA145Answer == null  ||
                                                                                questionA146Answer == null  ||
                                                                                questionA151Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD17E);
                            baseActivityData.setAlternativeGroup("ALT_E_5_AMONT");
                        baseActivityData.setRank(1);
            baseActivityData.setSpecificPurpose(null);
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_4);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_6);
            baseActivityData.setActivityType(ActivityTypeCode.AT_26);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_177);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA145Answer, getUnitByCode(UnitCode.U5135))*toDouble(questionA146Answer, getUnitByCode(UnitCode.U5106))*toDouble(questionA151Answer));
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}