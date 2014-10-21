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
* AUTO-GENERATED by BADImportator at 21/10/2014 12:32
* Name of the BAD : Site: Déchet eau usée indirecte
 */
@Component
public class BaseActivityDataAE_BAD21 extends ActivityResultContributor {

    @Override
    public List<BaseActivityData> getBaseActivityData(Map<QuestionCode, List<QuestionSetAnswer>> questionSetAnswers) {

        List<BaseActivityData> res = new ArrayList<>();

        //1. build unit(s) needed for
        //  - a) the BAD.unit
        //  - b) conversion

        // Get Target Unit (GJ in this case)
        // Allow finding unit by a UnitCode (unit symbole = équivalent.habitant )
        Unit baseActivityDataUnit = getUnitByCode(UnitCode.U5330);


        //2. build BAD

        
        //2.1 loop for each answer for each loop for each parents of one or more question needed by the BAD
        
            
                            List<QuestionSetAnswer> questionSetAnswersA182 = questionSetAnswers.get(QuestionCode.A182);
                //2.2 control if the list if different than null
                if (questionSetAnswersA182 == null) {
                    return res;
                }
            
            //loop ($repetition.mainQuestionSetDescription)
                            for (QuestionSetAnswer questionSetAnswer1 : questionSetAnswersA182) {
             
                            //create a map for each repetition level
                Map<QuestionCode, QuestionAnswer> answersByCode1 = byQuestionCode(questionSetAnswer1.getQuestionAnswers());

                //load question for this level
                                    QuestionAnswer questionA183Answer = answersByCode1.get(QuestionCode.A183);
                                    QuestionAnswer questionA184Answer = answersByCode1.get(QuestionCode.A184);
                
                    //control them
                    if (                            questionA183Answer == null  ||
                                                                                questionA184Answer == null                                                 ) {
                       continue;
                    }
                    
            
            //build the bad
            BaseActivityData baseActivityData = new BaseActivityData();

            baseActivityData.setKey(BaseActivityDataCode.AE_BAD21);
                            baseActivityData.setAlternativeGroup("null");
                        baseActivityData.setRank(2);
            baseActivityData.setSpecificPurpose("atelier");
            baseActivityData.setActivityCategory(ActivityCategoryCode.AC_7);
            baseActivityData.setActivitySubCategory(ActivitySubCategoryCode.ASC_10);
            baseActivityData.setActivityType(ActivityTypeCode.AT_55);

            baseActivityData.setActivitySource(ActivitySourceCode.AS_191);

            baseActivityData.setActivityOwnership(false);
            baseActivityData.setUnit(baseActivityDataUnit);
            baseActivityData.setValue(toDouble(questionA183Answer)/2*toDouble(questionA184Answer)/365);
            res.add(baseActivityData);

            
                         }
                 return res;
    }

}